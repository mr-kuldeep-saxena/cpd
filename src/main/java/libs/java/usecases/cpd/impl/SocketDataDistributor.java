package libs.java.usecases.cpd.impl;

import java.util.List;

import libs.java.usecases.cpd.AbstractDistributor;
import libs.java.usecases.cpd.Consumer;
import libs.java.usecases.cpd.ds.ListDataStore;

public class SocketDataDistributor extends AbstractDistributor<ParsedBean> implements Runnable {

	public SocketDataDistributor(ListDataStore<ParsedBean> queue) {
		super(queue);
	}

	public void onMessage(ParsedBean message) {
		for (Consumer<ParsedBean> consumer : consumers) {
			consumer.onMessage(message);
		}
	}

	public void run() {
		while (true) {
			// blocks
			List<ParsedBean> messages = queue.removeAll();
			for (ParsedBean message : messages) {
				onMessage(message);

			}
		}

	}

	public void addConsumer(Consumer<ParsedBean> consumer) {
		consumers.add(consumer);
	}

	public void removeConsumer(Consumer<ParsedBean> consumer) {
		consumers.remove(consumer);
	}

}
