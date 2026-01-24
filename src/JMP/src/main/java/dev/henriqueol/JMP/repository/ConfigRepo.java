package dev.henriqueol.JMP.repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class ConfigRepo {
	JSONObject appConfig;
	JSONObject userConfig;
	
	public ConfigRepo() {
		loadAppConfig();
		if (appConfig != null) {
			System.out.println("\u001B[32m Loaded App Configurations \u001B[0m");
		}
	}
	
	private void loadAppConfig() {
		try {
			appConfig = new JSONObject(
					Files.readString(
							Paths.get(getClass().getResource("/config/appConfig.json").toURI())
					)
			);
		} catch (Exception e) {
			System.err.println("Configuration Loading ERROR:" + e.getMessage());
			appConfig = null;
		}
	}

	public JSONObject getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(JSONObject appConfig) {
		this.appConfig = appConfig;
	}

	public JSONObject getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(JSONObject userConfig) {
		this.userConfig = userConfig;
	}
}
