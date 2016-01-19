package com.bintray.origin.publish;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import com.bintray.origin.JDKDescriptor;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface Publisher extends Consumer<JDKDescriptor>{
	
	default void download(JDKDescriptor desc) throws IOException {
		HtmlAnchor link = desc.getLink();
		log("downloading "+ link.getHrefAttribute());
		WebResponse webResponse = link.click().getWebResponse();
		log(webResponse.getStatusCode());
		log(webResponse.getStatusMessage());
		Files.copy(webResponse.getContentAsStream(), Paths.get(desc.getFilename()), REPLACE_EXISTING);
	}
	
	@Override
	default void accept(JDKDescriptor desc){
		System.out.println("Checking "+desc);
		if(!isActiveFor(desc)){
			log("inactive");
			return;
		}
		if(!isNewVersion(desc)){
			log("version already published");
			return;
		}
		log("publishing ...");
		publish(desc);
	}
	
	default void log(Object message){
		System.out.print("\t ");
		System.out.println(message);
	}
	
	default boolean isNewVersion(JDKDescriptor desc){
		try {
			String latestBinTrayVersion = BintrayJDK.getInstance().getLatestVersion(desc);
			log("Latest bintray version is "+latestBinTrayVersion);
			return !latestBinTrayVersion.equals(desc.getVersion());
		} catch (UnirestException e) {
			e.printStackTrace();
			return false;
		}
	}
	boolean isActiveFor(JDKDescriptor desc);
	void publish(JDKDescriptor desc);
}