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
			// fetch data from tcp connection and put to queue, for now just put
			// dummy message every 100 mills
			Random r = new Random(System.currentTimeMillis());
			int nameRandom = r.nextInt(1000);
			int empIdRandom = r.nextInt(10000);
			int ageRandom = r.nextInt(50);
			String data = "name:name" + nameRandom + ",empid:" + empIdRandom + ", age:" + ageRandom;
			onMessage(data.getBytes());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
