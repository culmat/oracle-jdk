package com.bintray.origin;

import static com.bintray.origin.JDKDescriptors.getJDKDescriptor;
import static com.bintray.origin.publish.Publishers.getPublishers;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.bintray.origin.publish.BintrayJDK;
import com.bintray.origin.publish.Publisher;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class OracleJDK{
	
	public static void main(String[] args) throws Exception {
		setLogLevel(org.slf4j.Logger.ROOT_LOGGER_NAME, Level.ERROR);
		System.out.println(System.getProperty("bintray_token").substring(35));
		if(true) return;
		new OracleJDK().run();
	}

	public static void setLogLevel(String loggerName, Level level) {
		Logger root = (Logger)LoggerFactory.getLogger(loggerName);
		root.setLevel(level);
	}

	public void run() throws Exception {
		String url = "http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html";
		System.out.println("Getting versions from "+url);
		try (final WebClient webClient = new WebClient()) {
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			final HtmlPage page = webClient.getPage(url);
			HtmlForm form = page.getForms().stream().filter(
					f -> f.getNameAttribute().startsWith("agreementForm")
					).sorted(
							(f1,f2) -> -1 * f1.getNameAttribute().compareTo(f2.getNameAttribute())
							)
					.findFirst().get();
			String version = form.getNameAttribute().replace("agreementForm", "");
			HtmlInput radio = form.getInputByName(form.getNameAttribute().replace("Form", ""));
			radio.click();
			@SuppressWarnings("unchecked")
			final List<HtmlAnchor> links = (List<HtmlAnchor>) page.getByXPath("//a");
			System.out.println("Latest version found at Oracle is " + version);
			Iterable<Publisher> publishers = getPublishers();
			links.stream().filter(
					a -> a.getNameAttribute().matches(version+".+")
					).forEach(a->{
						publishers.forEach(p -> p.accept(getJDKDescriptor(a)));
					});
		}
		
	}

}
