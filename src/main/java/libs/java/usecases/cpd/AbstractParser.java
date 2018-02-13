package libs.java.usecases.cpd;

public abstract class AbstractParser<I,O> implements Parser<I, O>{

	protected DataStore<I> inQueue;
	protected DataStore<O> outQueue;
	public AbstractParser(DataStore<I> inQueue, DataStore<O> outQueue) {
		this.inQueue = inQueue;
		this.outQueue = outQueue;
	}
}
