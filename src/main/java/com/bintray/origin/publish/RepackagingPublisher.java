package com.bintray.origin.publish;

import java.io.File;
import java.io.FileInputStream;

import com.bintray.origin.JDKDescriptor;
import com.bintray.origin.repack.Repack;

public interface RepackagingPublisher extends Publisher {
	@Override
	default void publish(JDKDescriptor desc) {
		try {
			download(desc);
			Repack repacker = getRepacker();
			File repacked = repacker.repack(desc.getFilename());
			new File(desc.getFilename()).delete();
			desc.setFilename(desc.getFilename().replace(repacker.getNativeExtension(), ".zip"));
			BintrayJDK.getInstance().upload(desc, new FileInputStream(repacked));
			repacked.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  Repack getRepacker();
}
