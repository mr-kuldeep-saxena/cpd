package libs.java.usecases.cpd.test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import libs.java.usecases.cpd.Consumer;
import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.ds.impl.BlockingMemoryListDataStore;
import libs.java.usecases.cpd.impl.ParsedBean;
import libs.java.usecases.cpd.impl.SocketDataConsumer;
import libs.java.usecases.cpd.impl.SocketDataDistributor;
import libs.java.usecases.cpd.impl.SocketDataParser;

public class Test {

	public static void main(String[] args) {

		// DataStore has many features, actually it is part of other
		// application, reutilized here, but we would be using only some fetures
		// of it. As well, only List Part of it to show functionality.
		// To utilize data store for other use cases look into ListDataStore,
		// MapDataStore and MapListDataStore

		// 3 thread/process, uses 2 shared queue
		ListDataStore<byte[]> channelData = new BlockingMemoryListDataStore<>(null, -1);

		ListDataStore<ParsedBean> parsedData = new BlockingMemoryListDataStore<>(null, -1);

		Executor ex = Executors.newFixedThreadPool(3);
		ex.execute(new SocketDataConsumer("ip", 10000, channelData));
		ex.execute(new SocketDataParser(channelData, parsedData));
		Consumer<ParsedBean> printConsumer = new Consumer<ParsedBean>() {

			@Override
			public void onMessage(ParsedBean element) {
				System.out.println("Received data in Distributor's consumer : " + element);
			}
		};
		SocketDataDistributor distributor = new SocketDataDistributor(parsedData);
		distributor.addConsumer(printConsumer);
		ex.execute(distributor);

	}
}
