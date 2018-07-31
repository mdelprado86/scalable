package com.scalable.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.scalable.webcrawler.services.GoogleSearchService;
import com.scalable.webcrawler.services.GoogleSearchServiceImpl;
import com.scalable.webcrawler.services.WebPageScraperService;
import com.scalable.webcrawler.services.WebPageScraperServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GoogleSearchServiceImpl.class, WebPageScraperServiceImpl.class })
public class WebCrawlerTest {

	@Test
	public void getTop5JavascriptLibrariesForTermTest() {
		try {
			mockStatic(GoogleSearchServiceImpl.class);
			mockStatic(WebPageScraperServiceImpl.class);

			GoogleSearchService googleSearchService = Mockito.mock(GoogleSearchServiceImpl.class);
			WebPageScraperService webPageScraperService = Mockito.mock(WebPageScraperServiceImpl.class);

			PowerMockito.when(GoogleSearchServiceImpl.getInstance()).thenReturn(googleSearchService);
			PowerMockito.when(WebPageScraperServiceImpl.getInstance()).thenReturn(webPageScraperService);

			Collection<String> googleHints = Arrays.asList("test.html", "test1.html", "test2.html");
			PowerMockito.when(googleSearchService.getUrlsForSearchTerm(any())).thenReturn(googleHints);

			Collection<String> librariesForTest = Arrays.asList("jquery.js", "angular.js");
			Collection<String> librariesForTestOne = Arrays.asList("jquery.js", "react.js");
			Collection<String> librariesForTestTwo = Arrays.asList("jquery.js", "angular.js");

			PowerMockito.when(webPageScraperService.getScriptLinksForWebPage("test.html")).thenReturn(librariesForTest);
			PowerMockito.when(webPageScraperService.getScriptLinksForWebPage("test1.html")).thenReturn(librariesForTestOne);
			PowerMockito.when(webPageScraperService.getScriptLinksForWebPage("test2.html")).thenReturn(librariesForTestTwo);

			WebCrawler webCrawler = new WebCrawler();
			Collection<String> top5JavascriptLibrariesForTerm = webCrawler.getTop5JavascriptLibrariesForTerm("test");

			assertEquals("jquery.js", top5JavascriptLibrariesForTerm.toArray()[0]);
			assertEquals("angular.js", top5JavascriptLibrariesForTerm.toArray()[1]);
			assertEquals("react.js", top5JavascriptLibrariesForTerm.toArray()[2]);
		} catch (Exception e) {
			fail();
		}

	}

}
