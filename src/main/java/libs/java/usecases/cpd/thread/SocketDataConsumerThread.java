package libs.java.usecases.cpd.thread;

import java.net.Socket;
import java.util.Random;

import libs.java.usecases.cpd.Consumer;

/**
 * Thread to collect data from tcp, and put that in consumer queue
 * @author Kuldeep
 *
 */
public class SocketDataConsumerThread implements Runnable {
	private Consumer<byte[]> consumer;
	private Socket connection;

	public SocketDataConsumerThread(Socket connection, Consumer<byte[]> consumer) {
		this.consumer = consumer;
		this.connection = connection;
	}

	public void run() {
		long count = 0;
		long startTime = System.currentTimeMillis();
		long lastPrintCount = count;
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
			count++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if ((System.currentTimeMillis() - (60 *1000)) > startTime){
				startTime = System.currentTimeMillis();
				System.out.println("Number of message Generated in last 1 minute. " + (count-lastPrintCount));
				System.out.println("Total Number of message Generated . " + count);
				
				lastPrintCount = count;
				if (lastPrintCount > 100000){
					break;
				}
			}
			
		}

	}

}
