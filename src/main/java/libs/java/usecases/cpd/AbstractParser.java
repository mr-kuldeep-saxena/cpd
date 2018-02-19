package libs.java.usecases.cpd;

import libs.java.usecases.cpd.ds.ListDataStore;

public abstract class AbstractParser<I,O> implements Parser<I, O>{

	protected ListDataStore<I> inQueue;
	protected ListDataStore<O> outQueue;
	public AbstractParser(ListDataStore<I> inQueue, ListDataStore<O> outQueue) {
		this.inQueue = inQueue;
		this.outQueue = outQueue;
	}
}
