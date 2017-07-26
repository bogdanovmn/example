package com.github.bogdanovmn.example.crawler;

import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class HtmlPage {
	private final URL url;
	private final String text;
	private final Set<String> badUrls = new HashSet<>();
	private Set<URL> urls = null;

	HtmlPage(URL url, String text) {
		this.url = url;
		this.text = text;
	}

	Set<URL> getAllHttpUrls() {
		if (this.urls == null) {
			String host = this.url.getProtocol() + "://" + this.url.getHost();

			this.urls = new HashSet<>();
			this.urls = Jsoup.parse(this.text).select("a").stream()
				.map(link -> link.attr("href"))
				.filter(href -> href.startsWith("http") || href.startsWith("/"))
				.map(
					href -> {
						URL url = null;
						try {
							url = new URL(
								href.startsWith("/")
									? host + href
									: href
							);
						}
						catch (MalformedURLException e) {
							this.badUrls.add(href);
						}
						return url;
					}
				)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		}

		return this.urls;
	}

	URL getUrl() {
		return url;
	}
}
