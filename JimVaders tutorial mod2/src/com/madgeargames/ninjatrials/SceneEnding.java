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
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;

import android.graphics.Typeface;
import android.util.Log;

public class SceneEnding extends Scene {
	BaseActivity activity;
	
	private Sprite spriteLogoMG; // = BaseActivity.mSpriteLogo;
	private float timePreLogo, timeLogoIn, timeLogoStay, timeLogoOut, timePostLogo;
	
	// Homenaje:
	private BitmapTextureAtlas homenajeTexture;
	private ITextureRegion homenajeTextureRegion;
	private Sprite homenajeSprite;
	//private Font mFont = FontFactory.create(activity.getFontManager(), activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
	private Text homenajeText;
	// fin
	
	
	public SceneEnding() {
		
		activity = BaseActivity.getSharedInstance();
		
		spriteLogoMG = activity.mSpriteLogo;
		
		timePreLogo = 0.5f;
		timeLogoIn = 0.5f;
		timeLogoStay = 1f;
		timeLogoOut = 0.5f;
		timePostLogo = 0.5f;
		
		
		
		setBackground(new Background(1, 1, 1));
				
		// spriteLogoMG.setPosition(activity.CENTER_X, activity.CENTER_Y);
		
//		spriteLogoMG.setAlpha(0);
//		spriteLogoMG.setScale(0.9f);
//		
//		attachChild(spriteLogoMG);
//		
//		spriteLogoMG.registerEntityModifier(
//			new SequenceEntityModifier(
//				new DelayModifier(timePreLogo),// en negro, antes del logo
//				new ParallelEntityModifier(// entrada logo
//						new AlphaModifier(timeLogoIn, 0, 1), 
//						new ScaleModifier(timeLogoIn, 0.95f, 1)
//				),
//				new DelayModifier(timeLogoStay),// logo quieto
//				new ParallelEntityModifier(// entrada logo
//						new AlphaModifier(timeLogoOut, 1, 0), 
//						new ScaleModifier(timeLogoOut, 1, 0.95f)
//				),
//				new DelayModifier(timePostLogo) // en negro, después del logo
//			)
//		);
		
		
		// Añadimos "homenaje"  :P
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    homenajeTexture = new BitmapTextureAtlas(activity.getTextureManager(), activity.CAMERA_HEIGHT, activity.CAMERA_WIDTH, TextureOptions.DEFAULT);
	    homenajeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(homenajeTexture, activity, "ark-1.gif", 0, 0);
	    homenajeTexture.load(); 
	    
	    homenajeSprite = new Sprite(activity.CENTER_X, activity.CENTER_Y, homenajeTextureRegion, activity.getVertexBufferObjectManager());
	    
		attachChild(homenajeSprite);
		

		homenajeText = new Text(activity.CENTER_X, 100, activity.mFont, "Arkanoid ZX Spectrum", new TextOptions(HorizontalAlign.CENTER), activity.getVertexBufferObjectManager());
		homenajeText.setColor(1f, 0f, 1f);
		
		attachChild(homenajeText);
		
		
		
		
		//loadResources();
		

		
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
						
						spriteLogoMG.detachSelf(); // Importante o no se podrá volver a crear la SceneSplash
						
						// activity.mCurrentScene.detachSelf();
						
						activity.setCurrentScene(new SceneMainMenu());
					}
				});

		registerEntityModifier(dMod);
	}

}
