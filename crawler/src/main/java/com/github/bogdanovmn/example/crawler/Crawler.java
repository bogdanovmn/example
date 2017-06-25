package com.github.bogdanovmn.example.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

class Crawler {
	private static final Logger LOG = LogManager.getLogger(Crawler.class);

	private final URL startUrl;
	private final int workersCount;

	Crawler(URL startUrl, int workersCount) {
		this.startUrl = startUrl;
		this.workersCount = workersCount;
	}

	void go() {
		ExecutorService workers = Executors.newFixedThreadPool(this.workersCount);

		Set<URL> queue = new HashSet<>();
		queue.add(this.startUrl);

		Set<URL> errors = new HashSet<>();
		Set<URL> processed = new HashSet<>();
		Set<URL> current = new HashSet<>();

		while (!queue.isEmpty()) {
			try {
				LOG.info("Processing {} urls", queue.size());

				List<Future<HtmlPage>> fetchResult = workers.invokeAll(
					queue.stream()
						.map(UrlContentGetChildsTask::new)
						.collect(Collectors.toList())
				);

				for (Future<HtmlPage> taskResult : fetchResult) {
					try {
						HtmlPage page = taskResult.get();
						current.addAll(
							page.getAllHttpUrls()
						);
						processed.add(page.getUrl());
						queue.remove(page.getUrl());

						LOG.info("{} urls on {}", page.getAllHttpUrls().size(), page.getUrl());
					}
					catch (ExecutionException e) {
						LOG.error(e.getMessage());
					}
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				LOG.info("Total urls processed: {}", processed.size());
				break;
			}
			errors.addAll(queue);
			queue.clear();
			queue.addAll(
				current.stream()
					.filter(url -> !processed.contains(url) && !errors.contains(url))
					.collect(Collectors.toList())
			);
			current.clear();
		}

		workers.shutdownNow();
		LOG.info("Processed: {}, errors: {}", processed.size(), errors.size());
		LOG.info("Errors: {}", errors);
	}
}
