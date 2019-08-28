package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.EActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity con el reproductor de audio incorporado.
 *
 * Created by jreci on 15/03/2017.
 */
@EActivity ( )
public class BasicActivity extends AppCompatActivity
{

	/**
	 * Indica si realizar la accion del pause.
	 */
	public static boolean DO_ON_PAUSE = true;

	/**
	 * Indica si realizar la accion del resume.
	 */
	public static boolean DO_ON_RESUME = false;

	/**
	 * {@inheritDoc}
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPause()
	{
		if ( DO_ON_PAUSE )
		{

		}
		DO_ON_PAUSE = true;
		super.onPause();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume()
	{
		if ( DO_ON_RESUME )
		{

		}
		DO_ON_RESUME = true;
		super.onResume();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onBackPressed()
	{
		DO_ON_PAUSE = false;
		DO_ON_RESUME = false;
		if ( this instanceof MainActivity )
		{
			moveTaskToBack( true );
		}
		else
		{
			super.onBackPressed();
		}
	}
}
