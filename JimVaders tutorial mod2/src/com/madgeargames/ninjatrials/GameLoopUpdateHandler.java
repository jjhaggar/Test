package com.madgeargames.ninjatrials;

import org.andengine.engine.handler.IUpdateHandler;

public class GameLoopUpdateHandler implements IUpdateHandler{

	@Override
	public void onUpdate(float pSecondsElapsed) {
		((SceneGameShoot)BaseActivity.getSharedInstance().mCurrentScene).moveShip();
		((SceneGameShoot)BaseActivity.getSharedInstance().mCurrentScene).cleaner();
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
