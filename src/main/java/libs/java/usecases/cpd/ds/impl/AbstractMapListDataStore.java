package libs.java.usecases.cpd.ds.impl;

import java.util.Collection;
import java.util.List;

import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.ds.MapListDataStore;

public class AbstractMapListDataStore<K, T> implements MapListDataStore<K, T> {

	protected long capacity;
	protected MapListDataStore<K, T> dataStore;

	public AbstractMapListDataStore(MapListDataStore<K, T> dataStore, long capacity) {
		this.capacity = capacity;
		this.dataStore = dataStore;
	}

	public AbstractMapListDataStore(MapListDataStore<K, T> dataStore) {
		this.dataStore = dataStore;
	}

	public AbstractMapListDataStore() {

	}

	@Override
	public List<T> get(K key, int size) {
		return dataStore.get(key, size);
	}

	@Override
	public List<T> get(K key, int size, long startTime, long endTime) {
		return dataStore.get(key, size, startTime, endTime);
	}

	@Override
	public List<T> get(K key, long startTime, long endTime) {
		return dataStore.get(key, startTime, endTime);
	}

	@Override
	public List<T> remove(K key, int size) {
		return dataStore.remove(key, size);
	}

	@Override
	public T put(K key, T data) {
		return dataStore.put(key, data);
	}

	@Override
	public List<T> remove(K key, int size, long startTime, long endTime) {
		return dataStore.remove(key, size, startTime, endTime);
	}

	@Override
	public List<T> remove(K key, long startTime, long endTime) {
		return dataStore.remove(key, startTime, endTime);
	}

	@Override
	public List<T> removeAll(K key) {
		return dataStore.removeAll(key);
	}


	@Override
	public T put(K key, T data, long time) {
		return dataStore.put(key, data, time);
	}

	public ListDataStore<T> getUnderlyingStorage(K key) {
		return dataStore.getUnderlyingStorage(key);

	}

	@Override
	public boolean containsKey(K key) {
		return dataStore.containsKey(key);
	}

	@Override
	public void clear() {
		dataStore.clear();
	}

	@Override
	public List<T> get(K key) {
		return dataStore.get(key);
	}

	@Override
	public void clear(K key) {
		dataStore.clear(key);
	}

	@Override
	public Collection<T> removeAll() {
		return dataStore.removeAll();
	}

}