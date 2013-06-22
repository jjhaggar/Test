package com.madgeargames.ninjatrials;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import android.util.Log;

public class CharacterRun extends Entity { // extends Entity{
	
	
	// Debe crear los personajes en pantalla (extends AnimSprite? OtrotipoSprite? Entity a secas?)
	// Debe poder cambiar su animación
	// Debe poder desplazarse por la pantalla
	// Debe cargar un sólo Spritesheet, el de "correr" 
	// Mejor Entity, así puedo "apilar capas" en el personaje (sombras, efectos, etc)
	
	private float speedMultiplier = 1;
	
	private IAnimationListener anilis;
	
	public AnimatedSprite zSprite;
	
	public static final int RUN_CHARGE_1 = 0;
	public static final int RUN_CHARGE_2 = 1;
	public static final int RUN_CHARGE_3 = 2;
	public static final int RUN_STANDING = 3;
	public static final int RUN_NORMAL = 4;
	public static final int RUN_FAST = 5;
	public static final int RUN_FASTEST = 6;
	
	
	CharacterRun(){
		
		BaseActivity instance = BaseActivity.instance;
		
		BuildableBitmapTextureAtlas zBitmapTextureAtlas = 
				new BuildableBitmapTextureAtlas(instance.getTextureManager(), 1088, 1280, TextureOptions.BILINEAR); // debería ser 1024 y 1024
		 
		TiledTextureRegion zTextureRegion = 
				BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(zBitmapTextureAtlas, instance, "running_ryoko.png", 4, 4);

		try {
			zBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			zBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		
		zSprite = new AnimatedSprite(instance.CAMERA_WIDTH * 0.25f, instance.CAMERA_HEIGHT * 0.33f, zTextureRegion, instance.getVertexBufferObjectManager());
		// zSprite.setScale(8, 8);
		
		// zSprite.animate(100);
		// zSprite.animate (new long[] {100, 100, 100, 100, 100, 100}, 
		//		 		 new int [] { 3,   4,    5,   6,   7,   8}, true); //new int [] {0,   1,   2, 3, 4, 5, 6, 7, 8, 9, 10, 11  }, true);
		
		// updateAnimation(RUN_STAND, 1);
		
		this.attachChild(zSprite);
		
		
		
		
		
		
		
		
		
	}
	
	
	void updateSpeedMultiplier(float newSpeed){
		this.speedMultiplier = newSpeed; 
		zSprite.animate(100);
		
	}
	
	
	void goToFrame(int frame){
		zSprite.setCurrentTileIndex(frame);
	}
	
	
	void updateStandAnimation(float power){
		
		if (power == 0)
			zSprite.setCurrentTileIndex(RUN_CHARGE_1);
		else if  ( 1 <= power && power < 50)
			zSprite.setCurrentTileIndex(RUN_CHARGE_2);
		else if  ( 50 <= power && power < 90)
			zSprite.setCurrentTileIndex(RUN_CHARGE_3);
		else if  ( power >= 90)
			zSprite.setCurrentTileIndex(RUN_CHARGE_3); // Aquí habría que ponerle aura a lo Dragon Ball
		
	}
	
	void updateRunAnimation(float power){
		
		// this.speedMultiplier = speedMultiplier;
		// float sM = this.speedMultiplier;
		
		anilis  = new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				updateRunAnimation(SceneGameRun.power);
				Log.v("Ninja Trials", "SceneGameRun.power = "+SceneGameRun.power);
			}
		};
		
		
		float sM; 
		
		if (power>0){
			

			
			sM = 50/power; // speedMultiplier, variará entre 
			
			if (power <= 50){ // Run normal 
				zSprite.animate(new long[]{(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM)}, 
		 		 		new int []{3, 4, 5, 6, 7, 8}, false, anilis);
			}
			
			if (power > 50 &&power <= 90){ // Run fast
				zSprite.animate(new long[]{(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM)}, 
		 		 		new int []{9, 10, 11, 12, 13, 14}, false, anilis);
			}
			
			if (power > 90){ // Run fastest
				zSprite.animate(new long[]{(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM),(long)(100*sM)}, 
		 		 		new int []{9, 10, 11, 12, 13, 14}, false, anilis);
			}
				
			
			
			
		}
		
		else { // if power == 0  // Standing animation
			zSprite.animate(new long[]{300, 300, 300, 300},  
							new int []{  0,   1,   2,   1}, false, anilis); // TODO: caso especial, no se puede dejar la animación en manos de "anilis"
																			// pasa lo mismo con el caso de run normal a la mínima velocidad
																			// quizás habría que cambiar el método en el que se cambian las animaciones
		}
		
		
		
		
	}
	
	
	
	
	
	
	
}
