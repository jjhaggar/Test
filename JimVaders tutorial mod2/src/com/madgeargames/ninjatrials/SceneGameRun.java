package com.madgeargames.ninjatrials;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;


public class SceneGameRun  extends Scene implements IOnSceneTouchListener{
	
	// Valores que deberíamos recibir desde la anterior escena, de un objeto creado de la clase "Player"
	private String playerID = "adf76afd6afd7667a5d76f"; // id global
	private String playerName = "JJHaggar"; // id local
	private int playerNumber = 1;
	private String playerCharacter = "Ryoko";
	private int playerPoints = 0;
	private int playerDifficultyChosen = 1;
	
	// Fields
	private BaseActivity activity;
	
	// Fields "lógica de negocio" del juego
	private float timerStartedIn = 0; 
	private float timerActualInstant = 0;
	private float timeMax = 20; // Tiempo máximo para cpmpletar la prueba
	private float power = 0;
	private boolean canGainPower = false;
	
	// Pruebas
	private int pulsacionesInt = 0;
	private float ppsFloat = 0;
	private float ppsRecordFloat = 0;
	
	private Font mFont;
	private HUDGame mHud;
	
	public SceneGameRun() {
		
		activity = BaseActivity.getSharedInstance();
		this.mFont = activity.mFont;
		
		setBackground(new Background(0.7f, 0.9f, 0));
		
		
		
		
		
		
		
		
		
		

		
		
		/*
		final FPSCounter fpsCounter = new FPSCounter();
		activity.getEngine().registerUpdateHandler(fpsCounter);
		final float centerX = activity.CENTER_X;
		final Text elapsedText = new Text(centerX, 600, this.mFont, "Seconds elapsed:", "Seconds elapsed: XXXXXX".length(), activity.getVertexBufferObjectManager());
		final Text fpsText = new Text(centerX, 500, this.mFont, "FPS:", "FPS: XXXXX".length(), activity.getVertexBufferObjectManager());
		// final Text fpsText = new Text(centerX, 500, this.mFont, "FPS:", "FPS: XXXXX".length(), new TextOptions(HorizontalAlign.RIGHT), activity.getVertexBufferObjectManager());
		// parece que se pasa por el forro cómo se alinee el texto
		final Text pulsacionesText = new Text(centerX, 400, this.mFont, "Pulsaciones:", "Pulsaciones: XXXXXX".length(), activity.getVertexBufferObjectManager()); // cuidado con el tamaño mínimo/máximo
		// final Text pulsacionesText = new Text(centerX, 400, this.mFont, "Pulsaciones:", "Pulsaciones: XXXXXX".length(), new TextOptions(HorizontalAlign.LEFT), activity.getVertexBufferObjectManager()); // cuidado con el tamaño mínimo/máximo
		final Text ppsText = new Text(centerX, 300, this.mFont, "PPS:", "PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		final Text ppsRecordText = new Text(centerX, 200, this.mFont, "Record PPS:", "Record PPS: XX,XX".length(), activity.getVertexBufferObjectManager());
		this.attachChild(elapsedText);
		this.attachChild(fpsText);
		this.attachChild(pulsacionesText);
		this.attachChild(ppsText);
		this.attachChild(ppsRecordText );
		// Bucle de juego (game loop)
		this.registerUpdateHandler(new TimerHandler(1 / 10.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				timerActualInstant = activity.getEngine().getSecondsElapsedTotal();
				elapsedText.setText(String.format("Seconds elapsed: %.2f", timerActualInstant));
				fpsText.setText(String.format("FPS: %.2f", fpsCounter.getFPS()));
				pulsacionesText.setText( "Pulsaciones: " + pulsacionesInt );
				mHud.updateTimer(timerActualInstant);
				// ppsInt = pulsacionesInt / ( ((int)timerActualInstant > 0 ) ? (int)timerActualInstant : 1); //(a > b) ? a : b;
				ppsFloat = (float)pulsacionesInt / ( (timerActualInstant > 0 ) ? timerActualInstant : 1f); //(a > b) ? a : b;
				// ppsText.setText( "PPS: " + ppsFloat );
				ppsText.setText( String.format("PPS: %.2f", ppsFloat) );
				if (ppsFloat > ppsRecordFloat) ppsRecordFloat = ppsFloat;
				ppsRecordText.setText( String.format("Record PPS: %.2f", ppsRecordFloat) );
			}
		}));
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		runPreparation(); // <- Esto debería ser prácticamente lo único que hubiera en el constructor 

		
		this.setOnSceneTouchListener(this);
		
		
		
		
	}// Constructor
	
	
	private void runPreparation(){
		
		// Crea el HUD
		mHud = new HUDGame(HUDGame.RUNNING_STAGE);
		activity.mCamera.setHUD(mHud);
		
		power = 0; 
		timerStartedIn = activity.getEngine().getSecondsElapsedTotal();  
		mHud.updateTimer(timeMax);
		
		this.registerUpdateHandler(new TimerHandler(1 / 1.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				timerActualInstant = 5 + timerStartedIn - activity.getEngine().getSecondsElapsedTotal();
				
				if ( (int)timerActualInstant < 4){
					
					if ((int)timerActualInstant == 0){
						mHud.showMessage( activity.getString(R.string.run_start) ); //mHud.showMessage("Go!");
						runStart();
					}
					else{
						mHud.showMessage(""+(int)timerActualInstant);
						canGainPower = true;
					}
				}
				else {
					mHud.showMessage( activity.getString(R.string.run_ready) ); //mHud.showMessage("Ready?");
				}
			}
		}));
	} 
	
	private void runStart(){
		
		// Elimina los manejadores de actualización anteriores
		this.clearUpdateHandlers();
		
		// Reinicia la cuenta del temporizador
		timerStartedIn = activity.getEngine().getSecondsElapsedTotal();  
		timerActualInstant = timeMax;
		
		// Bucle de juego (game loop)
		this.registerUpdateHandler(new TimerHandler(1 / 10.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				timerActualInstant = timeMax + timerStartedIn - activity.getEngine().getSecondsElapsedTotal();
				
				mHud.updateTimer(timerActualInstant);
				
				if (timerActualInstant <= 0f){
					mHud.showMessage( activity.getString(R.string.run_finish) ); // mHud.showMessage("Game Over"); 
					runLostByTime();
				}
			}
		}));
		
		
	} 
	private void runLostByTime(){
		
		this.clearUpdateHandlers(); // Limpia los "UpdateHandlers" de la escena actual (this)
		canGainPower = false;
		
		runFinish();
	} 
	
	private void runFinish(){
		
		this.clearUpdateHandlers(); // Limpia los "UpdateHandlers" de la escena actual (this)
		canGainPower = false;
		
	} 
	
	
	
	
	public void resetScene(){
		mHud.detachChildren(); // activity.mCamera.getHUD().detachChildren();
		mHud.detachSelf(); // activity.mCamera.getHUD().detachSelf();
	}
	
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
			
			
			// Log.v("Prueba pulsar rápido", "pulsada pantalla " + pulsacionesInt + " veces.");
			
			// Log.v("Prueba pulsar rápido",  String.format("String.format: %.2f", speed) );
			
			gainPower();
			
			
			
			return true;
		}
		
		return false;
	}
	
	private void gainPower(){ // min power = 0 / max = 100 ? 
		
		if (canGainPower){ 
			pulsacionesInt++;
			mHud.updatePowerBarVertical( (float)pulsacionesInt*5f/100f ); // prueba 
			// mHud.showMessage("Hola");
			
			
			
			
			
			
			
			
			
			
		}
		
		
	}

	

}
