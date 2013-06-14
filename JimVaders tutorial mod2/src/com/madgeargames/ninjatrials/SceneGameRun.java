package com.madgeargames.ninjatrials;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;

public class SceneGameRun  extends Scene implements IOnSceneTouchListener{
	
	private BaseActivity activity;
	
	private Font mFont;
	private float timerFloat;
	private int pulsacionesInt = 0;
	private float ppsFloat = 0;
	private float ppsRecordFloat = 0;
	
	public SceneGameRun() {
		
		activity = BaseActivity.getSharedInstance();
		this.mFont = activity.mFont;
		
		setBackground(new Background(0.7f, 0.9f, 0));
		
		final FPSCounter fpsCounter = new FPSCounter();
		activity.getEngine().registerUpdateHandler(fpsCounter);
		final float centerX = activity.CENTER_X;
		
		final Text elapsedText = new Text(centerX, 600, this.mFont, "Seconds elapsed:", "Seconds elapsed: XXXXXX".length(), activity.getVertexBufferObjectManager());
		final Text fpsText = new Text(centerX, 500, this.mFont, "FPS:", "FPS: XXXXX".length(), activity.getVertexBufferObjectManager());
		
		final Text pulsacionesText = new Text(centerX, 400, this.mFont, "Pulsaciones:", "Pulsaciones: XXXXXX".length(), activity.getVertexBufferObjectManager()); // cuidado con el tamaño mínimo/máximo
		
		final Text ppsText = new Text(centerX, 300, this.mFont, "PPS:", "PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		final Text ppsRecordText = new Text(centerX, 200, this.mFont, "Record PPS:", "Record PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		
		
		this.attachChild(elapsedText);
		this.attachChild(fpsText);
		this.attachChild(pulsacionesText);
		this.attachChild(ppsText);
		this.attachChild(ppsRecordText );
		
		this.registerUpdateHandler(new TimerHandler(1 / 10.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				timerFloat = activity.getEngine().getSecondsElapsedTotal();
				elapsedText.setText(String.format("Seconds elapsed: %.2f", timerFloat));
				
				fpsText.setText(String.format("FPS: %.2f", fpsCounter.getFPS()));
				
				pulsacionesText.setText( "Pulsaciones: " + pulsacionesInt );
				
				
				// ppsInt = pulsacionesInt / ( ((int)timerFloat > 0 ) ? (int)timerFloat : 1); //(a > b) ? a : b;
				ppsFloat = (float)pulsacionesInt / ( (timerFloat > 0 ) ? timerFloat : 1f); //(a > b) ? a : b;
				// ppsText.setText( "PPS: " + ppsFloat );
				ppsText.setText( String.format("PPS: %.2f", ppsFloat) );
				
				if (ppsFloat > ppsRecordFloat) ppsRecordFloat = ppsFloat;
					
				ppsRecordText.setText( String.format("Record PPS: %.2f", ppsRecordFloat) );
				

			}
		}));
		
		this.setOnSceneTouchListener(this);
		
		
	}
	
	
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
			
			pulsacionesInt++;
			// Log.v("Prueba pulsar rápido", "pulsada pantalla " + pulsacionesInt + " veces.");
			
			// Log.v("Prueba pulsar rápido",  String.format("String.format: %.2f", speed) );
			
			return true;
		}
		
		
		return false;
	}

}
