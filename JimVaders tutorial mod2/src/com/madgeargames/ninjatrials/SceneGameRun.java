package com.madgeargames.ninjatrials;

import java.text.DecimalFormat;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;

import android.util.Log;


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
	// TODO: Hay que cambiar casi todos los float a int (con dos ceros extras para actuar como decimales)
	private float timerStartedIn = 0; 
	private float timerActualInstant = 0;
	private float timerPreviousInstant = 0;
	private float timerSecondsGameLoopLogic = 1f/10f; // Se actualiza 10 veces por segundo (pocas en principio)
	private float timerSecondsGameLoopAnimation = 1f/30f; // Yo diría que debería ser al revés, pero...
	private float timeMax = 20; // Segundos. Tiempo máximo para completar la prueba
	private float power = 0;
	private boolean canGainPower = false;
	private float substIncrMultiplier = 4; // Con esto se aumente la velocidad, tanto al incrementar la barra como al decrementarla 
	private float subtractNumber = 1 * substIncrMultiplier; // esto se resta cada décima de segundo a "power"
	private float increment00To10 = 10 * substIncrMultiplier;
	private float increment11To20 = 9 * substIncrMultiplier;
	private float increment21To30 = 8 * substIncrMultiplier;
	private float increment31To40 = 7 * substIncrMultiplier;
	private float increment41To50 = 6 * substIncrMultiplier;
	private float increment51To60 = 5 * substIncrMultiplier;
	private float increment61To70 = 4 * substIncrMultiplier;
	private float increment71To80 = 3 * substIncrMultiplier;
	private float increment81To90 = 2 * substIncrMultiplier;
	private float increment91To100= 1 * substIncrMultiplier;
	private float distanceReached = 0;
	private float distanceTotalStage = 0;
	private boolean distanceCompleted = false;
	private float highPowerValue = 90.0f; // Tanto por ciento de la barra de power necesario para hacer "HighPower-Combo"
	private float highPowerSecondsActual = 0;
	private float highPowerSecondsTotal = 0;
	private float highPowerPreviousInstant = 0;
	private float highPowerSecondsMax = 0; // No sé si premiar esto en la puntuación final :S
	private float maxPowerValue = 110.0f; // Poder máximo REAL alcanzable. NO es 100. Es para dar al jugador un margen y que pueda mantenerse al 100% 
	
	// Pruebas
	private int pulsacionesInt = 0;
	private float ppsFloat = 0;
	private float ppsRecordFloat = 0;
	
	private DecimalFormat mFormatter = new DecimalFormat("00.00");
	
	
	
	private Font mFont;
	private HUDGame mHud;
	
	public SceneGameRun() {
		
		activity = BaseActivity.getSharedInstance();
		this.mFont = activity.mFont;
		
		setBackground(new Background(0.7f, 0.9f, 0));
		
		
		
		
		
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
		

		
		// Controla la cuenta atrás y el inicio de la carrera con runStart();
		this.registerUpdateHandler(new TimerHandler(1 / 1.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				
				timerActualInstant = 5 + timerStartedIn - activity.getEngine().getSecondsElapsedTotal();
				
				if ( (int)timerActualInstant >= 4)
					mHud.showMessage( activity.getString(R.string.run_ready) ); //mHud.showMessage("Ready?");
				
				else {
					if ((int)timerActualInstant > 0){
						mHud.showMessage(""+(int)timerActualInstant);
						canGainPower = true;
					}
					else{
						mHud.showMessage( activity.getString(R.string.run_start) ); //mHud.showMessage("Go!");
						runStart();
					}
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
		
		// Esto es similar a los Bucles de juego (game loops), a diferencia de lo habitual, se pueden "crear varios" (¿en realidad es sólo uno?)
		this.registerUpdateHandler(new TimerHandler(timerSecondsGameLoopLogic, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				
				timerActualInstant = timeMax + timerStartedIn - activity.getEngine().getSecondsElapsedTotal();
				mHud.updateTimer(timerActualInstant);
				
				if(power>0){
					power -= subtractNumber;
					mHud.updatePowerBarVertical(power/100f);
				}
				
				// Aún por implementar. Probablemente los meta en otro "gameloop" que se actualice más rápido
				animateBackground();
				animateCharacters();
				
				
				if (power >= highPowerValue){
					incrementHighPowerSeconds(); // BRUTAL SPEED STREAK!!
				}
				
				incrementDistanceReached(); // Se actualiza justo
				
				if (timerActualInstant <= 0f && !distanceCompleted){ // si se acaba el tiempo, se acabó todo 
					// mHud.showMessage( activity.getString(R.string.run_finish) ); // mHud.showMessage("Game Over");
					
					// for (int i= 0; i<(int)timeMax; i++) highPowerSecondsMax += 0.10f;
					
					// mHud.showMessage(timeMax*0.10f + " = " + distanceReached + " = " + highPowerSecondsMax, 1,5,1);
					runLostByTime();
				}
				
				timerPreviousInstant = timerActualInstant;
			}
		}));
		
	} 
	
	private void animateBackground(){
		
	}
	private void animateCharacters(){
		
	}
	
	private void incrementDistanceReached(){
		distanceReached += power; // ya veremos si hay que multiplicar/dividir esto por algo para que de una medida razonable
		// distanceReached += 0.10f; // prueba para ver el combo de tiempo 
		// if (distanceReached >= distanceStage) runCompleted();
	}
	
	private void incrementHighPowerSeconds(){
		
		highPowerSecondsTotal += timerSecondsGameLoopLogic;
		
		if (highPowerPreviousInstant == timerPreviousInstant){ // El instante comprobado en el GameLoop lar última vez también estába en "highPower"
			// if (highPowerSecondsActual == 0) highPowerSecondsActual += timerSecondsGameLoopLogic; // si es la 1ª vez q entramos en "combo" sumamos el tiempo del instante anterior
			highPowerSecondsActual += timerSecondsGameLoopLogic;
			if (highPowerSecondsActual > highPowerSecondsMax){
				highPowerSecondsMax = highPowerSecondsActual;
			}
			mHud.showMessage(mFormatter.format(highPowerSecondsActual) + "!");
			Log.v("mormor", mFormatter.format(highPowerSecondsActual) + "!");
		}
		
		
		/*
		else{
			mHud.showMessage(timerSecondsGameLoopLogic + "!");
			Log.v("mormor", timerSecondsGameLoopLogic + "!");
		}
		*/
		
		
		
		highPowerPreviousInstant = timerActualInstant; // Actualizamos esto al final del método
		
		/*
		private float highPowerValue = 90.0f; // Tanto por ciento de la barra de power
		private float highPowerSecondsActual = 0;
		private float highPowerSecondsTotal = 0;
		private float highPowerPreviousInstant = 0;
		private float highPowerSecondsMax = 0; // No sé si premiar esto en la puntuación final :S
		private float maxPowerValue = 110.0f; // Poder máximo REAL alcanzable. NO es 100. Es para dar al jugador un margen y que pueda mantenerse al 100%
		*/
	}
	
	
	
	
	
	
	

	
	private void runLostByTime(){ // Se acaba el tiempo antes de copmpletar la carrera
		
		// Comprobaciones ¿?
		
		runShowResults();
	} 
	
	private void runCompleted(){ // Carrera completada con éxito
		distanceCompleted = true;
		
		
	} 
	
	
	private void runShowResults(){ 
		
		this.clearUpdateHandlers(); // Limpia los "UpdateHandlers" de la escena actual (this)
		canGainPower = false;

		
		// mHud.showMessage(mFormatter.format(highPowerSecondsMax) , 1,20,1); //highPowerSecondsTotal highPowerSecondsMax
		
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
		
		// Me preocupa que al mismo tiempo que actualizo la variable "power" al pulsar el botón
		// la actualice el bucle de juego restando de ella :S
		// ¿Habrá que usar Synchronized?
		
		// Si hay algún fallo (que lo habrá) mirar:
		// http://epere4.blogspot.com.es/2008/04/cmo-funciona-synchronized-en-java.html
		// http://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html
		
		if (canGainPower){ 
			pulsacionesInt++;

			// mHud.updatePowerBarVertical( (float)pulsacionesInt*5f/100f ); // prueba 

			
			if (power<=10){
				power += increment00To10;
			}
			else if (power<=20){
				power += increment11To20;
			}
			else if (power<=30){
				power += increment21To30;
			}
			else if (power<=40){
				power += increment31To40;
			}
			else if (power<=50){
				power += increment41To50;
			}
			else if (power<=60){
				power += increment51To60;
			}
			else if (power<=70){
				power += increment61To70;
			}
			else if (power<=80){
				power += increment71To80;
			}
			else if (power<=90){
				power += increment81To90;
			}
			else if (power>90){
				power += increment91To100;
			}
			
			if (power > maxPowerValue) power = maxPowerValue; // Limitamos el valor de "power" 
			
			mHud.updatePowerBarVertical( power/100f ); // necesario para que se actualice duratne la cuenta atrás
			
			// mHud.showMessage(""+power);
			// Log.v("Push",""+power);
			
			
		}
		
		
	}// gainPower
	
	private void maxPower(){
		
		
		
	}

	

}
