package com.census.entities;

public class Pair<T, U> {
	public final T size;
	public final U list;

	public Pair(T t, U u) {
		this.size = t;
		this.list = u;
	}
}