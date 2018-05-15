package com.rerijaapps.sanapptolin.Services;

import com.rerijaapps.sanapptolin.Singleton.SanApptolinAudioPlayer;
import com.rerijaapps.sanapptolin.Utils.AudioNotificationUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;

/**
 * Servicio para recibir en tiempo real el foco del audio.
 *
 * Created by jrecio on 25/8/17.
 */
public class AudioPlayerFocusService extends Service implements AudioManager.OnAudioFocusChangeListener
{

	/**
	 * Acciones del Service.
	 */
	public static final String PLAY_ACTION = "com.rerijaapps.sanapptolin.play_service.listenerPlay";
	public static final String PAUSE_ACTION = "com.rerijaapps.sanapptolin.play_service.listenerPause";

	/**
	 * Binder.
	 */
	private final IBinder mBinder = new LocalBinder();

	/**
	 * Binder local.
	 */
	public class LocalBinder extends Binder
	{
		public AudioPlayerFocusService getService()
		{
			return AudioPlayerFocusService.this;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param intent
	 * @param flags
	 * @param startId
	 * @return
	 */
	public int onStartCommand( Intent intent, int flags, int startId )
	{
		if ( intent != null && intent.getAction() != null )
		{
			if ( intent.getAction().equals( AudioPlayerFocusService.PLAY_ACTION ) )
			{
				play();
			}
			else if ( intent.getAction().equals( AudioPlayerFocusService.PAUSE_ACTION ) )
			{
				pause();
			}
		}
		return Service.START_STICKY;
	}

	/**
	 * Hace una peticion para coger el foco del audio del sistema.
	 *
	 * @return - Entero que refleja el estado de la peticion.
	 */
	public int requestAudioFocus()
	{
		AudioManager am = ( AudioManager ) this.getSystemService( Context.AUDIO_SERVICE );
		return am.requestAudioFocus( this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN );
	}

	/**
	 * Abandona el foco del audio.
	 *
	 * @return - Entero que refleja el estado de la peticion.
	 */
	public void abandonAudioFocus()
	{
		AudioManager am = ( AudioManager ) this.getSystemService( Context.AUDIO_SERVICE );
		if ( null != am )
		{
			am.abandonAudioFocus( this );
		}
	}

	/**
	 * Reproduce un audio pidiendo antes el foco del audio.
	 */
	public void play()
	{
		if ( null != SanApptolinAudioPlayer.getInstance() && requestAudioFocus() == AudioManager.AUDIOFOCUS_REQUEST_GRANTED )
		{
			SanApptolinAudioPlayer.getInstance().start();
			SanApptolinAudioPlayer.notifyPlayListeners();
		}
	}

	/**
	 * Pausa un audio.
	 */
	public void pause()
	{
		if ( null != SanApptolinAudioPlayer.getInstance() && SanApptolinAudioPlayer.getInstance().isPlaying() )
		{
			SanApptolinAudioPlayer.getInstance().pause();
			SanApptolinAudioPlayer.notifyPauseListeners();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDestroy()
	{
		abandonAudioFocus();
		super.onDestroy();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param intent
	 * @return
	 */
	@Override
	public IBinder onBind( Intent intent )
	{
		return mBinder;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param focusChange
	 */
	@Override
	public void onAudioFocusChange( int focusChange )
	{
		if ( focusChange == AudioManager.AUDIOFOCUS_GAIN )
		{
			// No hacemos nada, ya que si el user quita el audio de otra app, pero el de
			// esta no estaba puesta, no deberia hacerlo.
		}
		else if ( focusChange == AudioManager.AUDIOFOCUS_LOSS ) // Hemos perdido el foco de audio, así que pausamos el player.
		{
			pause();

			// Si la app esta en segundo plano, la notificacion esta creada, por lo tanto
			// hay que actualizar la vista de la notificacion.
			AudioNotificationUtils.updateAudioNotificationPlayStatus( false );

		}
	}

}
