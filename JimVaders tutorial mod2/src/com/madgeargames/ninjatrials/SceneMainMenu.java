package com.madgeargames.ninjatrials;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;

import android.util.Log;

import com.madgeargames.ninjatrials.R;

//placeHolder scene class for the main menu, currently only includes a start menu item 
public class SceneMainMenu extends MenuScene implements
		IOnMenuItemClickListener {
	BaseActivity activity;
	final int MENU_START_RUN = 0;
	final int MENU_START_SHOOT = 1;

	public SceneMainMenu() {
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();

		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		IMenuItem buttonStart_1 = new TextMenuItem(MENU_START_RUN, activity.mFont,
				activity.getString(R.string.start)+" RUN",
				activity.getVertexBufferObjectManager());
		buttonStart_1.setPosition(activity.CENTER_X, activity.CENTER_Y + buttonStart_1.getHeight()); // startButton.getHeight() // startButton.setScale(4); 
		
		IMenuItem buttonStart_2 = new TextMenuItem(MENU_START_SHOOT, activity.mFont,
				activity.getString(R.string.start)+" Shoot",
				activity.getVertexBufferObjectManager());
		buttonStart_2.setPosition(activity.CENTER_X, activity.CENTER_Y - buttonStart_2.getHeight());  
		
		
		
		
		
		addMenuItem(buttonStart_1);
		addMenuItem(buttonStart_2);

		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		switch (arg1.getID()) {
		case MENU_START_RUN:
			activity.setCurrentScene(new SceneSplash());
			return true;
		case MENU_START_SHOOT:
			Log.v("NinjaTrials", "PRE Nueva escena");
			activity.setCurrentScene(new SceneGame());
			Log.v("NinjaTrials", "POST Nueva escena");
			return true;
		default:
			break;
		}
		return false;
	}

}
