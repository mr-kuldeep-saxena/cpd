package libs.java.usecases.cpd.ds.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.java.usecases.cpd.ds.ListDataStore;
import libs.java.usecases.cpd.ds.MapListDataStore;

/**
 * Blocking implementation of {@link MapListDataStore}
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */
public class BlockingMemoryMapListDataStore<K, T> extends AbstractMapListDataStore<K, T> {
	private Map<K, ListDataStore<T>> storage = null;

	public BlockingMemoryMapListDataStore(MapListDataStore<K, T> dataStore, long capacity) {
		super(dataStore, capacity);
		storage = new HashMap<K, ListDataStore<T>>();
	}

	/**
	 * Data storage
	 */

	@Override
	public List<T> remove(K key, int size) {

		synchronized (storage) {
			if (size < 1 && size != -1) {
				storage.notifyAll();
				return null;
			}
			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			int recordsSize = storage.get(key).size();
			int availableSize = recordsSize;
			if (size == -1) {
				size = recordsSize;
			} else {
				availableSize = size;
			}
			if (recordsSize < size) {
				availableSize = recordsSize;
			}
			List<T> returnedRecords = storage.get(key).remove(availableSize);
			/*
			 * List<T> returnedRecordCopy = new LinkedList<T>();
			 * System.arraycopy(returnedRecords, 0, returnedRecordCopy, 0,
			 * returnedRecords.size()); returnedRecords.clear();
			 */
			storage.notifyAll();
			return returnedRecords;
		}
	}

	@Override
	public List<T> remove(K key, int size, long startTime, long endTime) {
		synchronized (storage) {
			if (size < 1 && size != -1) {
				storage.notifyAll();
				return null;
			}

			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			int recordsSize = storage.get(key).size();
			int availableSize = recordsSize;
			if (size == -1) {
				size = recordsSize;
			} else {
				availableSize = size;
			}
			if (recordsSize < size) {
				availableSize = recordsSize;
			}

			List<T> availableRecordsInTimeSpan = storage.get(key).remove(availableSize, startTime, endTime);
			storage.notifyAll();
			return availableRecordsInTimeSpan;
		}
	}

	@Override
	public List<T> remove(K key, long startTime, long endTime) {
		synchronized (storage) {

			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}
			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}
			List<T> availableRecordsInTimeSpan = storage.get(key).remove(startTime, endTime);
			storage.notifyAll();
			return availableRecordsInTimeSpan;
		}
	}

	@Override
	public List<T> removeAll(K key) {
		List<T> records = null;
		synchronized (storage) {
			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}
			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			records = storage.get(key).removeAll();
			/*
			 * List<T> returnedRecords = records.subList(0, records.size() - 1);
			 * List<T> returnedRecordCopy = new LinkedList<T>();
			 * System.arraycopy(returnedRecords, 0, returnedRecordCopy, 0,
			 * returnedRecords.size()); returnedRecords.clear();
			 */
			storage.notifyAll();
			return records;

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
			if (size < 1 && size != -1) {
				storage.notifyAll();
				return null;
			}

			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			int recordsSize = storage.get(key).size();
			int availableSize = recordsSize;
			if (size == -1) {
				size = recordsSize;
			} else {
				availableSize = size;
			}
			if (recordsSize < size) {
				availableSize = recordsSize;
			}

			List<T> returnedRecords = storage.get(key).get(availableSize);
			storage.notifyAll();
			return returnedRecords;
		}

	}

	// duplicate code, can be cleaned later for remove and copy
	@Override
	public List<T> get(K key, int size, long startTime, long endTime) {

		synchronized (storage) {
			if (size < 1 && size != -1) {
				storage.notifyAll();
				return null;
			}

			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			int recordsSize = storage.get(key).size();
			int availableSize = recordsSize;
			if (size == -1) {
				size = recordsSize;
			} else {
				availableSize = size;
			}
			if (recordsSize < size) {
				availableSize = recordsSize;
			}
			List<T> availableRecordsInTimeSpan = storage.get(key).get(availableSize, startTime, endTime);
			storage.notifyAll();
			return availableRecordsInTimeSpan;
		}

	}

	@Override
	public List<T> get(K key, long startTime, long endTime) {
		synchronized (storage) {
			while (!storage.containsKey(key)) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			while (storage.get(key).size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}

			List<T> availableRecordsInTimeSpan = storage.get(key).get(startTime, endTime);
			storage.notifyAll();
			return availableRecordsInTimeSpan;
		}
	}

	@Override
	public T put(K key, T data, long time) {
		synchronized (storage) {
			if (!storage.containsKey(key)) {
				storage.put(key,  new MemoryListDataStore<T>(null, -1));
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

	@Override
	public List<T> removeAll() {
		synchronized (storage) {
			while (storage.size() < 1) {
				try {
					storage.wait();
				} catch (InterruptedException e) {
				}
			}
			
			List<K> keys = new ArrayList<>();
			List<T> data = new ArrayList<>();
			
	 		for (K key : storage.keySet()) {
				keys.add(key);
			}
			
			for (K key: keys){
				data.addAll(storage.remove(key).removeAll());
			}
			// pending - lock in case of no values
			storage.notifyAll();
			return data;
		}

	}
}
