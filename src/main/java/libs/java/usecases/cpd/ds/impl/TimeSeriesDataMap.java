package libs.java.usecases.cpd.ds.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimeSeriesDataMap<K, T> {

	private Map<K, List<TimeSeriesElement<T>>> timeSeriesElements = new ConcurrentHashMap<K, List<TimeSeriesElement<T>>>();

	public void addElement(K key, T record, long time) {
		List<TimeSeriesElement<T>> elements = timeSeriesElements.get(key);
		if (elements == null || elements.size() < 1) {
			elements = new LinkedList<TimeSeriesElement<T>>();
			timeSeriesElements.put(key, elements);
		}
		elements.add(new TimeSeriesElement<>(record, time));

	}

	public List<T> removeAll() {
		List<T> data = new LinkedList<T>();
		for (K key : timeSeriesElements.keySet()) {
			List<T> keyData = removeAll(key);
			if (keyData != null && keyData.size() > 0) {
				data.addAll(keyData);
			}
		}
		return data;
	}

	// remove is a slow process, until cleaning all elements
	public List<TimeSeriesElement<T>> copyElements(
			List<TimeSeriesElement<T>> source) {
		Object sourceObj[] = source.toArray();
		Object destinationObj[] = new Object[sourceObj.length];// destination.toArray();
		System.arraycopy(sourceObj, 0, destinationObj, 0, sourceObj.length);

		List<TimeSeriesElement<T>> returnedObjects = new ArrayList<>(
				destinationObj.length);

		for (Object obj : destinationObj) {
			returnedObjects.add((TimeSeriesElement<T>) obj);
		}

		return returnedObjects;
	}

	public List<T> remove(K key, long startTime, long endTime, int size) {
		List<TimeSeriesElement<T>> data = availableRecordsInTimeSpan(key,
				startTime, endTime);
		if (data.size() < 1) {
			return null;
		}
		int availableSize = size;
		if (data.size() < size) {
			availableSize = data.size();
		}
		List<TimeSeriesElement<T>> returnedRecords = data.subList(0,
				availableSize);
		List<TimeSeriesElement<T>> returnedRecordsCopy = copyElements(returnedRecords);
		returnedRecords.clear();
		List<T> records = new ArrayList<>(returnedRecordsCopy.size());
		for (TimeSeriesElement<T> record : returnedRecordsCopy) {
			records.add(record.getRecord());
		}
		return records;
	}

	public List<T> remove(K key, long startTime, long endTime) {
		List<TimeSeriesElement<T>> data = availableRecordsInTimeSpan(key,
				startTime, endTime);
		if (data.size() < 1) {
			return null;
		}
		List<TimeSeriesElement<T>> returnedRecords = data.subList(0,
				data.size());
		List<TimeSeriesElement<T>> returnedRecordsCopy = copyElements(returnedRecords);
		returnedRecords.clear();
		List<T> records = new ArrayList<>(returnedRecordsCopy.size());
		for (TimeSeriesElement<T> record : returnedRecordsCopy) {
			records.add(record.getRecord());
		}
		return records;
	}

	public List<T> removeAll(K key) {
		List<TimeSeriesElement<T>> data = timeSeriesElements.get(key);
		if (data == null || data.size() < 1) {
			return null;
		}
		List<TimeSeriesElement<T>> returnedRecordsCopy = timeSeriesElements
				.remove(key);
		List<T> records = new ArrayList<>(returnedRecordsCopy.size());
		for (TimeSeriesElement<T> record : returnedRecordsCopy) {
			records.add(record.getRecord());
		}
		return records;
	}

	public List<T> subList(K key, int fromIndex, int toIndex) {

		if (!timeSeriesElements.containsKey(key)) {
			return null;
		}

		List<TimeSeriesElement<T>> elements = timeSeriesElements.get(key);
		if (toIndex == -1) {
			toIndex = elements.size() - 1;
		}
		elements = elements.subList(fromIndex, toIndex);
		List<T> data = new ArrayList<T>(elements.size());
		for (TimeSeriesElement<T> element : elements) {
			data.add(element.getRecord());
		}
		return data;
	}

	public boolean containsKey(K key) {
		return timeSeriesElements.containsKey(key);
	}

	public int size() {
		return timeSeriesElements.size();
	}

	public List<T> get(K key) {
		return subList(key, 0, -1);
	}

	public int size(K key) {
		if (timeSeriesElements.containsKey(key)) {
			return timeSeriesElements.get(key).size();
		}
		return 0;
	}

	public List<TimeSeriesElement<T>> availableRecordsInTimeSpan(K key,
			long startTime, long endTime) {
		List<TimeSeriesElement<T>> availableRecordsInTimeSpan = new LinkedList<TimeSeriesElement<T>>();
		if (!timeSeriesElements.containsKey(key)) {
			return availableRecordsInTimeSpan;
		}
		for (TimeSeriesElement<T> record : timeSeriesElements.get(key)) {
			if (record.getTime() >= startTime || record.getTime() <= endTime) {
				availableRecordsInTimeSpan.add(record);
			}
		}
		return availableRecordsInTimeSpan;

	}

	@Override
	public String toString() {
		return "TimeSeriesData [timeSeriesElements=" + timeSeriesElements + "]";
	}

}
