package util;

import java.io.Serializable;

public class Pair<T,K> implements Serializable {
	private static final long serialVersionUID = -201085438441320151L;
	public T key1;
	public K key2;
	
	public Pair(T key1,K key2) {
		this.key1=key1;
		this.key2=key2;
	}

}
