package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.EActivity;

import com.rerijaapps.sanapptolin.Services.AudioPlayerFocusService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity con el reproductor de audio incorporado.
 *
 * Created by jreci on 15/03/2017.
 */
@EActivity ( )
public class AudioActivity extends AppCompatActivity
{

	/**
	 * Servicio del player.
	 */
	public static AudioPlayerFocusService PLAY_SERVICE;

	/***
	 * Conexion del servicio del foco del audio.
	 */
	public static ServiceConnection PLAY_SERVICE_CONNECTION = new ServiceConnection()
	{
		public void onServiceConnected( ComponentName className, IBinder service )
		{
			PLAY_SERVICE = ( ( AudioPlayerFocusService.LocalBinder ) service ).getService();
		}

		public void onServiceDisconnected( ComponentName className )
		{
			PLAY_SERVICE = null;
		}
	};

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

		// Creamos el service del foco del audio solo si es nulo.
		if ( null == PLAY_SERVICE )
		{
			this.startService( new Intent( this , AudioPlayerFocusService.class ) );
			this.bindService( new Intent( this , AudioPlayerFocusService.class ), PLAY_SERVICE_CONNECTION, Context.BIND_AUTO_CREATE );
		}
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
