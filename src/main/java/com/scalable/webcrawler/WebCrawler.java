package com.scalable.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.scalable.webcrawler.services.GoogleSearchService;
import com.scalable.webcrawler.services.GoogleSearchServiceImpl;
import com.scalable.webcrawler.services.WebPageScraperService;
import com.scalable.webcrawler.services.WebPageScraperServiceImpl;

public class WebCrawler {

	private final GoogleSearchService googleSearchService = GoogleSearchServiceImpl.getInstance();
	private final List<String> scripts = Collections.synchronizedList(new ArrayList<>());
	private final ExecutorService executor = Executors.newCachedThreadPool();

	public Collection<String> getTop5JavascriptLibrariesForTerm(String term) throws IOException, InterruptedException {
		System.out.printf("Calculating Top 5 Javascript libraries for term \"%s\" ", term);

		Collection<String> urlsForSearchTerm = googleSearchService.getUrlsForSearchTerm(term);
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>(urlsForSearchTerm);

		while (!queue.isEmpty()) {
			Runnable worker = () -> {
				try {
					String element = queue.remove();
					WebPageScraperService webPageScrapeService = WebPageScraperServiceImpl.getInstance();
					Collection<String> scriptLinksForWebPage = webPageScrapeService.getScriptLinksForWebPage(element);
					scripts.addAll(scriptLinksForWebPage);
				} catch (NoSuchElementException e) {
					// No more elements in queue. Thread will be stopped
				}
			};
			executor.execute(worker);
		}

		executor.shutdown();

		while (!executor.isTerminated()) {
			System.out.print(".");
			Thread.sleep(500);
		}

		Collection<String> results = calculateResults();

		return results;
	}

	private Collection<String> calculateResults() {
		Map<String, Long> resultsGrouped = scripts.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Stream<Entry<String, Long>> resultsGroupepOrderedAsc = resultsGrouped.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
		List<String> results = resultsGroupepOrderedAsc.limit(5).map(e -> e.getKey()).collect(Collectors.toList());

		return results;
	}
}
