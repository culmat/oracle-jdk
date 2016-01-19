package com.bintray.origin.repack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public interface Repack {
	
	String getNativeExtension();
	
	default File repack(String fileName) throws IOException, FileNotFoundException, InterruptedException {
		String version = fileName.replace(getNativeExtension(), "");
		String simpleName = getClass().getSimpleName();
		File sh = new File( simpleName+"-"+version+".sh");
		IOUtils.copy(RepackWin.class.getResourceAsStream(simpleName+".sh"), new FileOutputStream(sh));
		sh.setExecutable(true);
		
		ProcessBuilder pb = new ProcessBuilder(
				"bash", "-c", 
				sh.getAbsolutePath() + " " + version
				)
		.redirectInput(ProcessBuilder.Redirect.INHERIT)
		.redirectOutput(ProcessBuilder.Redirect.INHERIT)
		.redirectError(ProcessBuilder.Redirect.INHERIT)
		.directory(new File("."));
		Map<String, String> environment = pb.environment();
		environment.put("PATH", "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin");
		
		final Process process = pb
		.start();
		
		process.waitFor();
		sh.delete();
		return new File(version+".zip");
	}
}
