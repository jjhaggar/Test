package com.madgeargames.ninjatrials;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.EmptyTexture;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

public class HUDTimer2 extends HUD{
	
	
	private BaseActivity activity;
	
	private Font mFont; // a borrar
	private Text ppsRecordText;// a borarr
	
	private Font mFontSecs, mFontCent;
	private Text mTextSecs, mTextCent;
	
	 
	
	
	// Constructor
	public HUDTimer2(){ //Font mFont){ // Log.v("Prueba pulsar rápido", "dentro HUD");
		
		
		activity = BaseActivity.getSharedInstance();
		
		// Crear fuente
		
		FontFactory.setAssetBasePath("fonts/");
		Font mFontCrono = FontFactory.createFromAsset(
				activity.getFontManager(), activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR, 
				activity.getAssets(), "dom_parquim.ttf", 48, true, android.graphics.Color.WHITE
				);
		
		
		// Font mPlokFont = FontFactory.createStrokeFromAsset(this.mFontTexture, this, "Plok.ttf", 40, true, Color.WHITE, 2, Color.BLACK);
		
		//Font mPlokFont = FontFactory.createStroke(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, 
		// 		pTypeface, pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor)
		
		
		final BitmapTextureAtlas orangeFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),512, 512, TextureOptions.BILINEAR);
		final AssetManager AssetManagerSB = activity.getAssets();
		
		StrokeFont orangeFontSB = FontFactory.createStrokeFromAsset(activity.getFontManager(), orangeFontTexture , AssetManagerSB, 
				"dom_parquim.ttf", (float)35, true, android.graphics.Color.RED, 10, android.graphics.Color.BLUE );
		
		orangeFontSB.load();
		
		final Text patataText = new Text(activity.CENTER_X, 800, orangeFontSB, "..............", 
				"..............".length(), activity.getVertexBufferObjectManager());
		
		this.attachChild(patataText);
		
		
		/*
		StrokeFont patata = FontFactory.createStrokeFromAsset(activity.getFontManager(), final ITexture pTexture, final AssetManager pAssetManager, 
				final String pAssetPath, final float pSize, final boolean pAntiAlias, final int pColor, final float pStrokeWidth, final int pStrokeColor); 
		*/
		
		/*
		StrokeFont patata = FontFactory.createStrokeFromAsset(final FontManager pFontManager, final ITexture pTexture, final AssetManager pAssetManager, 
				final String pAssetPath, final float pSize, final boolean pAntiAlias, final int pColor, final float pStrokeWidth, final int pStrokeColor); 
		
		*/
		
		
		/*
		Font mPlokFont = FontFactory.createStroke(activity.getFontManager(), activity.getTextureManager(), 512, 512, 
				"dom_parquim.ttf", 48, TextureOptions.BILINEAR, android.graphics.Color.BLACK, 2, android.graphics.Color.WHITE);
		
		
		
		public static StrokeFont createStroke
			(final FontManager pFontManager, final ITexture pTexture, final float pSize, final boolean pAntiAlias, 
			final int pColor, final float pStrokeWidth, final int pStrokeColor) {
		return FontFactory.createStroke(pFontManager, pTexture, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor);
		}
		
		*/
				/*
				createFromAsset(
				this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, 
				this.getAssets(), "dom_parquim.ttf", 48, true, android.graphics.Color.WHITE
				);
		*/
		
		/*
		final int FONT_SIZE = 96; //48;
		final ITexture strokeFontTexture = new EmptyTexture(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		// final ITexture strokeFontTexture = new EmptyTexture(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		StrokeFont mFontStroke  = new StrokeFont(activity.getFontManager(), strokeFontTexture, 
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.YELLOW, 2, Color.RED);
		activity.mFontStroke.load();
		
		
		ppsRecordText = new Text(activity.CENTER_X, 100, activity.mFontStroke, "Record PPS:", 
				"Record PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		
		this.attachChild(ppsRecordText);
		*/
		
		/*
		
		// this.mFont = mFont;// = activity.mFontCrono; // .mFontStroke;//.mFontCrono;
		
		// Ahora a crear el temporizador aquí. Después lo acicalaremos con algo de herencia
		final VertexBufferObjectManager vertexBufferObjectManager = activity.getVertexBufferObjectManager();
		final Text centerText = new Text(activity.CENTER_X, 400, this.mFont, "Hello AndEngine!\nYou can even have multilined text!", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		final Text leftText = new Text(activity.CENTER_X, 250, this.mFont, "Also left aligned!\nLorem ipsum dolor sit amat...", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
		final Text rightText = new Text(activity.CENTER_X, 100, this.mFont, "And right aligned!\nLorem ipsum dolor sit etc...", new TextOptions(HorizontalAlign.RIGHT), vertexBufferObjectManager);
		
		
		
		
		
		
		ppsRecordText = new Text(activity.CENTER_X, 100, this.mFont, "Record PPS:", 
				"Record PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		
		this.attachChild(ppsRecordText);
		
		ppsRecordText.setText( "Funciona!!! :D" );
		
		
		
		*/
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
