package dev.henriqueol.JMP.view.Controls;

import dev.henriqueol.JMP.controller.ConfigController;
import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.model.Defaults;
import dev.henriqueol.JMP.model.UIDefaults;
import dev.henriqueol.JMP.view.windows.AboutWindow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BottomControls extends HBox {
	PlayerController playerController = null;
	IconController icons = new IconController();
	ConfigController configController;
	
	AboutWindow aboutWindow;
	
	Button burguerBtn = new Button();
	Button shuffleBtn = new Button();
	Button repeatBtn = new Button();
	
	ContextMenu extrasMenu = new ContextMenu();
	MenuItem aboutMenuItem = new MenuItem("About");
	
	public BottomControls(PlayerController playerController, ConfigController configController) {
		this.configController = configController;
		this.playerController = playerController;
		this.aboutWindow = new AboutWindow(configController);
		
		burguerBtn.setGraphic(new ImageView(icons.getIconImage("dots-three", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
		setShuffleButton();
		setRepeatButton();
		
		this.setAlignment(Pos.CENTER_RIGHT);
		this.getChildren().addAll(repeatBtn, shuffleBtn, burguerBtn);
		
		extrasMenu.getItems().add(aboutMenuItem);
		
		//Handles
		shuffleBtn.setOnAction((e) -> {
			switchShuffle();
		});
		
		repeatBtn.setOnAction((e) -> {
			setRepeatButton();
		});
		
		aboutMenuItem.setOnAction((e) -> {
			aboutWindow.show();
		});
		
		burguerBtn.setContextMenu(extrasMenu);
		burguerBtn.setOnMouseClicked((e) -> {
			extrasMenu.show(burguerBtn, e.getScreenX(), e.getScreenY());
			extrasMenu.requestFocus();
		});
		
	}
	
	private void setRepeatButton() {
		switch (playerController.getRepeatOption()) {
		case Defaults.REPEAT_NONE:
			repeatBtn.setGraphic(new ImageView(icons.getIconImage("repeat-once", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
			playerController.setRepeatOption(Defaults.REPEAT_ONE);
			break;
		case Defaults.REPEAT_ONE:
			repeatBtn.setGraphic(new ImageView(icons.getIconImage("repeat", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
			playerController.setRepeatOption(Defaults.REPEAT_ALL);
			break;
		case Defaults.REPEAT_ALL:
			repeatBtn.setGraphic(new ImageView(icons.getIconImage("arrow-right", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
			playerController.setRepeatOption(Defaults.REPEAT_NONE);
			break;
		default:
			break;
		}
	}
	
	private void setShuffleButton() {
		if (playerController.isShuffleQueue()) {
			shuffleBtn.setGraphic(new ImageView(icons.getIconImage("shuffle", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
		} else {
			shuffleBtn.setGraphic(new ImageView(icons.getIconImage("arrows-left-right", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
		}
	}
	
	private void switchShuffle() {
		playerController.setShuffleQueue(!playerController.isShuffleQueue());
		setShuffleButton();
	}
}
