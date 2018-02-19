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
import libs.java.usecases.cpd.thread.SocketDataConsumerThread;
import libs.java.usecases.cpd.thread.SocketDataDistributorThread;
import libs.java.usecases.cpd.thread.SocketDataParserThread;

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
		// leaving socket and connection out for this sample
		ex.execute(new SocketDataConsumerThread(null, new SocketDataConsumer(channelData)));
		SocketDataParser parser = new SocketDataParser();
		ex.execute(new SocketDataParserThread(channelData, parsedData, parser));
		Consumer<ParsedBean> printConsumer = new Consumer<ParsedBean>() {

			@Override
			public void onMessage(ParsedBean element) {
		//		System.out.println("Received data in Distributor's consumer : " + element);
			}
		};
		SocketDataDistributor distributor = new SocketDataDistributor();
		distributor.addConsumer(printConsumer);
		ex.execute(new SocketDataDistributorThread(parsedData, distributor));

	}
}
