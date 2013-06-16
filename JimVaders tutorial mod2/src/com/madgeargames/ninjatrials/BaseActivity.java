package com.madgeargames.ninjatrials;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.EmptyTexture;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.Typeface;
import android.util.Log;

public class BaseActivity extends SimpleBaseGameActivity {
	
	/*
	static final int CAMERA_WIDTH = 1920; //800;
	static final int CAMERA_HEIGHT = 1080; //480;
	static final int CENTER_X = CAMERA_WIDTH / 2;
	static final int CENTER_Y = CAMERA_HEIGHT / 2;
	*/
	
	public final int CAMERA_WIDTH = 1920; //800;
	public final int CAMERA_HEIGHT = 1080; //480;
	public final int CENTER_X = CAMERA_WIDTH / 2;
	public final int CENTER_Y = CAMERA_HEIGHT / 2;
	
	

	public Font mFont, mFontCrono, mFontStroke;
	// public Texture mStrokeFontTexture = new Texture(256, 256, TextureOptions.BILINEAR);

	
	public Camera mCamera;

	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private ITextureRegion mSVGTestTextureRegions;// = new BaseTextureRegion();
	public Sprite mSpriteLogo;
	
	
	public BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlasHUD; // 1024*578
	public ITextureRegion mTexRegHUDPowerBar, mTexRegHUDPrecissionBar, mTexRegHUDLine, mTexRegHUDLineMark, mTexRegHUDHeads, mTexRegHUDEyeFire;
	public Sprite mSpriteHUD;
	
	
	
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
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);// 32); // con valores grandes, peta (posiblemente por el tamaño del texturemanager)
		
		
		FontFactory.setAssetBasePath("fonts/");
		mFontCrono = FontFactory.createFromAsset(
				this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, 
				this.getAssets(), "dom_parquim.ttf", 48, true, android.graphics.Color.WHITE
				);
		
		
		final int FONT_SIZE = 96; //48;
		final ITexture strokeFontTexture = new EmptyTexture(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		// final ITexture strokeFontTexture = new EmptyTexture(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		mFontStroke  = new StrokeFont(this.getFontManager(), strokeFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.YELLOW, 2, Color.RED);
		this.mFontStroke.load();
		
		// mFontStroke = new StrokeFont(this.mStrokeFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.BLACK, 2, Color.WHITE);

		
		// getFontManager().loadFonts(this.mFont, this.mFontCrono, this.mFontStroke);
		
		 mFont.load();
		 this.mFontCrono.load();
	}
	
	private void loadGraphics() {
		
		// Logo Splash
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mSVGTestTextureRegions = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "logo_madgear.svg", 800, 800);
		try {
			this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.mBuildableBitmapTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		this.mSpriteLogo = new Sprite(CENTER_X, CENTER_Y, 800, 800, mSVGTestTextureRegions, this.getVertexBufferObjectManager());
		
		
		
		
		
		// Gráficos del HUD
		this.mBuildableBitmapTextureAtlasHUD = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT); // 1024*578
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		mTexRegHUDPowerBar = 
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBuildableBitmapTextureAtlasHUD, this, "hud_power_bar.png");
		mTexRegHUDPrecissionBar = 
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBuildableBitmapTextureAtlasHUD, this, "hud_precission_bar.png");
		mTexRegHUDHeads = 
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBuildableBitmapTextureAtlasHUD, this, "hud_heads.png");
		
		try { // Buildable bitmap texture atlases require a try/catch statement
			mBuildableBitmapTextureAtlasHUD.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		mBuildableBitmapTextureAtlasHUD.load(); // Once the atlas has been built, we can now load
		
		
		
		
		//this.mTexRegHUDPowerBar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlasHUD, 
		//																				 this.getAssets(), "gfx/hud_power_bar.png");
		//this.mSpriteHUD = new Sprite(CENTER_X, CENTER_Y, 120, 240, this.mTexRegHUDPowerBar, this.getVertexBufferObjectManager());
		
		
		
		
		
		
		/*
		private ITextureRegion mTexRegHUDPrecissionBar;
		private ITextureRegion mTexRegHUDLine;
		private ITextureRegion mTexRegHUDLineMark;
		private ITextureRegion mTexRegHUDHeads;
		private ITextureRegion mTexRegHUDEyeFire;
		
		
		
		hud_fire.png
		hud_heads.png
		hud_line_bar.png
		hud_line_mark.png
		hud_power_bar.png
		hud_precission_bar.png

		*/
		
		// mTextureRegionHUD; 
		// mHUD;
				
		
		
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
		
		// mCurrentScene = new SceneSplash();
		// mCurrentScene = new SceneMainMenu();
		mCurrentScene = new SceneGameRun();
		

		

		
		
		
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
		Log.v("NinjaTrials", "BaseActivity BackPressed " + mCurrentScene.toString());
		if (mCurrentScene instanceof SceneGameShoot){
			((SceneGameShoot) mCurrentScene).detach(); // linea original
			setCurrentScene(new SceneMainMenu());
			// hay que arreglarlo, mirar lo que se usa tras mostrar la pantalla de resultados para resetearlo todo
			return;
		}
		if (mCurrentScene instanceof SceneOptionsMenu){
			setCurrentScene(new SceneMainMenu());
			return;
		}
		
		if (mCurrentScene instanceof SceneSelectPlayer){
			setCurrentScene(new SceneMainMenu());
			return;
		}
		if (mCurrentScene instanceof SceneGameRun){
			((SceneGameRun)mCurrentScene).resetScene(); // Cuando todas las escenas tengan su resetScene() no hará falta el casting 
			setCurrentScene(new SceneMainMenu());
			return;
		}
		if (mCurrentScene instanceof SceneMainMenu){
			setCurrentScene(new SceneSplash());
			return;
		}
		
		mCurrentScene = null;
		SensorListener.instance = null;
		super.onBackPressed();
	}

}