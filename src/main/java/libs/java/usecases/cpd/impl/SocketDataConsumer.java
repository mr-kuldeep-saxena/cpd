package libs.java.usecases.cpd.impl;

import libs.java.usecases.cpd.Consumer;
import libs.java.usecases.cpd.ds.ListDataStore;

/**
 * Consumer, which listens for data over tcp connection and put data on queue
 * (shared with parser). You can have different implementations for this
 * 
 * @author Kuldeep
 *
 */
public class SocketDataConsumer implements Consumer<byte[]> {

	private ListDataStore<byte[]> queue;

	public void onMessage(byte[] element) {
		queue.put(element);
	}

	public SocketDataConsumer(ListDataStore<byte[]> queue) {
		this.queue = queue;
	}

}
