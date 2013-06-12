package com.madgeargames.ninjatrials;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.graphics.Typeface;
import android.util.Log;

public class BaseActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	static final int CENTER_X = CAMERA_WIDTH / 2;
	static final int CENTER_Y = CAMERA_HEIGHT / 2;
	
	

	public Font mFont;
	public Camera mCamera;

	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private ITextureRegion mSVGTestTextureRegions;// = new BaseTextureRegion();
	
	
	// A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;

	public static BaseActivity getSharedInstance() {
		return instance;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {

		
		loadFonts();
		loadGraphics();
		loadMusic();		
	    loadSounds();
		
	}

	private void loadFonts() {
		mFont = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mFont.load();
	}
	
	private void loadGraphics() {
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mSVGTestTextureRegions = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "logo_madgear.svg", 400, 400);
		
		try {
			this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.mBuildableBitmapTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
	}
	
	private void loadMusic() {
		// TODO Auto-generated method stub
		
	}
	
	private void loadSounds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SceneSplash();
		
		mCurrentScene.attachChild( new Sprite(CENTER_X, CENTER_Y, 400, 400, mSVGTestTextureRegions, this.getVertexBufferObjectManager()) );
		
		return mCurrentScene;
	}
	
	// to change the current main scene
	public void setCurrentScene(Scene scene) {
		mCurrentScene = null;
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);

	}
	
	@Override
	public void onBackPressed() {
		Log.v("Jimvaders",
				"BaseActivity BackPressed " + mCurrentScene.toString());
		if (mCurrentScene instanceof SceneGame)
			((SceneGame) mCurrentScene).detach();

		mCurrentScene = null;
		SensorListener.instance = null;
		super.onBackPressed();
	}

}