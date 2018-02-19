package libs.java.usecases.cpd.thread;

import java.util.List;

import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.impl.ParsedBean;
import libs.java.usecases.cpd.impl.SocketDataConsumer;
import libs.java.usecases.cpd.impl.SocketDataParser;

/**
 * Parser thread, get data from {@link SocketDataConsumer} and put in out queue
 * after parsing, which is consumed by distributor
 * 
 * @author Kuldeep
 *
 */
public class SocketDataParserThread implements Runnable {
	private ListDataStore<byte[]> inQueue;
	private ListDataStore<ParsedBean> outQueue;
	private SocketDataParser parser;

	public SocketDataParserThread(ListDataStore<byte[]> inQueue, ListDataStore<ParsedBean> outQueue,
			SocketDataParser parser) {
		this.inQueue = inQueue;
		this.parser = parser;
		this.outQueue = outQueue;

	}

	public void run() {
		long count = 0;
		long startTime = System.currentTimeMillis();
		long lastPrintCount = count;
		while (true) {
			// blocks
			List<byte[]> messages = inQueue.removeAll();
			for (byte[] message : messages) {
				ParsedBean parsed = parser.parse(message);
				if (parsed != null) {
					outQueue.put(parsed);
				}
			}
			count++;
			if ((System.currentTimeMillis() - (60 *1000)) > startTime){
				startTime = System.currentTimeMillis();
				System.out.println("Number of message Parsed in last 1 minute. " + (count-lastPrintCount));
				System.out.println("Total Number of message Parsed. " + count);
				
				lastPrintCount = count;
				
			}
		}
	}

}
