package libs.java.usecases.cpd;

/**
 * Consumer for message, notified on new message
 * @author Kuldeep
 *
 * @param <V>
 */
public interface Consumer<V> {
	public void onMessage(V element);
}
