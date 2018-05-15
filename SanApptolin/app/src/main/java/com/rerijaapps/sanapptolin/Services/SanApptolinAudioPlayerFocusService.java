package com.rerijaapps.sanapptolin.Services;

import com.rerijaapps.sanapptolin.Activities.AudioActivity;
import com.rerijaapps.sanapptolin.Singleton.SanApptolinAudioPlayer;

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
public class SanApptolinAudioPlayerFocusService extends Service implements AudioManager.OnAudioFocusChangeListener
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
		public SanApptolinAudioPlayerFocusService getService()
		{
			return SanApptolinAudioPlayerFocusService.this;
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
			if ( intent.getAction().equals( SanApptolinAudioPlayerFocusService.PLAY_ACTION ) )
			{
				play();
			}
			else if ( intent.getAction().equals( SanApptolinAudioPlayerFocusService.PAUSE_ACTION ) )
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
		if ( null != SanApptolinAudioPlayer.getInstance() )
		{
			SanApptolinAudioPlayer.getInstance().pause();
			SanApptolinAudioPlayer.notifyPauseListeners();
			abandonAudioFocus();
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
		switch ( focusChange )
		{
		case AudioManager.AUDIOFOCUS_GAIN:
			break;
		case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
			break;
		case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			if ( null != AudioActivity.PLAY_SERVICE )
			{
				AudioActivity.PLAY_SERVICE.pause();
			}
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			if ( null != AudioActivity.PLAY_SERVICE )
			{
				AudioActivity.PLAY_SERVICE.pause();
			}
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			break;
		}

	}

}
