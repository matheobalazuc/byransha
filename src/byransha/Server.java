package byransha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
	public static void main(String[] args)
			throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		String nodeClass = args[0];
		System.out.println("initialising " + nodeClass);
		GOBMNode node = (GOBMNode) Class.forName(nodeClass).getConstructor().newInstance();

		int port = 5567;
		var ss = new ServerSocket(port);
		System.out.println("listening on port " + port);
		AtomicBoolean prompt = new AtomicBoolean(false);

		List<Command> allCmds = new ArrayList<>();
		allCmds.add(new Command("a", "print_current_node_class") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				out.println(node.getClass());
			}
		});
		allCmds.add(new Command("views", "v", "list_views") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				if (parms.isEmpty()) {
					out.println(node.compliantViews().stream().map(v -> v.name()).toList());
				} else if (parms.size() == 1 && parms.get(0).equals("*")) {
					node.compliantViews()
							.forEach(v -> {
                                try {
                                    out.println(v.name() + "\n" + v.toJSONNode(node, null).toPrettyString());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
				} else {
					node.compliantViews().stream().filter(v -> parms.contains(v.name()))
							.forEach(v -> {
                                try {
                                    out.println(v.toJSONNode(node, null).toPrettyString());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
				}
			}
		});
		allCmds.add(new Command("outs", "o", "print_name_of_out_components") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				node.forEachOut((name, o) -> out.println(name));
			}
		});
		allCmds.add(new Command("p", "prompt", "toggle_prompt") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				prompt.set(!prompt.get());
			}
		});
		allCmds.add(new Command("q", "exit", "quit", "bye") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		allCmds.add(new Command("ks", "kill_server") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				System.exit(0);
			}
		});

		allCmds.add(new Command("h", "help") {

			@Override
			void f(PrintStream out, List<String> parms, Socket client) {
				allCmds.forEach(c -> out.println(c.names));
			}
		});

		while (true) {
			var client = ss.accept();

			new Thread(() -> {
				try {
					var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					var out = new PrintStream(client.getOutputStream());

					while (true) {
						if (prompt.get()) {
							out.print(node + "> ");
						}

						var line = in.readLine();

						if (line == null) {
							break;
						}

						var commandLine = new ArrayList<>(Arrays.asList(line.split(" +")));
						var command = commandLine.remove(0);
						var matchingCmds = allCmds.stream().filter(cmd -> cmd.names.contains(command)).toList();

						if (matchingCmds.isEmpty()) {
							out.println("unknown command: " + command);
						} else if (matchingCmds.size() > 1) {
							out.println("multiple command have this name:");
							matchingCmds.forEach(c -> out.println(c.names));
						} else {
							matchingCmds.forEach(cmd -> cmd.f(out, commandLine, client));
						}
					}

					client.close();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

	public static abstract class Command {
		final List<String> names;

		public Command(String... names) {
			this.names = Arrays.asList(names);
		}

		abstract void f(PrintStream out, List<String> parms, Socket client);
	}
}
