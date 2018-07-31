package com.scalable.webcrawler.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JavaScriptUtilsTest {

	@Test
	public void removeVersionFromNameTest() {
		assertEquals(JavaScriptUtils.removeVersionFromName("test-1.11.1.js"), "test.js");
		assertEquals(JavaScriptUtils.removeVersionFromName("test.1.11.1.js"), "test.js");
		assertEquals(JavaScriptUtils.removeVersionFromName("test-1.11.js"), "test.js");
	}

	@Test
	public void removeMinifiedFromNameTest() {
		assertEquals(JavaScriptUtils.removeMinifiedFromName("test-min.js"), "test.js");
		assertEquals(JavaScriptUtils.removeMinifiedFromName("test.min.js"), "test.js");
		assertEquals(JavaScriptUtils.removeMinifiedFromName("test.js"), "test.js");
	}

	@Test
	public void removeCacheStringFromNameTest() {
		assertEquals(JavaScriptUtils.removeCacheStringFromName("test-28ad3885d20336165372ce7de5caf99dd332388c.js"), "test.js");
		assertEquals(JavaScriptUtils.removeCacheStringFromName("test.28ad3885d20336165372ce7de5caf99dd332388c.js"), "test.js");
		assertEquals(JavaScriptUtils.removeCacheStringFromName("test.js"), "test.js");
	}
}
