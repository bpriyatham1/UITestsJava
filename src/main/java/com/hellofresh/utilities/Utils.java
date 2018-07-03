package com.hellofresh.utilities;

import org.apache.commons.lang3.RandomStringUtils;

import com.hellofresh.challenge.TestBase;

public class Utils extends TestBase {

	public final static long PAGE_LOAD_TIMEOUT = 15;
	public final static long IMPLICIT_WAIT = 10;

	public static String generaterandomname() {
		String rn = RandomStringUtils.randomAlphabetic(15);
		return rn;
	}
	
}