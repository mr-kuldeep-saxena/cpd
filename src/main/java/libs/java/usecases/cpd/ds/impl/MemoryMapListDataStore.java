package libs.java.usecases.cpd.ds.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.ds.MapListDataStore;

/**
 * Non Blocking implementation of {@link MapListDataStore}
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */
public class MemoryMapListDataStore<K, T> extends AbstractMapListDataStore<K, T> {

	// ListDataStore<T> storage;
	// private TimeSeriesDataMap<K, T> storage;
	private Map<K, ListDataStore<T>> storage = null;

	public MemoryMapListDataStore(MapListDataStore<K, T> dataStore, long capacity) {
		super(dataStore, capacity);
		storage = new HashMap<K, ListDataStore<T>>();
	}


	/**
	 * Data storage
	 */

	@Override
	public List<T> remove(K key, int size) {
		synchronized (storage) {
			if (storage.containsKey(key)) {
				int recordsSize = storage.get(key).size();
				int availableSize = size;
				if (recordsSize < size) {
					availableSize = recordsSize;
				}
				List<T> returnedRecords = storage.get(key).remove(availableSize - 1);
				storage.notifyAll();
				return returnedRecords;
			} else {
				storage.notifyAll();
				return null;
			}
		}
	}

	@Override
	public List<T> remove(K key, int size, long startTime, long endTime) {
		synchronized (storage) {
			if (storage.containsKey(key)) {
				List<T> availableRecordsInTimeSpan = storage.get(key).remove(size, startTime, endTime);
				storage.notifyAll();
				return availableRecordsInTimeSpan;
			}
			storage.notifyAll();
			return null;
		}
	}

	@Override
	public List<T> remove(K key, long startTime, long endTime) {
		synchronized (storage) {
			if (storage.containsKey(key)) {
				List<T> availableRecordsInTimeSpan = storage.get(key).remove(startTime, endTime);
				storage.notifyAll();
				return availableRecordsInTimeSpan;
			} else {
				storage.notifyAll();
				return null;
			}
		}
	}

	@Override
	public List<T> removeAll(K key) {
		List<T> records = null;
		synchronized (storage) {
			if (storage.containsKey(key)) {
				records = storage.get(key).removeAll();
				storage.notifyAll();
				return records;
			}
			storage.notifyAll();
			return null;
		}
	}

	@Override
	public T put(K key, T data) {
		synchronized (storage) {
			if (!storage.containsKey(key)) {
				storage.put(key, new MemoryListDataStore<T>(null, -1));
			}
			storage.get(key).put(data, System.currentTimeMillis());
			storage.notifyAll();
		}
		return data;
	}

	/**
	 * Returns a copy of list backed by original list, so any changes in
	 * original list will reflect to old one.
	 * 
	 * @param key
	 * @param size
	 * @return
	 */
	@Override
	public List<T> get(K key, int size) {
		synchronized (storage) {
			if (containsKey(key)) {
				int recordsSize = storage.get(key).size();
				if (recordsSize > 0) {
					if (size == -1) {
						size = recordsSize;
					}
					int availableSize = size;
					if (recordsSize < size) {
						availableSize = recordsSize;
					}
					List<T> returnedRecords = storage.get(key).get(availableSize);
					storage.notifyAll();
					return returnedRecords;
				} else {
					storage.notifyAll();
					return null;
				}
			} else {
				storage.notifyAll();
				return null;
			}
		}
	}

	// duplicate code, can be cleaned later for remove and copy
	@Override
	public List<T> get(K key, int size, long startTime, long endTime) {

		synchronized (storage) {
			if (storage.containsKey(key)) {
				int recordsSize = storage.get(key).size();
				if (recordsSize > 0) {
					if (size == -1) {
						size = recordsSize;
					}
					int availableSize = size;
					if (recordsSize < size) {
						availableSize = recordsSize;
					}
					List<T> availableRecordsInTimeSpan = storage.get(key).get(size, startTime, endTime);
					storage.notifyAll();
					return availableRecordsInTimeSpan;
				}
				storage.notifyAll();
				return null;
			} else {
				storage.notifyAll();
				return null;
			}
		}

	}

	@Override
	public List<T> get(K key, long startTime, long endTime) {
		synchronized (storage) {
			if (storage.containsKey(key)) {
				List<T> availableRecordsInTimeSpan = storage.get(key).get(startTime, endTime);
				storage.notifyAll();
				return availableRecordsInTimeSpan;
			} else {
				storage.notifyAll();
				return null;
			}
		}
	}

	@Override
	public T put(K key, T data, long time) {
		synchronized (storage) {
			if (!storage.containsKey(key)) {
				storage.put(key, (ListDataStore<T>) new MemoryListDataStore<T>(null, -1));
			}
			storage.get(key).put(data, time);
			storage.notifyAll();
		}
		return data;
	}

	public ListDataStore<T> getUnderlyingStorage(K key) {
		return storage.get(key);

	}

	public String toString() {
		if (storage != null) {
			return storage.toString();
		}
		return null;
	}

	@Override
	public void clear(K key) {
		synchronized (storage) {
			storage.remove(key);
			storage.notifyAll();
		}
	}

	@Override
	public void clear() {
		synchronized (storage) {
			storage.clear();
			storage.notifyAll();
		}
	}

	@Override
	public List<T> get(K key) {
		return get(key, -1);
	}

	@Override
	public boolean containsKey(K key) {
		return storage.containsKey(key);
	}

}
