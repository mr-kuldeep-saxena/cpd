package libs.java.usecases.cpd.ds.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import libs.java.usecases.cpd.ds.ListDataStore;

/**
 * A non blocking, thread safe implementation of {@link ListDataStore}
 * 
 * @author Kuldeep
 *
 * @param <T>
 */

public class MemoryListDataStore<T> extends AbstractListDataStore<T> {

	private TimeSeriesDataList<T> storage;

	public MemoryListDataStore(ListDataStore<T> dataStore, long capacity) {
		super(dataStore, capacity);
		this.storage = new TimeSeriesDataList<T>();
	}

	@Override
	public List<T> get(int size) {
		synchronized (storage) {
			if (size < 1) {
				storage.notifyAll();
				return null;
			}
			List<T> recordsList = new LinkedList<T>();
			if (size > storage.size()) {
				size = storage.size();
			}

			recordsList = storage.subList(0, size - 1);
			storage.notifyAll();
			return recordsList;
		}
	}

	@Override
	public T put(T data) {
		return this.put(data, System.currentTimeMillis());

	}

	/**
	 * a blocking remove operation, wait if element not available
	 */

	@Override
	public List<T> remove(int size) {
		synchronized (storage) {
			if (size > 0 && storage.size() > 0) {
				int total = size;
				if (storage.size() < size) {
					total = storage.size();
				}
				List<T> returnedRecordCopy = storage.remove(total);
				storage.notifyAll();
				return returnedRecordCopy;
			} else {
				storage.notifyAll();
				return null;
			}
		}

	}

	/**
	 * a blocking remove operation, wait if element not available
	 */

	@Override
	public List<T> removeAll() {
		synchronized (storage) {
			List<T> recordsList = null;
			recordsList = storage.removeAll();
			storage.notifyAll();
			return recordsList;
		}
	}

	@Override
	public void putAll(Collection<T> data) {
		synchronized (storage) {
			for (T element : data) {
				storage.addElement(element, System.currentTimeMillis());
			}
			storage.notifyAll();
		}
	}

	@Override
	public void waitTillAvailable(int size, long timeInMills) {
	}

	@Override
	public T put(T data, long insertTime) {
		synchronized (storage) {
			storage.addElement(data, insertTime);
			storage.notifyAll();
		}
		return data;
	}

	@Override
	public List<T> get(int size, long startTime, long endTime) {
		synchronized (storage) {
			List<T> returnedRecords = storage.get(startTime, endTime, size);
			storage.notifyAll();
			return returnedRecords;
		}
	}

	@Override
	public List<T> getAll() {
		synchronized (storage) {
			List<T> returnedRecords = storage.getAll();
			storage.notifyAll();
			return returnedRecords;
		}
	}

	@Override
	public List<T> remove(int size, long startTime, long endTime) {
		synchronized (storage) {
			if (size > 0 && storage.size() > 0) {
				int total = size;
				if (storage.size() < size) {
					total = storage.size();
				}
				List<T> returnedRecordCopy = storage.remove(startTime, endTime, total);
				storage.notifyAll();
				return returnedRecordCopy;
			} else {
				storage.notifyAll();
				return null;
			}
		}
	}

	@Override
	public List<T> remove(long startTime, long endTime) {
		synchronized (storage) {
			List<T> recordsList = null;
			recordsList = storage.remove(startTime, endTime);
			storage.notifyAll();
			return recordsList;

		}
	}

	@Override
	public List<T> get(long startTime, long endTime) {
		List<TimeSeriesElement<T>> availableRecords = null;
		synchronized (storage) {
			availableRecords = storage.availableRecordsInTimeSpan(startTime, endTime);
			storage.notifyAll();
		}
		if (availableRecords == null || availableRecords.size() < 1) {
			return null;
		}
		List<T> records = new ArrayList<>(availableRecords.size());
		for (TimeSeriesElement<T> element : availableRecords) {
			records.add(element.getRecord());
		}
		return records;
	}

	@Override
	public int size() {
		return storage.size();
	}

	public String toString() {
		if (storage != null) {
			return storage.toString();
		}
		return null;
	}

	public T remove(T data) {
		synchronized (storage) {
			for (int i = 0; i < storage.size(); i++) {
				T storedData = storage.get(i);
				if (storedData == data) {
					storage.notifyAll();
					return storage.removeElementAt(i);
				}
			}
			storage.notifyAll();
		}
		return data;
	}

}
