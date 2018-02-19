package libs.java.usecases.cpd.impl;

import java.util.List;

import libs.java.usecases.cpd.AbstractParser;
import libs.java.usecases.cpd.ds.ListDataStore;

public class SocketDataParser extends AbstractParser<byte[], ParsedBean> implements Runnable {

	public SocketDataParser(ListDataStore<byte[]> inQueue, ListDataStore<ParsedBean> outQueue) {
		super(inQueue, outQueue);
		new Thread().start();
	}

	public ParsedBean parse(byte[] value) {
		ParsedBean bean = new ParsedBean();
		String splittedFields[] = new String(value).split(",");
		bean.setName(splittedFields[0].substring(splittedFields[0].indexOf(":") + 1, splittedFields[0].length()));
		bean.setEmpId(Integer.parseInt(splittedFields[1].substring(splittedFields[1].indexOf(":") + 1, splittedFields[1].length())));
		bean.setAge(Integer.parseInt(splittedFields[2].substring(splittedFields[2].indexOf(":") + 1, splittedFields[2].length())));

		return bean;
	}

	public void run() {
		while (true) {
			// blocks
			List<byte[]> messages = inQueue.removeAll();
			for (byte[] message : messages) {
				ParsedBean parsed = parse(message);
				if (parsed != null) {
					outQueue.put(parsed);
				}

			}
		}
	}

}
