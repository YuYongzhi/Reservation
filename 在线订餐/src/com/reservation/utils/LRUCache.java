package com.reservation.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.net.ConnectivityManager.*;

@SuppressWarnings("rawtypes")
public class LRUCache<K extends Comparable, V> implements Cache<K, V>, Serializable {

	/**
	 * 
	 * 
	 * @author 2014-2-6
	 */
	private static final long serialVersionUID = 1L;
	private Map<K, Item> map = Collections.synchronizedMap(new HashMap<K, Item>());
	private Item mStart = new Item();
	private Item mEnd = new Item();
	private int mMaxSize;
	private Object mListLock = new Object();
	
	private static final int CACHE_WIFI_TIME = 60 * 60 * 1000;
	private static final int CACHE_MOBILE_TIME = 60 * 60 * 1000;
	
	private static class Item {
		public Comparable key;
		public Object value;
		public long expires;
		public Item previous;
		public Item next;
		
		public Item() {
			
		}
		
		public Item(Comparable k, Object v, long e) {
			key = k;
			value = v;
			expires = e;
		}
	}
	
	public LRUCache(int maxObjects) {
		mMaxSize = maxObjects;
		mStart.next = mEnd;
		mEnd.previous = mStart;
	}
	
	private void removeItem(Item item) {
		synchronized (mListLock) {
			item.previous.next = item.next;
			item.next.previous = item.previous;
		}
	}
	
	private void insertHead(Item item) {
		synchronized (mListLock) {
			item.previous = mStart;
			item.next = mStart.next;
			mStart.next.previous = item;
			mStart.next = item;
		}
	}
	
	private void moveToHead(Item item) {
		synchronized (mListLock) {
			item.previous.next = item.next;
			item.next.previous = item.previous;
			item.previous = mStart;
			item.next = mStart.next;
			mStart.next.previous = item;
			mStart.next = item;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key, int type) {
		Item cur = map.get(key);
		if (cur == null) {
			return null;
		}
		int outTime = 0;
		if (type == TYPE_WIFI) {
			outTime = CACHE_WIFI_TIME;
		} else {
			outTime = CACHE_MOBILE_TIME;
		}
		if (System.currentTimeMillis() - cur.expires > outTime) {
			map.remove(cur.key);
			removeItem(cur);
			return null;
		}
		if (cur != mStart.next) {
			moveToHead(cur);
		}
		return (V)cur.value;
	}

	@Override
	public V get(K key) {
		return get(key, TYPE_WIFI);
	}
	
	@Override
	public void put(K key, V obj) {
		Item cur = map.get(key);
		if (cur != null) {
			cur.value = obj;
			cur.expires = System.currentTimeMillis();
			moveToHead(cur);
			return ;
		}
		if (map.size() >= mMaxSize) {
			cur = mEnd.previous;
			map.remove(cur.key);
			removeItem(cur);
		}
		Item item = new Item(key, obj, System.currentTimeMillis());
		insertHead(item);
		map.put(key, item);
	}

	@Override
	public void remove(K key) {
		Item cur = map.get(key);
		if (cur == null) {
			return ;
		} else {
			map.remove(key);
			removeItem(cur);
		}
	}

	@Override
	public void clearAll() {
		for(K key : map.keySet()) {
			Item cur = map.get(key);
			removeItem(cur);
		}
		map.clear();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean containKey(K key) {
		if (map.get(key) != null) {
			return true;
		}
		return false;
	}


}
