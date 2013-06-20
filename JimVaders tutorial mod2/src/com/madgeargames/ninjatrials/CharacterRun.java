package com.madgeargames.ninjatrials;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

public class CharacterRun extends Entity { // extends Entity{
	
	
	// Debe crear los personajes en pantalla (extends AnimSprite? OtrotipoSprite? Entity a secas?)
	// Debe poder cambiar su animación
	// Debe poder desplazarse por la pantalla
	// Debe cargar un sólo Spritesheet, el de "correr" 
	// Mejor Entity, así puedo "apilar capas" en el personaje (sombras, efectos, etc)
	
	
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
		
		
		AnimatedSprite zSprite = new AnimatedSprite(instance.CAMERA_WIDTH * 0.25f, instance.CAMERA_HEIGHT * 0.33f, zTextureRegion, instance.getVertexBufferObjectManager());
		// zSprite.setScale(8, 8);
		
		zSprite.animate(100);
		zSprite.animate (new long[] {100, 100, 100, 100, 100, 100}, 
				 		 new int [] { 3,   4,    5,   6,   7,   8}, true); //new int [] {0,   1,   2, 3, 4, 5, 6, 7, 8, 9, 10, 11  }, true);
		
		this.attachChild(zSprite);

		
	}
	
	
	
}
