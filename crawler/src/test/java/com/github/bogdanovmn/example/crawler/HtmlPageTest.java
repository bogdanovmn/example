package com.github.bogdanovmn.example.crawler;

import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class HtmlPageTest {
	@Test
	public void getAllHttpUrls() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					ClassLoader.getSystemClassLoader().getResource("somepage.html").toURI()
				)
			)
		);

		HtmlPage page = new HtmlPage(new URL("http://teplovoz.com"), html);

		System.out.println(page.getAllHttpUrls());
		assertEquals(10, page.getAllHttpUrls().size());
	}

}