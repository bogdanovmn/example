package com.github.bogdanovmn.example.crawler;

import java.util.*;

public class BatchQueue<T> implements Iterator<List<T>> {
	private final int batchSize;
	private final LinkedList<List<T>> data = new LinkedList<>();
	private int total = 0;

	public BatchQueue(int batchSize) {
		this.batchSize = batchSize;
		this.data.add(
			new ArrayList<>(this.batchSize)
		);
	}

	public void add(T item) {
		this.getFreeBatch().add(item);
		this.total++;
	}

	public void add(Collection<T> items) {
		for (T item : items) {
			this.add(item);
		}
	}

	private List<T> getFreeBatch() {
		if (this.data.isEmpty() || this.data.getLast().size() == this.batchSize) {
			this.data.add(
				new ArrayList<>(this.batchSize)
			);
		}
		return this.data.getLast();
	}

	@Override
	public boolean hasNext() {
		return !this.data.isEmpty();
	}

	@Override
	public List<T> next() {
		this.total -= this.data.getLast().size();
		return this.data.pollLast();
	}

	public int getTotal() {
		return this.total;
	}
}
