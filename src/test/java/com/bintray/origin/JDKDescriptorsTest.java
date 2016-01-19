package com.bintray.origin;

import org.junit.Assert;
import org.junit.Test;

public class JDKDescriptorsTest {

//	jdk-8u66-linux-i586.rpm
//	jdk-8u66-linux-i586.tar.gz
//	jdk-8u66-linux-x64.rpm
//	jdk-8u66-linux-x64.tar.gz
//	jdk-8u66-macosx-x64.dmg
//	jdk-8u66-solaris-sparcv9.tar.Z
//	jdk-8u66-solaris-sparcv9.tar.gz
//	jdk-8u66-solaris-x64.tar.Z
//	jdk-8u66-solaris-x64.tar.gz
//	jdk-8u66-windows-i586.exe
//	jdk-8u66-windows-x64.exe
	
	@Test
	public void testGetJDKDescriptor1() throws Exception {
		JDKDescriptor desc = JDKDescriptors.getJDKDescriptor("jdk-8u66-solaris-sparcv9.tar.gz");
		Assert.assertEquals("8u66", desc.getVersion());
		Assert.assertEquals("solaris", desc.getOs());
		Assert.assertEquals("sparcv9", desc.getArch());
		Assert.assertEquals("tar.gz", desc.getFormat());
	}
	@Test
	public void testGetJDKDescriptor2() throws Exception {
		JDKDescriptor desc = JDKDescriptors.getJDKDescriptor("jdk-8u66-linux-i586.rpm");
		Assert.assertEquals("8u66", desc.getVersion());
		Assert.assertEquals("linux", desc.getOs());
		Assert.assertEquals("x86", desc.getArch());
		Assert.assertEquals("rpm", desc.getFormat());
		Assert.assertEquals("jdk-8u66-linux-i586.rpm", desc.getFilename());
	}

}
