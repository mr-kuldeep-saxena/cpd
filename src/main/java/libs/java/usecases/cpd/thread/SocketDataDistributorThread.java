package libs.java.usecases.cpd.thread;

import java.util.List;

import libs.java.usecases.cpd.Distributor;
import libs.java.usecases.cpd.ds.ListDataStore;
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
		long count = 0;
		long startTime = System.currentTimeMillis();
		long lastPrintCount = count;
		while (true) {
			// blocks
			List<ParsedBean> messages = queue.removeAll();
			
			for (ParsedBean message : messages) {
				distributor.onMessage(message);

			}
			count++;
			if ((System.currentTimeMillis() - (60 *1000)) > startTime){
				startTime = System.currentTimeMillis();
				System.out.println("Number of message Distributed in last 1 minute. " + (count-lastPrintCount));
				System.out.println("Total message Distributed. " + count);
				
				lastPrintCount = count;
				
			}
		}

	}
}
