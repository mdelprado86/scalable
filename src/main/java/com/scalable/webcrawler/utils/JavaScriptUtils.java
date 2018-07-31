package com.scalable.webcrawler.utils;

public final class JavaScriptUtils {

	private JavaScriptUtils() {

	}

	public static String removeVersionFromName(String name) {
		final String regex = "(\\.|-)(\\d+(\\.\\d+)+)";
		return name.replaceAll(regex, "");
	}

	public static String removeMinifiedFromName(String name) {
		final String regex = "(\\.|-)(min)";
		return name.replaceAll(regex, "");
	}

	public static String removeCacheStringFromName(String name) {
		final String regex = "(\\.|-)([a-z0-9+]{35,60})";
		return name.replaceAll(regex, "");
	}
}
