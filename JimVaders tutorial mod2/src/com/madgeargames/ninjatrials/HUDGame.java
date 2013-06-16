package com.madgeargames.ninjatrials;

import java.text.DecimalFormat;

import org.andengine.engine.camera.hud.HUD;
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
	private int mIntPowBarVer; 
	private Sprite mSpriteHUD;
	
	
	
	// Constructor
	public HUDGame(int typeOfGameScene){ //Font mFont){ // Log.v("Prueba pulsar rápido", "dentro HUD");
		
		activity = BaseActivity.getSharedInstance();
		
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
		
		
	}
	
	
	
	/*
	

	// ===========================================================
	// Constants          
	// ===========================================================
	private static final float FRAME_LINE_WIDTH = 5f;
	// ===========================================================          
	// Fields         
	// =========================================================== 
	private final Line[] mFrameLines = new Line[4];
	private final Rectangle mBackgroundRectangle;
	private final Rectangle mProgressRectangle;
	
	private final float mPixelsPerPercentRatio;
	// ===========================================================          
	// Constructors          
	// =========================================================== 
	public SceneGameRunHUD (final Camera pCamera, final float pX, final float pY, final float pWidth, final float pHeight) {
		super();
		super.setCamera(pCamera);
		
		this.mBackgroundRectangle = new Rectangle(pX, pY, pWidth, pHeight);
		
		this.mFrameLines[0] = new Line(pX, pY, pX+pWidth, pY, FRAME_LINE_WIDTH); //Top line.
		this.mFrameLines[1] = new Line(pX + pWidth, pY, pX + pWidth, pY + pHeight, FRAME_LINE_WIDTH); //Right line.
		this.mFrameLines[2] = new Line(pX + pWidth, pY + pHeight, pX, pY + pHeight, FRAME_LINE_WIDTH); //Bottom line.
		this.mFrameLines[3] = new Line(pX, pY + pHeight, pX, pY, FRAME_LINE_WIDTH); //Left line.
		
		this.mProgressRectangle = new Rectangle(pX, pY, pWidth, pHeight);
		
		super.attachChild(this.mBackgroundRectangle); //This one is drawn first.
		super.attachChild(this.mProgressRectangle); //The progress is drawn afterwards.
		for(int i = 0; i < this.mFrameLines.length; i++)
			super.attachChild(this.mFrameLines[i]); //Lines are drawn last, so they'll override everything.
		
		this.mPixelsPerPercentRatio = pWidth / 100;
	}
	// ===========================================================          
	// Getter & Setter          
	// =========================================================== 
	public void setBackColor(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		this.mBackgroundRectangle.setColor(pRed, pGreen, pBlue, pAlpha);
	}
	public void setFrameColor(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		for(int i = 0; i < this.mFrameLines.length; i++)
			this.mFrameLines[i].setColor(pRed, pGreen, pBlue, pAlpha);
	}
	public void setProgressColor(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		this.mProgressRectangle.setColor(pRed, pGreen, pBlue, pAlpha);
	}
	
	// Set the current progress of this progress bar.
	// @param pProgress is <b> BETWEEN </b> 0 - 100.
	 
	public void setProgress(final float pProgress) {
		if(pProgress < 0)
			this.mProgressRectangle.setWidth(0); //This is an internal check for my specific game, you can remove it.
		this.mProgressRectangle.setWidth(this.mPixelsPerPercentRatio * pProgress);
	}
	// ===========================================================          
	// Methods for/from SuperClass/Interfaces          
	// ===========================================================  
	
	// ===========================================================          
	// Methods          
	// ===========================================================  
	
	// ===========================================================          
	// Inner and Anonymous Classes          
	// ===========================================================  
	
	*/
	
	
	public void createPowerBarVertical(){
		/*
		this.mSpriteHUD = activity.mSpriteHUD;
		this.mSpriteHUD.setPosition(100, 100);
		this.attachChild(this.mSpriteHUD);
		*/
		
	}
	
	public void updatePowerBarVertical(int actualPower){
		
	}
	
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
	
	public void updateTimer(float actualTime){
		
		if (actualTime >= 100) { // Cuidamos que no se intente mostrar un número más largo de 5 caracteres "00.00"
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
