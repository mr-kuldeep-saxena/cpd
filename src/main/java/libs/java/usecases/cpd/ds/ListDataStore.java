package libs.java.usecases.cpd.ds;

import java.util.Collection;
import java.util.List;

/**
 * A {@link TimeSeriesElement} backed array data store.
 * 
 * Storage of element supposed to be in sequence in sorted time order by caller
 * of code
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */

public interface ListDataStore<T> {

	public List<T> get(int size);

	public List<T> getAll();

	public List<T> get(int size, long startTime, long endTime);

	public List<T> get(long startTime, long endTime);

	public T put(T data);

	public int size();

	public T put(T data, long insertTime);

	public void putAll(Collection<T> data);

	public List<T> remove(int size);

	public T remove(T data);

	public List<T> removeAll();

	public List<T> remove(int size, long startTime, long endTime);

	public List<T> remove(long startTime, long endTime);

	public void waitTillAvailable(int size, long timeInMills);

}