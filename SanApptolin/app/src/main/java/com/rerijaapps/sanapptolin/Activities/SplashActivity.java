package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Storage.PreferencesManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jreci on 09/11/2016.
 */
@EActivity ( R.layout.activity_splash )
public class SplashActivity extends AppCompatActivity
{

	/**
	 * Delay para el Splash.
	 */
	private final int SPLASH_DELAY = 2500;

	/**
	 * Inicializa las vistas.
	 */
	@AfterViews
	public void setupViews()
	{
		doThreadDelay();
	}

	/**
	 * Realiza la operacion del delay.
	 */
	@Background
	public void doThreadDelay()
	{
		try
		{
			Thread.sleep( SPLASH_DELAY );
		}
		catch ( Exception ignored )
		{
		}
		if ( !PreferencesManager.getBoolean( Constants.PREFERENCE_NAME_SHOW_TUTORIAL, true ) )
		{
			goToMainActivity();
		}
		else
		{
			goToTutorialActivity();
		}
	}

	/**
	 * Navega a la activity principal.
	 */
	@UiThread
	public void goToMainActivity()
	{

	}

	/**
	 * Navega a la activity de presentacion.
	 */
	@UiThread
	public void goToTutorialActivity()
	{
		startActivity( new Intent( this , TutorialActivity.class ) );
		finish();
	}

}
