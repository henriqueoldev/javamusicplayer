package dev.henriqueol.JMP.view.Controls;

import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.model.Defaults;
import dev.henriqueol.JMP.model.UIDefaults;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BottomControls extends HBox {
	Button burguerBtn = new Button();
	Button shuffleBtn = new Button();
	Button repeatBtn = new Button();
	
	PlayerController playerController = null;
	IconController icons = new IconController();
	
	public BottomControls(PlayerController playerController) {
		this.playerController = playerController;
		
		burguerBtn.setGraphic(new ImageView(icons.getIconImage("dots-three", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
		repeatBtn.setGraphic(new ImageView(icons.getIconImage("repeat", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
		shuffleBtn.setGraphic(new ImageView(icons.getIconImage("arrows-left-right", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
		
		this.setAlignment(Pos.CENTER_RIGHT);
		this.getChildren().addAll(repeatBtn, shuffleBtn, burguerBtn);
		
		//Handles
		shuffleBtn.setOnAction((e) -> {
			playerController.setShuffleQueue(!playerController.isShuffleQueue());
			if (playerController.isShuffleQueue()) {
				shuffleBtn.setGraphic(new ImageView(icons.getIconImage("shuffle", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
			} else {
				shuffleBtn.setGraphic(new ImageView(icons.getIconImage("arrows-left-right", UIDefaults.UI_ICON_SMALLER.getWidth(), UIDefaults.UI_ICON_SMALLER.getHeight())));
			}
		});
		
		repeatBtn.setOnAction((e) -> {
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
			System.out.println(playerController.getRepeatOption());
		});
	}
}
