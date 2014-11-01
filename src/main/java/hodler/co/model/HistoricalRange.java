package hodler.co.model;

public class HistoricalRange {

	private final int from;
	private final int to;

	public HistoricalRange(final int from, final int to) {
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

}
