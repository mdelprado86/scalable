package com.scalable.webcrawler.services;

import java.util.Collection;

public interface GoogleSearchService {

	Collection<String> getUrlsForSearchTerm(String term);
}
