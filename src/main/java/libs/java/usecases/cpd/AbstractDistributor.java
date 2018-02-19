package libs.java.usecases.cpd;

import java.util.ArrayList;
import java.util.List;

import libs.java.usecases.cpd.ds.ListDataStore;

public abstract class AbstractDistributor<V> implements Distributor<V>{

	protected ListDataStore<V> queue;
	protected List<Consumer<V>> consumers = new ArrayList<Consumer<V>>();
	
	public AbstractDistributor(ListDataStore<V> queue) {
		this.queue = queue;
	}
	
}
