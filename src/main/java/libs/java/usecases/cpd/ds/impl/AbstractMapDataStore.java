package libs.java.usecases.cpd.ds.impl;

import java.util.Collection;
import java.util.Set;

import libs.java.usecases.cpd.ds.MapDataStore;

public class AbstractMapDataStore<K, T> implements MapDataStore<K, T> {

	protected long capacity;
	protected MapDataStore<K, T> dataStore;

	public AbstractMapDataStore(MapDataStore<K, T> dataStore, long capacity) {
		this.capacity = capacity;
		this.dataStore = dataStore;
	}

	public AbstractMapDataStore(MapDataStore<K, T> dataStore) {
		this.dataStore = dataStore;
	}

	public AbstractMapDataStore() {

	}

	@Override
	public T put(K key, T data) {
		return dataStore.put(key, data);
	}

	@Override
	public void clear() {
		dataStore.clear();
	}

	@Override
	public T get(K key) {
		return dataStore.get(key);
	}

	public boolean containsKey(K key) {
		return dataStore.containsKey(key);
	}

	@Override
	public Collection<T> removeAll() {
		
		return dataStore.removeAll();
	}

	@Override
	public T remove(K key) {
		return dataStore.remove(key);
	}

	@Override
	public Set<K> keySet() {
		return dataStore.keySet();
	}

	@Override
	public int size() {
		return dataStore.size();
	}
}