package libs.java.usecases.cpd.ds.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TimeSeriesDataList<T> {

	private List<TimeSeriesElement<T>> timeSeriesElements = new LinkedList<TimeSeriesElement<T>>();

	public TimeSeriesDataList() {
	}

	public T get(int index) {
		return timeSeriesElements.get(index).getRecord();
	}

	public T removeElementAt(int index) {
		return timeSeriesElements.remove(index).getRecord();
	}

	public List<T> subList(int fromIndex, int toIndex) {

		List<TimeSeriesElement<T>> elements = timeSeriesElements.subList(fromIndex, toIndex);
		List<T> data = new ArrayList<T>(elements.size());
		for (TimeSeriesElement<T> element : elements) {
			data.add(element.getRecord());
		}
		return data;
	}

	public List<T> getAll() {
		List<TimeSeriesElement<T>> elements = timeSeriesElements.subList(0, timeSeriesElements.size());
		List<T> data = new ArrayList<T>(elements.size());
		for (TimeSeriesElement<T> element : elements) {
			data.add(element.getRecord());
		}
		return data;
	}

	public List<TimeSeriesElement<T>> copyElements(List<TimeSeriesElement<T>> source) {
		Object sourceObj[] = source.toArray();
		Object destinationObj[] = new Object[sourceObj.length];// destination.toArray();
		System.arraycopy(sourceObj, 0, destinationObj, 0, sourceObj.length);

		List<TimeSeriesElement<T>> returnedObjects = new ArrayList<>(destinationObj.length);

		for (Object obj : destinationObj) {
			returnedObjects.add((TimeSeriesElement<T>) obj);
		}

		return returnedObjects;
	}

	public List<T> remove(long startTime, long endTime, int size) {
		List<TimeSeriesElement<T>> data = availableRecordsInTimeSpan(startTime, endTime);
		if (data.size() < 1) {
			return null;
		}
		int availableSize = size;
		if (data.size() < size) {
			availableSize = data.size();
		}
		List<TimeSeriesElement<T>> returnedRecords = data.subList(0, availableSize - 1);
		List<TimeSeriesElement<T>> returnedRecordsCopy = copyElements(returnedRecords);
		returnedRecords.clear();
		List<T> records = new ArrayList<>(returnedRecordsCopy.size());
		for (TimeSeriesElement<T> record : returnedRecordsCopy) {
			records.add(record.getRecord());
		}
		return records;
	}

	public List<T> remove(long startTime, long endTime) {
		List<TimeSeriesElement<T>> data = availableRecordsInTimeSpan(startTime, endTime);
		if (data.size() < 1) {
			return null;
		}
		List<TimeSeriesElement<T>> returnedRecords = data.subList(0, data.size() - 1);
		List<TimeSeriesElement<T>> returnedRecordsCopy = copyElements(returnedRecords);
		returnedRecords.clear();
		List<T> records = new ArrayList<>(returnedRecordsCopy.size());
		for (TimeSeriesElement<T> record : returnedRecordsCopy) {
			records.add(record.getRecord());
		}
		return records;
	}

	public List<T> remove(int size) {

		if (size > timeSeriesElements.size()) {
			size = timeSeriesElements.size();
		}

		List<TimeSeriesElement<T>> recordsList = timeSeriesElements.subList(0, size);

		List<T> returnedRecord = new LinkedList<T>();

		for (TimeSeriesElement<T> record : recordsList) {
			returnedRecord.add(record.getRecord());
		}
		recordsList.clear();
		return returnedRecord;
	}

	public List<T> removeAll() {
		List<T> returnedRecordCopy = new LinkedList<T>();
		for (TimeSeriesElement<T> record : timeSeriesElements) {
			returnedRecordCopy.add(record.getRecord());
		}
		timeSeriesElements.clear();
		return returnedRecordCopy;
	}

	public List<T> get(long startTime, long endTime, int size) {
		List<TimeSeriesElement<T>> availableInTimeSpan = availableRecordsInTimeSpan(startTime, endTime);
		int availableSize = size;
		if (availableInTimeSpan.size() < size) {
			availableSize = availableInTimeSpan.size();
		}
		List<T> returnedRecords = new ArrayList<>();
		List<TimeSeriesElement<T>> recordsCopy = availableInTimeSpan.subList(0, availableSize);
		for (TimeSeriesElement<T> t : recordsCopy) {
			returnedRecords.add(t.getRecord());
		}
		return returnedRecords;
	}

	public List<TimeSeriesElement<T>> availableRecordsInTimeSpan(long startTime, long endTime) {
		List<TimeSeriesElement<T>> availableRecordsInTimeSpan = new LinkedList<TimeSeriesElement<T>>();
		for (TimeSeriesElement<T> record : timeSeriesElements) {
			if (record.getTime() >= startTime || record.getTime() <= endTime) {
				availableRecordsInTimeSpan.add(record);
			}
		}
		return availableRecordsInTimeSpan;

	}

	public void addElement(T record, long time) {
		timeSeriesElements.add(new TimeSeriesElement<>(record, time));
	}

	public int size() {
		return timeSeriesElements.size();
	}

	@Override
	public String toString() {
		if (timeSeriesElements != null) {
			return timeSeriesElements.toString();
		}
		return null;
	}

}
