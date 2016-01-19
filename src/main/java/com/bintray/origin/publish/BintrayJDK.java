package com.bintray.origin.publish;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static java.lang.String.format;
import static java.util.Collections.emptyMap;

import java.io.InputStream;

import com.bintray.origin.JDKDescriptor;
import com.jfrog.bintray.client.api.handle.Bintray;
import com.jfrog.bintray.client.impl.BintrayClient;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class BintrayJDK {

	String token = System.getProperty("bintray.token");
	String user = "oracle-jdk";
	String url = "https://api.bintray.com";
	
	Bintray lazybintray;
	private Bintray bintray() {
		if(lazybintray == null) {
			lazybintray = BintrayClient.create(user, token);
		}
		return lazybintray;
	}
	
	private static BintrayJDK instance;
	
	private BintrayJDK() {
		//singleton
	}
	
	public static BintrayJDK getInstance() {
		if (instance == null) {
			instance = new BintrayJDK();
		}
		return instance;
	}

	public String getLatestVersion(JDKDescriptor desc) throws UnirestException {
		String versionUrl = format("%s/packages/%s/generic/%s/versions/_latest",url,user,desc.getOs_Arch());
		HttpResponse<JsonNode> resp = get(versionUrl).basicAuth(user, token)
		.header("accept", "application/json").asJson();
		return resp.getStatus() > 400 ? "undefined" :
				resp.getBody().getObject().getString("name");
	}

	public void upload(JDKDescriptor desc, InputStream in) throws Exception {
		createVersion(desc);
		org.apache.http.HttpResponse resp = bintray().put(format("/content/%s/%s/%s/%s/%s?publish=1&override=0&explode=0", user, "generic", desc.getOs_Arch(), desc.getVersion(), desc.getFilename()), emptyMap(), in );
		System.out.println(resp.getStatusLine().toString());
	}

	public void createVersion(JDKDescriptor desc) throws Exception {
		post(url + "/packages/{subject}/{repo}/{package}/versions")
		.basicAuth(user, token)
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.routeParam("subject", user)
		.routeParam("repo", "generic")
		.routeParam("package", desc.getOs_Arch())
		.body(format("{\"name\": \"%s\",\"desc\": \"\"}", desc.getVersion()))
		.asString();
	}

}
