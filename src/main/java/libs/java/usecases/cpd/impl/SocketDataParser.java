package libs.java.usecases.cpd.impl;

import libs.java.usecases.cpd.AbstractParser;
import libs.java.usecases.cpd.DataStore;

public class SocketDataParser extends AbstractParser<byte[], Object> {

	public SocketDataParser(DataStore<byte[]> inQueue, DataStore<Object> outQueue) {
		super(inQueue, outQueue);
		new Thread().start();
	}

	public Object parse(byte[] value) {
		// parsed bean
		outQueue.add(null);
		return null;
	}

	class ParserThread implements Runnable {
		public void run() {
			for (int i = 0; i < inQueue.size(); i++) {
				parse(inQueue.remove(i));
			}
		}

	}
}
