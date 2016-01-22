package com.bintray.origin.publish;

import com.bintray.origin.JDKDescriptor;
import com.bintray.origin.repack.Repack;
import com.bintray.origin.repack.RepackWin;

public class WindowsZipPublisher implements RepackagingPublisher {

	@Override
	public boolean isActiveFor(JDKDescriptor desc) {
		return false &&  "windows".equals(desc.getOs());
	}

	public Repack getRepacker() {
		return new RepackWin();
	}

}
