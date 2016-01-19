package com.bintray.origin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;

public class JDKDescriptors {
	public static JDKDescriptor getJDKDescriptor(HtmlAnchor link){
		return getJDKDescriptor(getFileName(link)).setLink(link);
	}
	public static JDKDescriptor getJDKDescriptor(String filename){
		Pattern pattern = Pattern.compile("jdk-([^-]+)-([^-]+)-([^.]+)\\.(.+)");
		Matcher matcher = pattern.matcher(filename);
		matcher.find();
		JDKDescriptor ret = new JDKDescriptor();
		ret.setVersion(matcher.group(1));
		ret.setOs(matcher.group(2));
		ret.setArch(matcher.group(3));
		ret.setFormat(matcher.group(4));
		ret.setFilename(filename);
		if("i586".equals(ret.getArch())) ret.setArch("x86");
		return ret;
		
	}

	public static String getFileName(HtmlAnchor link) {
		String[] tok = link.getHrefAttribute().split("/");
		return tok[tok.length-1];
	}
}
