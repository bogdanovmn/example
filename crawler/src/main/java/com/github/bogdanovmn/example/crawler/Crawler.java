package com.github.bogdanovmn.example.crawler;

import java.net.URL;

public class Crawler {
	private final URL startUrl;
	private final int workersCount;

	public Crawler(URL startUrl, int workersCount) {
		this.startUrl = startUrl;
		this.workersCount = workersCount;
	}
}
