package libs.java.usecases.cpd.ds;

import java.util.Collection;
import java.util.Set;

/**
 * A key-value  data store.
 * 
 * Storage of element supposed to be in sequence in sorted time order by caller
 * of code
 * 
 * @author Kuldeep
 *
 * @param <K>
 * @param <T>
 */
public interface MapDataStore<K, T> {

	public T put(K key, T data);

	public void clear();

	public T get(K key);

	public boolean containsKey(K key);

	public Collection<T> removeAll();

	public T remove(K key);
	
	public Set<K> keySet();
	
	public int size ();
	


}
