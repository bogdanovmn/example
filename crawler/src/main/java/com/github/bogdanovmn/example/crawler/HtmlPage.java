package com.github.bogdanovmn.example.crawler;

import java.net.URL;
import java.util.Set;

class HtmlPage {
	private final URL url;
	private final String text;

	HtmlPage(URL url, String text) {
		this.url = url;
		this.text = text;
	}

	Set<URL> getAllHttpUrls() {
		return null;
	}

	public URL getUrl() {
		return url;
	}
}
