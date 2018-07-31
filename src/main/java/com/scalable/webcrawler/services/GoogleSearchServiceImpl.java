package com.scalable.webcrawler.services;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearchServiceImpl implements GoogleSearchService {

	private final static String GOOGLE_SEARCH_URL = "http://www.google.com/search?q=%s";
	private final static String GOOGLE_SEARCH_AGENT = "Example Agent";
	private final static String GOOGLE_CHAR_SET = "UTF-8";
	private final static String GOOGLE_SEARCH_HINT_SELECTOR = ".g>.r>a";

	private static class SinglentonInstanceHolder {
		static GoogleSearchService instance = new GoogleSearchServiceImpl();
	}

	private GoogleSearchServiceImpl() {

	}

	public static GoogleSearchService getInstance() {
		return SinglentonInstanceHolder.instance;
	}

	public Collection<String> getUrlsForSearchTerm(String term) {
		try {
			Collection<String> results = new ArrayList<String>();

			String encodedTerm = URLEncoder.encode(term, GOOGLE_CHAR_SET);
			String searchUrlWithEncodedTerm = String.format(GOOGLE_SEARCH_URL, encodedTerm);
			Document document = Jsoup.connect(searchUrlWithEncodedTerm).userAgent(GOOGLE_SEARCH_AGENT).get();
			Elements links = document.select(GOOGLE_SEARCH_HINT_SELECTOR);

			for (Element link : links) {
				String url = link.absUrl("href");
				String extractedUrl = extractUrlFromGoogleSearchLink(url);
				String decodedExtractedUrl = URLDecoder.decode(extractedUrl, "UTF-8");

				if (decodedExtractedUrl.startsWith("http")) {
					results.add(decodedExtractedUrl);
				}
			}

			return results;
		} catch (Exception e) {
			// log exception
			return Collections.emptyList();
		}
	}

	private static String extractUrlFromGoogleSearchLink(String url) {
		String extractedUrl = url;
		int start = url.indexOf('=');
		int end = url.indexOf('&');

		if (start != -1 && end != -1) {
			extractedUrl = url.substring(start + 1, end);
		}

		return extractedUrl;
	}

}
