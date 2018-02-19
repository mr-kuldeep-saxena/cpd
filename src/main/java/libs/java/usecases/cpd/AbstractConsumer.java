package libs.java.usecases.cpd;

import libs.java.usecases.cpd.ds.ListDataStore;

/**
 * Default abstraction of Consumer to add queue to get data from. In combination
 * with Thread it is used to initiate consume operation
 * 
 * @author Kuldeep
 *
 * @param <V>
 */
public abstract class AbstractConsumer<V> implements Consumer<V> {

	protected ListDataStore<V> sharedQueue;

	protected AbstractConsumer(ListDataStore<V> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}
}
