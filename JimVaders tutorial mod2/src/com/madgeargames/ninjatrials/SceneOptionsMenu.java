package com.madgeargames.ninjatrials;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class SceneOptionsMenu extends Scene {
	BaseActivity activity;
	
	private Sprite spriteLogoMG; // = BaseActivity.mSpriteLogo;
	private float timePreLogo, timeLogoIn, timeLogoStay, timeLogoOut, timePostLogo;
	
	public SceneOptionsMenu() {
		
		activity = BaseActivity.getSharedInstance();
		
		spriteLogoMG = activity.mSpriteLogo;
		
		timePreLogo = 0.5f;
		timeLogoIn = 0.5f;
		timeLogoStay = 1f;
		timeLogoOut = 0.5f;
		timePostLogo = 0.5f;
		
		
		
		setBackground(new Background(0, 1.0f, 0));
				
		Text title1 = new Text(activity.CENTER_X , activity.CAMERA_HEIGHT*9/10, activity.mFont,
		        "Menu Opciones",
		        activity.getVertexBufferObjectManager());
			
		    //title1.setPosition(activity.CENTER_X , activity.CENTER_Y);
		    
		    attachChild(title1);
		    
		
		loadResources();
	}

	void loadResources() {
		DelayModifier dMod = new DelayModifier(timePreLogo+timeLogoIn+timeLogoStay+timeLogoOut+timePostLogo, //4,
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
						
						// spriteLogoMG.detachSelf(); // Importante o no se podr� volver a crear la SceneSplash
						
						// activity.mCurrentScene.detachSelf();
						
						// activity.setCurrentScene(new SceneMainMenu());
					}
				});

		registerEntityModifier(dMod);
	}

}
