package com.reservation.utils;

@SuppressWarnings("rawtypes")
public interface Cache<K extends Comparable, V> {

	V get(K key, int type);
	V get(K key);
	void put(K key, V obj);
	void remove(K key);
	public void clearAll();
	int size();
	public boolean containKey(K key);
}
