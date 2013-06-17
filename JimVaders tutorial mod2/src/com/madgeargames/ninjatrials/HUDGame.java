/*
 * Esta es la clase que se encargará de representar los diferentes marcadores (HUD) durante las pruebas del juego.
 * 
 * Dependiendo del tipo de fase (carrera, corte, saltos, etc) se cargarán unos elementos del HUD u otros.
 * 
 * Los assets del HUD deberían precargarse (con el AssetManager) en la pantalla previa (SceneMap? SceneLoading?)
 * 
 * */



package com.madgeargames.ninjatrials;

import java.text.DecimalFormat;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.util.Log;

public class HUDGame extends HUD{
	
	
	// Constants          
	public static final int RUNNING_STAGE = 0;
	public static final int CUTTING_STAGE = 1;
	public static final int JUMPING_STAGE = 2;
	public static final int THROWING_STAGE= 3;
	
	
	private BaseActivity activity;
	
	// Timer fields
	private Font mFontTimer; //private Font mFontSecs, mFontCent;
	private Text mTextTimer; // private Text mTextSecs, mTextCent;
	private int mIntTimerMarginX = 40;
	private int mIntTimerMarginY = 40;
	
	
	// PowerBarVertical fields
	private Sprite mSpriteHUDPowerBar;
	private int posPowBarX = 120;
	private int posPowBarY = 200;
	private Rectangle mRectangle;
	
	// Message fields
	private Font mFontMessage;
	
	
	// Constructor
	public HUDGame(int typeOfGameScene){ //Font mFont){ // Log.v("Prueba pulsar rápido", "dentro HUD");
		
		activity = BaseActivity.getSharedInstance();
		
		// Inicio preparativos para mostrar mensajes
		FontFactory.setAssetBasePath("fonts/");
		final BitmapTextureAtlas mTextureFontMessage = new BitmapTextureAtlas(activity.getTextureManager(),1024, 1024, TextureOptions.BILINEAR);
		mFontMessage = FontFactory.createStrokeFromAsset(activity.getFontManager(), mTextureFontMessage , activity.getAssets(), 
				"dom_parquim.ttf", (float)180, true, android.graphics.Color.YELLOW, 7, android.graphics.Color.RED ); // monofonto.ttf
		mFontMessage.load();
		// Fin preparativos para mostrar mensajes
		
		switch (typeOfGameScene) {
		case RUNNING_STAGE:
			createTimer();
			createPowerBarVertical();
			//createPlayerFace();
			break;
		case THROWING_STAGE:
			createTimer();
			//createPlayerFace();
			//createShurikensAvailable();
		default:
			break;
		}
		
		
		
		// final Sprite mSpriteHUDPrecissionBar = new Sprite(350, 130, activity.mTexRegHUDPrecissionBar, activity.getVertexBufferObjectManager());
		// final Sprite mSpriteHUDHeads = new Sprite(400, 850, activity.mTexRegHUDHeads, activity.getVertexBufferObjectManager());
		// attachChild(mSpriteHUDPrecissionBar);
		// attachChild(mSpriteHUDHeads);
		
	}
	
	
	public void showMessage(String message){ // Displaytime default = 1 second
		showMessage(message, 0.25f, 1.0f, 0.25f); 
	}
	
	public void showMessage(String message, float msgEnterTime, float msgDisplayTime, float msgExitTime){
	
		final Text mTextMessage = new Text( 0, 0, mFontMessage, message, message.length(), activity.getVertexBufferObjectManager());
		mTextMessage.setPosition(activity.CENTER_X, activity.CENTER_Y);
		
		this.attachChild(mTextMessage);
		
		mTextMessage.setAlpha(0);
		mTextMessage.setScale(0.9f);
		
		
		SequenceEntityModifier mSeqEntMod= new SequenceEntityModifier(
				new ParallelEntityModifier(// entrada logo
						new AlphaModifier(msgEnterTime, 0, 1), 
						new ScaleModifier(msgEnterTime, 0.95f, 1)
				),
				new DelayModifier(msgDisplayTime),// logo quieto
				new ParallelEntityModifier(// entrada logo
						new AlphaModifier(msgExitTime, 1, 0), 
						new ScaleModifier(msgExitTime, 1, 0.95f)
				)
			);
		mTextMessage.registerEntityModifier(mSeqEntMod);
	
	}


	// Crea la barra de poder vertical. Está compuesta de un Sprite con un bitmap de fondo y un rectángulo negro superpuesto que simula 
	// que la barra de energía crece o decrece (en realidad el rectángulo está tapando parte de dicha barra) 
	public void createPowerBarVertical(){
		// Sprite
		mSpriteHUDPowerBar = new Sprite(posPowBarX, posPowBarY, activity.mTexRegHUDPowerBar, activity.getVertexBufferObjectManager());
		attachChild(mSpriteHUDPowerBar);
		// Rectangle
		int rectHeight = 200; int rectWidth = 80;
		mRectangle = new Rectangle(posPowBarX, posPowBarY, rectWidth, rectHeight, activity.getVertexBufferObjectManager());
		mRectangle.setColor(0f, 0f, 0f);
		mRectangle.setScaleCenterY( 1 ); // se refiere al alto de la entidad en tanto por uno, no en pixels ^^U
		attachChild(mRectangle);
	}
	// Modifica la barra de energía. A valor 0 está vacía (el rectángulo la tapa completamente), a valor 1 está llena (el rectángulo no tapa nada)
	public void updatePowerBarVertical(float actualPower){ 
		// Comprobación valores permitidos. 0 min / 1 max
		if (0f<=actualPower && actualPower<=1)
			mRectangle.setScaleY( 1f - actualPower );
		else
			Log.v("HUDGame>updatePowerBarVertical()", "El valor debe estar entre 0 y 1, y es igual a "+actualPower);
	}
	
	// Crea el Temporizador
	public void createTimer(){
		
		FontFactory.setAssetBasePath("fonts/");
		final BitmapTextureAtlas mTextureFontTimer = new BitmapTextureAtlas(activity.getTextureManager(),1024, 1024, TextureOptions.BILINEAR);
		this.mFontTimer = FontFactory.createStrokeFromAsset(activity.getFontManager(), mTextureFontTimer , activity.getAssets(), 
				"monofonto.ttf", (float)180, true, android.graphics.Color.YELLOW, 7, android.graphics.Color.RED ); // dom_parquim.ttf
		this.mFontTimer.load();
		this.mTextTimer = new Text( 0, 0, 
				mFontTimer, "00.00", "00.00".length(), activity.getVertexBufferObjectManager());
		// mIntTimerMarginX = mIntTimerMarginY = 40;
		this.mTextTimer.setPosition(activity.CAMERA_WIDTH - mTextTimer.getWidth() /2 -mIntTimerMarginX, 
									activity.CAMERA_HEIGHT- mTextTimer.getHeight()/2 -mIntTimerMarginY );
		this.attachChild(mTextTimer);
	}
	
	// Modifica el valor del Temporizador
	public void updateTimer(float actualTime){
		
		if (actualTime >= 100) { // Comprueba que no se intente mostrar un número más largo de 5 caracteres "00.00"
			Log.w(this.getClass().toString(), "Sólo se puede marcar hasta 99,99 de tiempo");
			this.mTextTimer.setText("ERROR");
			return;
		}
		DecimalFormat mFormatter = new DecimalFormat("00.00");
		String output = mFormatter.format(actualTime);
		this.mTextTimer.setText(output);
		// this.mTextTimer.setText(String.format("%02.2f", actualTime) ); 
	}
	
	public void resetTimer(){
		// this.mTextTimer.detachSelf();
	}
	
	
	
	
	public void resetHUD(){
		
		// resetTimer();
		
	}
	
	
}
