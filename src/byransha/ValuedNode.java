package byransha;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import toools.SizeOf;
import toools.text.TextUtilities;

public abstract class ValuedNode<V> extends GOBMNode {
	V value;

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public void forEachOut(BiConsumer<String, GOBMNode> consumer) {
	}

	public abstract void fromString(String s);

	@Override
	public int distanceToSearchString(String searchString) {
		return TextUtilities.computeLevenshteinDistance(searchString, get().toString());
	}

	public V get() {
		return value;
	}

	public void set(V newValue) {
		this.value = newValue;
		saveValue(DB.sysoutPrinter);
	}

	public void saveValue(Consumer<File> writingFiles) {
		var valueFile = new File(directory(), "value.txt");

		if (!valueFile.getParentFile().exists()) {
			writingFiles.accept(valueFile.getParentFile());
			valueFile.getParentFile().mkdirs();
		}

		writingFiles.accept(valueFile);

		try {
			Files.write(valueFile.toPath(), get().toString().getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void loadValue(Consumer<File> readingFiles) throws IOException {
		var valueFile = new File(directory(), "value.txt");
		readingFiles.accept(valueFile);
		byte[] bytes = Files.readAllBytes(valueFile.toPath());
		fromString(new String(bytes));
	}

	@Override
	public long sizeOf() {
		return SizeOf.sizeOf(value);
	}
}
