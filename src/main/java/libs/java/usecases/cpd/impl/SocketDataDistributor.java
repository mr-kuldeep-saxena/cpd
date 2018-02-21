package libs.java.usecases.cpd.impl;

import java.util.ArrayList;
import java.util.List;

import libs.java.usecases.cpd.Consumer;
import libs.java.usecases.cpd.Distributor;
import libs.java.usecases.cpd.thread.ParsedBean;

/**
 * Distributor, working as publisher as well for other consumers
 * @author Kuldeep
 *
 */
public class SocketDataDistributor implements Distributor<ParsedBean> {

	private List<Consumer<ParsedBean>> consumers = new ArrayList<>();

	public SocketDataDistributor() {
		super();
	}

	public void onMessage(ParsedBean message) {
		for (Consumer<ParsedBean> consumer : consumers) {
			consumer.onMessage(message);
		}
	}

	public void addConsumer(Consumer<ParsedBean> consumer) {
		consumers.add(consumer);
	}

	public void removeConsumer(Consumer<ParsedBean> consumer) {
		consumers.remove(consumer);
	}

}
