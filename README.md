# cpd
Use case to consume, parse and distribute data at high rate. Application Such as real time stock market data processing, Real time stream processing, Conversion of data are some of example where this architecture can be used.

Interfaces Consumer, Distributor and Parser are what is normal steps involved. Integration of these using Threads (under cp.thread package) is what is useful architecture to use with shared queues.

Though it is not shown in the code, you are free to add any number of thread instances each working on separate or shared queue. Even the choice of having 1 consumer, 5 parser thread, and 10 distributor can be accomplished by integrating different components as shown in Test.java
	