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
	final int MENU_START_STORYMODE = 2;
	final int MENU_OPTIONS = 3;

	public SceneMainMenu() {
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();

		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		IMenuItem buttonStart_1 = new TextMenuItem(MENU_START_RUN, activity.mFont,
				activity.getString(R.string.start)+" Running",
				activity.getVertexBufferObjectManager());
		//buttonStart_1.setPosition(activity.CENTER_X/2, activity.CENTER_Y+activity.CENTER_Y/2 ); // startButton.getHeight() // startButton.setScale(4); 
		buttonStart_1.setPosition(activity.CENTER_X, activity.CAMERA_HEIGHT * 4 /5);
		
		IMenuItem buttonStart_2 = new TextMenuItem(MENU_START_SHOOT, activity.mFont,
				activity.getString(R.string.start)+" Shooting",
				activity.getVertexBufferObjectManager());
		//buttonStart_2.setPosition(activity.CENTER_X + activity.CENTER_X/2, activity.CENTER_Y+activity.CENTER_Y/2 );  
		buttonStart_2.setPosition(activity.CENTER_X, activity.CAMERA_HEIGHT * 3 /5);
		
		IMenuItem buttonStart_3 = new TextMenuItem(MENU_START_STORYMODE, activity.mFont,
				activity.getString(R.string.start)+" StoryMode",
				activity.getVertexBufferObjectManager());
		//buttonStart_3.setPosition(activity.CENTER_X/2, activity.CENTER_Y/2 );  
		buttonStart_3.setPosition(activity.CENTER_X, activity.CAMERA_HEIGHT * 2 /5);
		
		IMenuItem buttonStart_4 = new TextMenuItem(MENU_OPTIONS, activity.mFont,
				"Options",
				activity.getVertexBufferObjectManager());
		//buttonStart_4.setPosition(activity.CENTER_X + activity.CENTER_X/2, activity.CENTER_Y/2 );  
		buttonStart_4.setPosition(activity.CENTER_X, activity.CAMERA_HEIGHT * 1 /5);
		
		this.addMenuItem(buttonStart_1);
		this.addMenuItem(buttonStart_2);
		this.addMenuItem(buttonStart_3);
		this.addMenuItem(buttonStart_4);

		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		
		Log.v("NinjaTrials", "Opcion " + arg1.getID() +" pulsada");
		
		// Log.v("NinjaTrials", "Opciones " + MENU_START_RUN +", "+ MENU_START_SHOOT +", "+ MENU_START_STORYMODE +", "+ MENU_OPTIONS +", ");
		
		switch (arg1.getID()) {
		case MENU_START_RUN:
			activity.setCurrentScene(new SceneGameRun());
			return true;
		case MENU_START_SHOOT:
			activity.setCurrentScene(new SceneGameShoot());
			return true;
		case MENU_START_STORYMODE:
			activity.setCurrentScene(new SceneSelectPlayer());
			return true;
		case MENU_OPTIONS:
			activity.setCurrentScene(new SceneOptionsMenu());
			return true;
		default:
			break;
		}
		return false;
	}

}
