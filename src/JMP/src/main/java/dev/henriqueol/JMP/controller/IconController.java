package dev.henriqueol.JMP.controller;

import javafx.scene.image.Image;

public class IconController {
	public Image getIconImage(String iconName, double width, double height, boolean isLight) {
		String path = String.format("/icons/%s-%s.png", iconName, isLight == true ? "light" : "dark");
		return new Image(getClass().getResource(path).toString(), width, height, true, true);
	}
	
	public Image getIconImage(String iconName, double width, double height) {
		String path = String.format("/icons/%s-light.png", iconName);
		return new Image(getClass().getResource(path).toString(), width, height, true, true);
	}
}
