package com.rerijaapps.sanapptolin.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.rerijaapps.sanapptolin.Singleton.SanApptolinAudioPlayer;
import com.rerijaapps.sanapptolin.Utils.AudioNotificationUtils;

/**
 * Service para ejecutar una accion cuando la app es matada de recientes.
 * 
 * Created by jreci on 17/04/2017.
 */
public class OnClearFromRecentService extends Service
{

	/**
	 * {@inheritDoc}
	 *
	 * @param intent
	 * @return
	 */
	@Override
	public IBinder onBind( Intent intent )
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param intent
	 * @param flags
	 * @param startId
	 * @return
	 */
	@Override
	public int onStartCommand( Intent intent, int flags, int startId )
	{
		return START_NOT_STICKY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param rootIntent
	 */
	@Override
	public void onTaskRemoved( Intent rootIntent )
	{
		// Cancelamos el audio y la notificacion.
		if ( SanApptolinAudioPlayer.getInstance().isPlaying() )
		{
			SanApptolinAudioPlayer.getInstance().pause();
		}

		AudioNotificationUtils.cancelAudioNotification();

		stopSelf();
	}
}
