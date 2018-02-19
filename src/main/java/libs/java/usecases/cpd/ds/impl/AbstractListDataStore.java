package libs.java.usecases.cpd.ds.impl;

import java.util.Collection;
import java.util.List;

import libs.java.usecases.cpd.ds.ListDataStore;

public abstract class AbstractListDataStore<T> implements ListDataStore<T> {

	protected long capacity;
	protected ListDataStore<T> dataStore;

	public AbstractListDataStore(ListDataStore<T> dataStore, long capacity) {
		this.capacity = capacity;
		this.dataStore = dataStore;
	}

	@Override
	public List<T> get(int size) {
		return dataStore.get(size);
	}

	@Override
	public List<T> get(long startTime, long endTime) {
		return dataStore.get(startTime, endTime);
	}

	@Override
	public int size() {
		return dataStore.size();
	}

	@Override
	public T put(T data) {
		return dataStore.put(data);
	}

	@Override
	public void putAll(Collection<T> data) {
		dataStore.putAll(data);
	}

	@Override
	public List<T> remove(int size) {
		return dataStore.remove(size);
	}

	@Override
	public List<T> removeAll() {
		return dataStore.removeAll();
	}

	@Override
	public T put(T data, long insertTime) {
		return dataStore.put(data, insertTime);
	}

	@Override
	public List<T> get(int size, long startTime, long endTime) {
		return dataStore.get(size, startTime, endTime);
	}

	@Override
	public List<T> remove(int size, long startTime, long endTime) {
		return dataStore.remove(size, startTime, endTime);
	}

	@Override
	public List<T> remove(long startTime, long endTime) {
		return dataStore.remove(startTime, endTime);
	}

	@Override
	public List<T> getAll() {
		return dataStore.getAll();
	}

	public T remove(T data) {
		return dataStore.remove(data);
	}

}