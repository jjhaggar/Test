package com.madgeargames.ninjatrials;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import com.madgeargames.ninjatrials.R;

public class SceneResultShoot extends CameraScene implements IOnSceneTouchListener {

	boolean done;
	BaseActivity activity;

	public SceneResultShoot(Camera mCamera) {
		super(mCamera);
		activity = BaseActivity.getSharedInstance();
		setBackgroundEnabled(false);
		SceneGameShoot scene = (SceneGameShoot) activity.mCurrentScene;
		float accureay = 1 - (float) scene.missCount / scene.bulletCount;
		if (Float.isNaN(accureay))
			accureay = 0;
		accureay *= 100;
		Text result = new Text(0, 0, activity.mFont,
				activity.getString(R.string.accuracy) + ": "
						+ String.format("%.2f", accureay) + "%", BaseActivity
						.getSharedInstance().getVertexBufferObjectManager());

		final int x = (int) (mCamera.getWidth() / 2 - result.getWidth() / 2);
		final int y = (int) (mCamera.getHeight() / 2 - result.getHeight() / 2);

		done = false;
		result.setPosition(x, mCamera.getHeight() + result.getHeight());
		MoveYModifier mod = new MoveYModifier(5, result.getY(), y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				done = true;
			}
		};
		attachChild(result);
		result.registerEntityModifier(mod);
		setOnSceneTouchListener(this);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (!done)
			return true;
		((SceneGameShoot) activity.mCurrentScene).resetValues();
		return false;
	}

}
