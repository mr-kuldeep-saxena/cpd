package libs.java.usecases.cpd;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDistributor<V> implements Distributor<V>{

	protected DataStore<V> queue;
	protected List<Consumer<V>> consumers = new ArrayList<Consumer<V>>();
	
	public AbstractDistributor(DataStore<V> queue) {
		this.queue = queue;
	}
	
}
