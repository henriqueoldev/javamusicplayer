package dev.henriqueol.JMP.view.Controls;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PlaylistContextMenu extends ContextMenu {
	private MenuItem	removeItem		= new MenuItem("Remove");
	private Runnable	removeAction	= () -> {};
	private MenuItem	addItem			= new MenuItem("Add");
	private Runnable	addAction		= () -> {};
	private MenuItem	moveUpItem		= new MenuItem("Move Up");
	private Runnable	moveUpAction	= () -> {};
	private MenuItem	moveDownItem	= new MenuItem("Move Down");
	private Runnable	moveDownAction 	= () -> {};
	
	PlaylistContextMenu() {
		this.getItems().add(addItem);
		this.getItems().add(removeItem);
		this.getItems().add(moveUpItem);
		this.getItems().add(moveDownItem);
		
		removeItem.setOnAction((e) -> {
			removeAction.run();
		});
		addItem.setOnAction((e) -> {
			addAction.run();
		});
		moveUpItem.setOnAction((e) -> {
			moveUpAction.run();
		});
		moveDownItem.setOnAction((e) -> {
			moveDownAction.run();
		});
	}

	public void setOnRemoveAction(Runnable removeAction) {
		this.removeAction = removeAction;
	}

	public void setOnAddAction(Runnable addAction) {
		this.addAction = addAction;
	}

	public void setOnMoveUpAction(Runnable moveUpAction) {
		this.moveUpAction = moveUpAction;
	}

	public void setOnMoveDownAction(Runnable moveDownAction) {
		this.moveDownAction = moveDownAction;
	}

	
}