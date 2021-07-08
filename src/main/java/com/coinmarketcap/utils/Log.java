package com.coinmarketcap.utils;

import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());

	public static void startTestCase(String sTestCaseName) {
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}

	public static void endTestCase(String sTestCaseName) {
		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
		Log.info("X");
		Log.info("X");
		Log.info("X");
		Log.info("X");
	}

	public static void info(String message) {
		Log.info(message);
	}
}
