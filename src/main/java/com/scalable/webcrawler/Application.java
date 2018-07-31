package com.scalable.webcrawler;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {
		WebCrawler webCrawler = new WebCrawler();

		String term = printMenuAndReturnTerm();

		long start = System.currentTimeMillis();

		Collection<String> results = webCrawler.getTop5JavascriptLibrariesForTerm(term);

		long time = System.currentTimeMillis() - start;

		printResults(results, time);
	}

	private static String printMenuAndReturnTerm() {
		String term;

		System.out.println("Enter term: ");
		try (Scanner scanner = new Scanner(System.in)) {
			term = scanner.nextLine();
		}

		return term;
	}

	private static void printResults(Collection<String> results, long time) {
		System.out.printf("\nTop 5 Javascript Libraries (%d ms taken)\n", time);

		int position = 0;
		for (String result : results) {
			System.out.printf("%d. %s\n", ++position, result);
		}
	}
}
