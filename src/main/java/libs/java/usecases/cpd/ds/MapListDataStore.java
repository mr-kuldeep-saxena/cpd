package libs.java.usecases.cpd.ds;

import java.util.Collection;
import java.util.List;

/**
 * A key-value backed data store.
 * 
 * Storage of element supposed to be in sequence in sorted time order by caller
 * of code
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */

public interface MapListDataStore<K, T> {

	public List<T> get(K key, int size);

	public List<T> get(K key, int size, long startTime, long endTime);

	public List<T> get(K key, long startTime, long endTime);

	public List<T> remove(K key, int size);

	public T put(K key, T data, long time);

	public T put(K key, T data);

	
	public List<T> remove(K key, int size, long startTime, long endTime);

	public List<T> remove(K key, long startTime, long endTime);

	public List<T> removeAll(K key);

	public ListDataStore<T> getUnderlyingStorage(K key);

	public boolean containsKey(K key);

	public void clear(K key);


	public void clear();

	
	public List<T> get(K key);
	public Collection<T> removeAll();

}
