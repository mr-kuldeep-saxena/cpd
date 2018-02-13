package libs.java.usecases.cpd.impl;

import java.net.Socket;

import libs.java.usecases.cpd.AbstractConsumer;
import libs.java.usecases.cpd.DataStore;

public class SocketDataConsumer extends AbstractConsumer<byte[]> {

	public void onMessage(byte[] element) {
		sharedQueue.add(element);
	}

	public SocketDataConsumer(String ip, int port, DataStore<byte[]> queue) {
		super(queue);
		new Thread(new DataReader(null)).start();
	}

	class DataReader implements Runnable {

		public DataReader(Socket socket) {
		}

		public void run() {
			onMessage(null);

		}

	}
}
