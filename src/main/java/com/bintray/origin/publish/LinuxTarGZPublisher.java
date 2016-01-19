package com.bintray.origin.publish;

import com.bintray.origin.JDKDescriptor;
import com.gargoylesoftware.htmlunit.WebResponse;

public class LinuxTarGZPublisher implements Publisher {

	@Override
	public boolean isActiveFor(JDKDescriptor desc) {
		return "tar.gz".equals(desc.getFormat()) && "linux".equals(desc.getOs());
	}

	@Override
	public void publish(JDKDescriptor desc) {
		try {
			WebResponse webResponse = desc.getLink().click().getWebResponse();
			BintrayJDK.getInstance().upload(desc, webResponse.getContentAsStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
