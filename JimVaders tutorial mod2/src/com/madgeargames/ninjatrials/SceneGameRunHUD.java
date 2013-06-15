package com.madgeargames.ninjatrials;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

public class SceneGameRunHUD extends HUD{
	
	private BaseActivity activity;
	
	private Font mFont;
	private final Text ppsRecordText; 
	
	public SceneGameRunHUD(){
		
		// Log.v("Prueba pulsar rápido", "dentro HUD");
		
		activity = BaseActivity.getSharedInstance();
		
		mFont = activity.mFontStroke; // activity.mFontCrono;
		
		// Ahora a crear el temporizador aquí. Después lo acicalaremos con algo de herencia
		final VertexBufferObjectManager vertexBufferObjectManager = activity.getVertexBufferObjectManager();
		final Text centerText = new Text(activity.CENTER_X, 400, this.mFont, "Hello AndEngine!\nYou can even have multilined text!", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		final Text leftText = new Text(activity.CENTER_X, 250, this.mFont, "Also left aligned!\nLorem ipsum dolor sit amat...", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
		final Text rightText = new Text(activity.CENTER_X, 100, this.mFont, "And right aligned!\nLorem ipsum dolor sit etc...", 
				new TextOptions(HorizontalAlign.RIGHT), vertexBufferObjectManager);

		
		
		
		
		
		ppsRecordText = new Text(activity.CENTER_X, 100, this.mFont, "Record PPS:", 
				"Record PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		
		this.attachChild(ppsRecordText);
		
		ppsRecordText.setText( "Funcionando! :D" );
		
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

}
