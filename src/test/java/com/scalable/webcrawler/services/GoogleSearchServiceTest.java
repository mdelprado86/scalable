package com.scalable.webcrawler.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class GoogleSearchServiceTest {

	@Test
	public void getUrlsForSearchTermTest() {
		try {
			Path pathToTestFile = Paths.get(getClass().getResource("/google.html").toURI());
			String simpleHTML = new String(Files.readAllBytes(pathToTestFile));
			Document document = Jsoup.parse(simpleHTML);

			mockStatic(Jsoup.class);
			Connection connection = Mockito.mock(Connection.class);
			PowerMockito.when(Jsoup.connect(any())).thenReturn(connection);
			PowerMockito.when(connection.userAgent(any())).thenReturn(connection);
			PowerMockito.when(connection.get()).thenReturn(document);

			GoogleSearchService googleSearchService = GoogleSearchServiceImpl.getInstance();
			Collection<String> urlsForSearchTerm = googleSearchService.getUrlsForSearchTerm("test");

			assertEquals("https://test.com/", urlsForSearchTerm.toArray()[0]);
			assertEquals("https://test1.com/", urlsForSearchTerm.toArray()[1]);
			assertEquals("https://test2.com/", urlsForSearchTerm.toArray()[2]);
		} catch (Exception e) {
			fail();
		}
	}
}
