package libs.java.usecases.cpd.ds.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import libs.java.usecases.cpd.ds.MapDataStore;

/**
 * Blocking implementation of {@link BlockingMemoryMapDataStore}
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */
public class BlockingMemoryMapDataStore<K, T> extends AbstractMapDataStore<K, T> {

	// ListDataStore<T> storage;
	// private TimeSeriesDataMap<K, T> storage;
	private Map<K, T> storage = null;

	public BlockingMemoryMapDataStore(MapDataStore<K, T> dataStore, long capacity) {
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
	public Collection<T> removeAll() {
		synchronized (storage) {
			Collection<T> recordsList = null;
			
			while (storage.size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}
			recordsList = storage.values();
			Collection<T> copy = new ArrayList<>();
			for (T record:recordsList){
				copy.add(record);
			}
			storage.clear();
			storage.notifyAll();
			return copy;
		}
	}

	@Override
	public int size (){
		return storage.size();
	}
}
