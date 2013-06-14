package com.madgeargames.ninjatrials;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class SceneSplash extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	
	private Sprite spriteLogoMG; // = BaseActivity.mSpriteLogo;
	private float timePreLogo, timeLogoIn, timeLogoStay, timeLogoOut, timePostLogo;
	private SequenceEntityModifier mSeqEntMod;
	private DelayModifier dMod;
	
	
	public SceneSplash() {
		
		activity = BaseActivity.getSharedInstance();
		
		spriteLogoMG = activity.mSpriteLogo;
		
		timePreLogo = 0.5f;
		timeLogoIn = 0.5f;
		timeLogoStay = 1f;
		timeLogoOut = 0.5f;
		timePostLogo = 0.5f;
		
		
		
		setBackground(new Background(0, 0, 0));
				
		// spriteLogoMG.setPosition(activity.CENTER_X, activity.CENTER_Y);
		
		spriteLogoMG.setAlpha(0);
		spriteLogoMG.setScale(0.9f);
		
		attachChild(spriteLogoMG);
		
		mSeqEntMod= new SequenceEntityModifier(
						new DelayModifier(timePreLogo),// en negro, antes del logo
						new ParallelEntityModifier(// entrada logo
								new AlphaModifier(timeLogoIn, 0, 1), 
								new ScaleModifier(timeLogoIn, 0.95f, 1)
						),
						new DelayModifier(timeLogoStay),// logo quieto
						new ParallelEntityModifier(// entrada logo
								new AlphaModifier(timeLogoOut, 1, 0), 
								new ScaleModifier(timeLogoOut, 1, 0.95f)
						),
						new DelayModifier(timePostLogo) // en negro, después del logo
					);
		
		spriteLogoMG.registerEntityModifier(mSeqEntMod);
		
		setOnSceneTouchListener(this);
		
		
		loadResources();
	}

	void loadResources() {
		dMod = new DelayModifier(timePreLogo+timeLogoIn+timeLogoStay+timeLogoOut+timePostLogo, //4,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						// TODO Auto-generated method stub
						// musica
						
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						
						spriteLogoMG.detachSelf(); // Importante o no se podrá volver a crear la SceneSplash
						
						// activity.mCurrentScene.detachSelf();
						
						activity.setCurrentScene(new SceneMainMenu());
					}
				});

		registerEntityModifier(dMod);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		Log.v("Ninja Trials", "Pantalla tocada en "+activity.mCurrentScene.toString() );
		spriteLogoMG.unregisterEntityModifier(mSeqEntMod);
		
		unregisterEntityModifier(dMod);
		
		spriteLogoMG.detachSelf();
		activity.setCurrentScene(new SceneMainMenu());
		return false;
	}

}
