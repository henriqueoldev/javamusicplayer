package dev.henriqueol.JMP.controller;

import java.net.URI;
import java.net.URL;

import org.json.JSONArray;

import dev.henriqueol.JMP.repository.ConfigRepo;
import javafx.application.HostServices;

public class ConfigController {
	private ConfigRepo configRepository;
	private HostServices hostServices;
	private URL defaultStylePath = this.getClass().getResource("/styles/app.css");
	
	public ConfigController(HostServices hostServices) {
		this.hostServices = hostServices;
		this.configRepository = new ConfigRepo();
	}
	
	public URL getAppStylePath() {
		return defaultStylePath;
	}
	
	// App Properties
	public String getAppName() {
		try {
			return configRepository.getAppConfig().getString("AppName");
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getAppDescription() {
		try {
			return configRepository.getAppConfig().getString("AppDescription");
		} catch (Exception e) {
				return null;
			}
	}
	
	public JSONArray getAppLibs() {
		try {
			return configRepository.getAppConfig().getJSONArray("AppLibs");
		} catch (Exception e) {
				return null;
			}
	}
	
	public URI getAppHelpURI() {
		try {
			return new URI(configRepository.getAppConfig().getString("HelpURL"));
		} catch (Exception e) {
				return null;
			}
	}
	
	public URI getAppGithubURI() {
		try {
			return new URI(configRepository.getAppConfig().getString("GithubURL"));
		} catch (Exception e) {
				return null;
			}
	}
	
	public HostServices getAppHostsServices() {
		return hostServices;
	}
}
