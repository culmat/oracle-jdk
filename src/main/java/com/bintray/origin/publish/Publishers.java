package com.bintray.origin.publish;

import static java.util.Arrays.asList;


public class Publishers {
	public static Iterable<Publisher>  getPublishers(){
		return asList(new LinuxTarGZPublisher(), new WindowsZipPublisher(), new MacZipPublisher());
	}
}
