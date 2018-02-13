package libs.java.usecases.cpd.impl;

import libs.java.usecases.cpd.AbstractDistributor;
import libs.java.usecases.cpd.Consumer;
import libs.java.usecases.cpd.DataStore;

public class SocketDataDistributor extends AbstractDistributor<Object> {
	
	public SocketDataDistributor(DataStore<Object> queue) {
		super(queue);
	}
	
	public void onMessage(Object message) {
		// TODO Auto-generated method stub
		for (Consumer<Object> consumer:consumers){
			consumer.onMessage(message);
		}
	}

	class DistributorThread implements Runnable {

		public void run() {
			onMessage(null);
			
		}

	}

	public void addConsumer(Consumer<Object> consumer) {
		// TODO Auto-generated method stub
		
	}

	public void removeConsumer(Consumer<Object> consumer) {
		// TODO Auto-generated method stub
		
	}

}
