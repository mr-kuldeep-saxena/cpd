package libs.java.usecases.cpd.ds.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import libs.java.usecases.cpd.ds.MapDataStore;

/**
 * Non Blocking implementation of {@link MemoryMapDataStore}
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */
public class MemoryMapDataStore<K, T> extends AbstractMapDataStore<K, T> {
	private Map<K, T> storage = null;

	public MemoryMapDataStore(MapDataStore<K, T> dataStore, long capacity) {
		super(dataStore, capacity);
		storage = new HashMap<K, T>();
	}

	/**
	 * Data storage
	 */

	@Override
	public T put(K key, T data) {
		synchronized (storage) {
			storage.put(key, data);
			storage.notifyAll();
		}
		return data;
	}

	public String toString() {
		if (storage != null) {
			return storage.toString();
		}
		return null;
	}

	@Override
	public void clear() {
		synchronized (storage) {
			storage.clear();
			storage.notifyAll();
		}
	}

	@Override
	public boolean containsKey(K key) {
		return storage.containsKey(key);
	}

	@Override
	public T get(K key) {
		T data;
		synchronized (storage) {
			data = storage.get(key);
			storage.notifyAll();
		}
		return data;
	}

	@Override
	public T remove(K key) {
		T data;
		synchronized (storage) {
			data = storage.remove(key);
			storage.notifyAll();
		}
		return data;
	}

	@Override
	public Collection<T> removeAll() {
		synchronized (storage) {
			Collection<T> dataSet;
			dataSet = storage.values();
			Collection<T> copy = new ArrayList<>();
			for (T record : dataSet) {
				copy.add(record);
			}
			storage.clear();
			storage.notifyAll();
			return copy;
		}

	}

	@Override
	public Set<K> keySet() {
		return storage.keySet();
	}

	@Override
	public int size() {
		return storage.size();
	}
}
