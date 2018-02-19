package libs.java.usecases.cpd.thread;

import java.net.Socket;
import java.util.Random;

import libs.java.usecases.cpd.Consumer;

public class SocketDataConsumerThread implements Runnable {
	private Consumer<byte[]> consumer;
	private Socket connection;

	public SocketDataConsumerThread(Socket connection, Consumer<byte[]> consumer) {
		this.consumer = consumer;
		this.connection = connection;
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
			consumer.onMessage(data.getBytes());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
