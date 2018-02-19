package libs.java.usecases.cpd.impl;

import java.util.Random;

import libs.java.usecases.cpd.AbstractConsumer;
import libs.java.usecases.cpd.ds.ListDataStore;

public class SocketDataConsumer extends AbstractConsumer<byte[]> implements Runnable {

	public void onMessage(byte[] element) {
		sharedQueue.put(element);
	}

	public SocketDataConsumer(String ip, int port, ListDataStore<byte[]> queue) {
		super(queue);
	}

	public void run() {
		while (true) {
			// fetch data from tcp connection and put to queue (like Stock
			// Market Data), for sample just putting
			// dummy message every 1 millisecond
			Random r = new Random(System.currentTimeMillis());
			int nameRandom = r.nextInt(1000);
			int empIdRandom = r.nextInt(10000);
			int ageRandom = r.nextInt(50);
			String data = "name:name" + nameRandom + ",empid:" + empIdRandom + ", age:" + ageRandom;
			onMessage(data.getBytes());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
