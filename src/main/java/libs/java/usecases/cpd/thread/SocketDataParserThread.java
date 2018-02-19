package libs.java.usecases.cpd.thread;

import java.util.List;

import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.impl.ParsedBean;
import libs.java.usecases.cpd.impl.SocketDataParser;

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
		while (true) {
			// blocks
			List<byte[]> messages = inQueue.removeAll();
			for (byte[] message : messages) {
				ParsedBean parsed = parser.parse(message);
				if (parsed != null) {
					outQueue.put(parsed);
				}

			}
		}
	}

}
