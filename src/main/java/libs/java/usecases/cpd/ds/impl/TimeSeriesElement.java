package libs.java.usecases.cpd.ds.impl;

public class TimeSeriesElement<T> {

	private long time;
	private T record;

	public TimeSeriesElement(T record, long time) {
		this.record = record;
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "TimeSeriesElement [time=" + time + ", record=" + record + "]";
	}

}
