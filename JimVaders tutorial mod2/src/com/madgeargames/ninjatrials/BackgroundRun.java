package com.madgeargames.ninjatrials;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.util.Log;

public class BackgroundRun extends Entity {
	
	
	private ITexture mParallaxLayerBackTexture;
	private ITexture mParallaxLayerMidTexture;
	private ITexture mParallaxLayerFrontTexture;

	private ITextureRegion mParallaxLayerBackTextureRegion;
	private ITextureRegion mParallaxLayerMidTextureRegion;
	private ITextureRegion mParallaxLayerFrontTextureRegion;

	private ParallaxEntity mParallaxLayerFrontParallaxEntity;
	
	private AutoParallaxBackground autoParallaxBackground;
	
	// private Music music;
	private BaseActivity instance;
	
	
	BackgroundRun() throws IOException{
		
		instance = BaseActivity.getSharedInstance();  

		this.mParallaxLayerBackTexture = new AssetBitmapTexture(instance .getTextureManager(), instance .getAssets(), 
				"gfx/parallax_background_layer_back.png");
		this.mParallaxLayerBackTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerBackTexture);
		this.mParallaxLayerBackTexture.load();

		this.mParallaxLayerMidTexture = new AssetBitmapTexture(instance .getTextureManager(), instance.getAssets(), 
				"gfx/parallax_background_layer_mid.png");
		this.mParallaxLayerMidTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerMidTexture);
		this.mParallaxLayerMidTexture.load();

		this.mParallaxLayerFrontTexture = new AssetBitmapTexture(instance .getTextureManager(), instance.getAssets(), 
				"gfx/parallax_background_layer_front.png");
		this.mParallaxLayerFrontTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerFrontTexture);
		this.mParallaxLayerFrontTexture.load();
		
		
		
		
		
		
		
		
		
		final VertexBufferObjectManager vertexBufferObjectManager = instance.getVertexBufferObjectManager();

		final Scene scene = new Scene();
		//final 
		autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		
		
		
		scene.setBackground(autoParallaxBackground);
		this.attachChild(scene); // new

		final Sprite parallaxLayerBackSprite = new Sprite(0, 306, this.mParallaxLayerBackTextureRegion, vertexBufferObjectManager);
		parallaxLayerBackSprite.setOffsetCenter(0, 0);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, parallaxLayerBackSprite));

		final Sprite parallaxLayerMidSprite = new Sprite(0, instance.CAMERA_HEIGHT - this.mParallaxLayerMidTextureRegion.getHeight(), 
				this.mParallaxLayerMidTextureRegion, vertexBufferObjectManager);
		parallaxLayerMidSprite.setOffsetCenter(0, 0);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, parallaxLayerMidSprite));

		final Sprite parallaxLayerFrontSprite = new Sprite(0, 0, this.mParallaxLayerFrontTextureRegion, vertexBufferObjectManager);
		parallaxLayerFrontSprite.setOffsetCenter(0, 0);
		mParallaxLayerFrontParallaxEntity = new ParallaxEntity(-120.0f, parallaxLayerFrontSprite);
		autoParallaxBackground.attachParallaxEntity( mParallaxLayerFrontParallaxEntity );
		
		
		autoParallaxBackground.setParallaxChangePerSecond(0);// new
		
		
		
		
		
		
	}
	
	public void updateSpeed(float speed){
		autoParallaxBackground.setParallaxChangePerSecond(speed);
		
	}
	
	
	
	
	
	
}
