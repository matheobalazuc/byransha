package byransha;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import toools.text.TextUtilities;

public abstract class ValuedNode<V> extends BNode {
	V value;

	@Override
	public void forEachOut(BiConsumer<String, BNode> consumer) {
	}

	public abstract void fromString(String s);

	@Override
	public int distanceToSearchString(String searchString) {
		return TextUtilities.computeLevenshteinDistance(searchString, get().toString());
	}

	public V get() {
		if (value == null) {
			try {
				loadValue(f -> System.out.println("loading value for " + this));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return value;
	}

	public void set(V newValue) {
		this.value = newValue;

		if (directory() != null) {
			saveValue(DB.sysoutPrinter);
		}
	}

	public void saveValue(Consumer<File> writingFiles) {
		var valueFile = new File(directory(), "value.txt");

		if (!valueFile.getParentFile().exists()) {
			writingFiles.accept(valueFile.getParentFile());
			valueFile.getParentFile().mkdirs();
		}

		writingFiles.accept(valueFile);

		try {
			Files.write(valueFile.toPath(), value.toString().getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void loadValue(Consumer<File> readingFiles) throws IOException {
		var valueFile = new File(directory(), "value.txt");

		if (valueFile.exists()) {
			readingFiles.accept(valueFile);
			byte[] bytes = Files.readAllBytes(valueFile.toPath());
			fromString(new String(bytes));
		}
	}
}
