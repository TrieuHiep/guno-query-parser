package vn.guno.cache;

public interface ICache<K, V> {
    void clear();
    boolean containsKey(Object key);
    V get(Object key);
    boolean isEmpty();
    V put(K key, V value);
    V remove(Object key);
    int size();
}
