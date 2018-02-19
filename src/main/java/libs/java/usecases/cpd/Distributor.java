package libs.java.usecases.cpd;

public interface Distributor<V> {
	public void onMessage (V message);
}
