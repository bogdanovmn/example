package com.github.bogdanovmn.example.crawler;

import org.apache.commons.cli.*;

import java.net.MalformedURLException;
import java.net.URL;

public class App {
	public static void main(String[] args) {
		Options cliOptions = new Options();
		cliOptions
			.addOption(
				Option.builder("u")
					.longOpt("start-url")
					.desc("crawler entry point")
					.required()
					.hasArg()
					.argName("URL")
					.build()
			)
			.addOption(
				Option.builder("w")
					.longOpt("workers")
					.desc("concurrent download workers count")
					.hasArg()
					.argName("COUNT")
					.build()
			);

		CommandLineParser cmdLineParser = new DefaultParser();
		try {
			CommandLine cmdLine = cmdLineParser.parse(cliOptions, args);
			URL startUrl = new URL(
				cmdLine.getOptionValue("start-url")
			);
			int workersCount = Integer.parseInt(
				cmdLine.getOptionValue("workers", "1")
			);

			Crawler crawler = new Crawler(startUrl, workersCount);
			crawler.go();
		}
		catch (ParseException | MalformedURLException e) {
			showErrWithUsage(e.getMessage(), cliOptions);
		}
	}

	private static void showErrWithUsage(final String message, final Options options) {
		System.out.println(message);
		showUsage(options);
	}

	private static void showUsage(final Options options) {
		new HelpFormatter()
			.printHelp("app", options, true);
	}
}
