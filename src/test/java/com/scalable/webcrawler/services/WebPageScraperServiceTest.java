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
public class WebPageScraperServiceTest {

	@Test
	public void getScriptLinksForWebPageTest() {
		try {
			Path pathToTestFile = Paths.get(getClass().getResource("/test.html").toURI());
			String simpleHTML = new String(Files.readAllBytes(pathToTestFile));
			Document document = Jsoup.parse(simpleHTML);

			mockStatic(Jsoup.class);
			Connection connection = Mockito.mock(Connection.class);
			PowerMockito.when(Jsoup.connect(any())).thenReturn(connection);
			PowerMockito.when(connection.get()).thenReturn(document);

			WebPageScraperService webPageScraperService = WebPageScraperServiceImpl.getInstance();
			Collection<String> links = webPageScraperService.getScriptLinksForWebPage("http://test.html");

			assertEquals("test.js", links.toArray()[0]);
			assertEquals("test1.js", links.toArray()[1]);
			assertEquals("test2.js", links.toArray()[2]);
			assertEquals("test3.js", links.toArray()[3]);
		} catch (Exception e) {
			fail();
		}

	}
}
