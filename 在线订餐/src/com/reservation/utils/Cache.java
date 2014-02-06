package com.reservation.utils;

@SuppressWarnings("rawtypes")
public interface Cache<K extends Comparable, V> {

	V get(K key, int type);
	void put(K key, V obj);
	void remove(K key);
	public void clearAll();
	int size();
}
