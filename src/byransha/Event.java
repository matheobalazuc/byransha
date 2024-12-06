package byransha;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public abstract class Event<N extends BNode> implements Serializable, Comparable<Event<N>> {
	Instant date;
	final N target;

	public Event(N target) {
		this.target = target;
	}

	public abstract void apply(N system);

	public abstract void undo(N system);

	public abstract void initFromCSV(List<String> a, N s);

	public int compareTo(Event<N> e) {
		return date.compareTo(e.date);
	}

	@Override
	public int hashCode() {
		return date.hashCode();
	}

	@Override
	public boolean equals(Object e) {
		return date == ((Event) e).date;
	}
}
