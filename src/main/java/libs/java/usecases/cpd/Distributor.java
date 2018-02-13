package libs.java.usecases.cpd;

public interface Distributor<V> {
	public void addConsumer (Consumer<V> consumer);
	public void removeConsumer (Consumer<V> consumer);
	public void onMessage (V message);
}
