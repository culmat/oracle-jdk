package com.bintray.origin;

import static java.lang.String.format;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;

public class JDKDescriptor {
	public String version, os, arch, format, filename;
	public HtmlAnchor link;
	
	public HtmlAnchor getLink() {
		return link;
	}

	public JDKDescriptor setLink(HtmlAnchor link) {
		this.link = link;
		return this;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}
	public String getOs_Arch() {
		return format("%s-%s", os,arch);
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "JDKDescriptor [version=" + version + ", os=" + os + ", arch=" + arch + ", format=" + format
				+ ", filename=" + filename + "]";
	}
	
}
