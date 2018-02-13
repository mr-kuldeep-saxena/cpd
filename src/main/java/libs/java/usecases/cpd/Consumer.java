package libs.java.usecases.cpd;

public interface Consumer<V> {
	public void onMessage(V element);
}
