package libs.java.usecases.cpd.thread;

import java.util.List;

import libs.java.usecases.cpd.Distributor;
import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.impl.ParsedBean;
import libs.java.usecases.cpd.impl.SocketDataDistributor;

/**
 * Distributor thread, removes all bean and put in distributor to progress
 * further
 * 
 * @author Kuldeep
 *
 */
public class SocketDataDistributorThread implements Runnable {

	private ListDataStore<ParsedBean> queue;
	private Distributor<ParsedBean> distributor;

	public SocketDataDistributorThread(ListDataStore<ParsedBean> queue, SocketDataDistributor distributor) {
		this.queue = queue;
		this.distributor = distributor;

	}

	public void run() {
		while (true) {
			// blocks
			List<ParsedBean> messages = queue.removeAll();
			for (ParsedBean message : messages) {
				distributor.onMessage(message);

			}
		}

	}
}
