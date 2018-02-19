package libs.java.usecases.cpd;

/**
 * Distributor, notified on new message, distributor can send data on its own or
 * can work in publish subscribe mode (playing role of publisher) and publish
 * data to different consumers (subscriber)
 * 
 * @author Kuldeep
 *
 * @param <V>
 */
public interface Distributor<V> {
	/**
	 * Call back method, called by thread to read from queue and notify
	 * accordingly
	 * 
	 * @param message
	 */
	public void onMessage(V message);
}
