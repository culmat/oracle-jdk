package com.bintray.origin.publish;

import com.bintray.origin.JDKDescriptor;
import com.bintray.origin.repack.Repack;
import com.bintray.origin.repack.RepackMac;

public class MacZipPublisher implements RepackagingPublisher {

	@Override
	public boolean isActiveFor(JDKDescriptor desc) {
		return "macosx".equals(desc.getOs());
	}

	public Repack getRepacker() {
		return new RepackMac();
	}
	
}
