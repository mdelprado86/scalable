package com.scalable.webcrawler.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scalable.webcrawler.utils.JavaScriptUtils;

public class WebPageScraperServiceImpl implements WebPageScraperService {

	private static class SinglentonInstanceWrapper {
		static WebPageScraperService instance = new WebPageScraperServiceImpl();
	}

	private WebPageScraperServiceImpl() {

	}

	public static WebPageScraperService getInstance() {
		return SinglentonInstanceWrapper.instance;
	}

	@Override
	public Collection<String> getScriptLinksForWebPage(String url) {
		try {
			Collection<String> results = new ArrayList<String>();

			Document doc = Jsoup.connect(url).get();
			Elements scripts = doc.select("script");

			for (Element script : scripts) {
				if (script.attr("src") != null && script.attr("src").length() > 0) {
					String scriptName = getScriptTitleFromScriptUrl(script.attr("src"));
					if (scriptName.length() > 0) {
						String deduplicatedName = JavaScriptUtils.removeMinifiedFromName(scriptName);
						deduplicatedName = JavaScriptUtils.removeCacheStringFromName(deduplicatedName);
						deduplicatedName = JavaScriptUtils.removeVersionFromName(deduplicatedName);
						results.add(deduplicatedName);
					}
				}
			}

			return results;
		} catch (Exception e) {
			// log exception
			return Collections.emptyList();
		}
	}

	private String getScriptTitleFromScriptUrl(String url) {
		String scriptName = "";
		Pattern pattern = Pattern.compile("(.*/.*\\.js)");
		Matcher matcher = pattern.matcher(url);

		if (matcher.find()) {
			String regex = matcher.group(1);
			int backSlashPosition = regex.lastIndexOf("/");
			scriptName = regex.substring(backSlashPosition + 1);
		}

		return scriptName;
	}
}
