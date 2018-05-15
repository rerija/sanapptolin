package com.rerijaapps.sanapptolin.Singleton;

import java.util.ArrayList;
import java.util.List;

import com.rerijaapps.sanapptolin.Activities.AudioActivity;
import com.rerijaapps.sanapptolin.Interfaces.IOnAudioPlayerStatusListener;
import com.rerijaapps.sanapptolin.Utils.AudioNotificationUtils;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Singleton para el audio player.
 *
 * Created by jreci on 16/10/2017.
 */
public class SanApptolinAudioPlayer
{

	/**
	 * Player para el audio.
	 */
	private static MediaPlayer mAudioPlayer;

	/**
	 * Listado con los listener registrados.
	 */
	private static List<IOnAudioPlayerStatusListener> mListenersRegistered;

	/**
	 * Consigue la instancia del audio player.
	 *
	 * @return Player para el audio.
	 */
	public static MediaPlayer getInstance()
	{
		if ( null == mAudioPlayer )
		{
			mAudioPlayer = new MediaPlayer();
			mAudioPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
		}
		return mAudioPlayer;
	}

	/**
	 * Registra un listener.
	 *
	 * @param listener - Listener.
	 */
	public static void registerListener( IOnAudioPlayerStatusListener listener )
	{
		mListenersRegistered = null != mListenersRegistered ? mListenersRegistered : new ArrayList<IOnAudioPlayerStatusListener>();
		mListenersRegistered.add( listener );
	}

	/**
	 * Notifica el play a los listeners.
	 */
	public static void notifyPlayListeners()
	{
		if ( null != mListenersRegistered && !mListenersRegistered.isEmpty() )
		{
			for ( IOnAudioPlayerStatusListener listener : mListenersRegistered )
			{
				listener.listenerPlay();
			}
		}
	}

	/**
	 * Notifica el pause a los listeners.
	 */
	public static void notifyPauseListeners()
	{
		if ( null != mListenersRegistered && !mListenersRegistered.isEmpty() )
		{
			for ( IOnAudioPlayerStatusListener listener : mListenersRegistered )
			{
				listener.listenerPause();
			}
		}
	}

	/**
	 * Broadcast para el play/pause de la notificacion del audio.
	 */
	public static class PlayPauseNotificationService extends IntentService
	{

		/**
		 * {@inheritDoc}
		 */
		public PlayPauseNotificationService()
		{
			super( "PlayPauseNotificationService" );
		}

		/**
		 * {@inheritDoc}
		 *
		 * @param intent
		 */
		@Override
		protected void onHandleIntent( Intent intent )
		{
			AudioNotificationUtils.updateAudioNotificationPlayStatus( !getInstance().isPlaying() );
			if ( null != AudioActivity.PLAY_SERVICE )
			{
				if ( getInstance().isPlaying() )
				{
					AudioActivity.PLAY_SERVICE.pause();
				}
				else
				{
					AudioActivity.PLAY_SERVICE.play();
				}
			}
		}
	}
}
