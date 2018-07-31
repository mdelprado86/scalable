package com.scalable.webcrawler.services;

import java.util.Collection;

public interface WebPageScraperService {

	Collection<String> getScriptLinksForWebPage(String url);
}
