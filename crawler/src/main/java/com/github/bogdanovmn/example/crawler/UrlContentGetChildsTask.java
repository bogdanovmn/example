package com.github.bogdanovmn.example.crawler;

import com.github.bogdanovmn.downloadwlc.UrlContentDiscCache;

import java.net.URL;
import java.util.concurrent.Callable;

class UrlContentGetChildsTask implements Callable<HtmlPage> {
	private static final UrlContentDiscCache CACHE = new UrlContentDiscCache(UrlContentGetChildsTask.class);
	private final URL url;

	UrlContentGetChildsTask(URL url) {
		this.url = url;
	}

	@Override
	public HtmlPage call() throws Exception {
		return new HtmlPage(
			this.url,
			CACHE.getText(this.url)
		);
	}
}
