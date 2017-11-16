package org.loushang.framework.util;


/**
 * 
 * 定义常量
 */
public class Constant {
	/**
	 * 判断操作系统
	 */
	public static String OS;
	
	
	static {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") >= 0) {
			OS="windows";
		}
		
		if (os.indexOf("linux") >= 0) {
			OS="linux";
		}
		if (os.indexOf("mac") >= 0) {
			OS="mac";
		}
		
	}
	
	
}
