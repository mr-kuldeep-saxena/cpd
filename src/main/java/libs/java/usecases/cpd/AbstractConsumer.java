package libs.java.usecases.cpd;

public abstract class AbstractConsumer<V> implements Consumer<V>{

	protected DataStore<V> sharedQueue;
	protected AbstractConsumer(DataStore<V> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}
}
