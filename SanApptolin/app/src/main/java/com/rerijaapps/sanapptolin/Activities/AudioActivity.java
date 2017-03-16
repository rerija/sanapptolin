package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Storage.PreferencesManager;
import com.rerijaapps.sanapptolin.Utils.LogUtils;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
	 * Player de la musica.
	 */
	private static MediaPlayer mPlayer;

	/**
	 * Indica si realizar la accion del pause.
	 */
	public static boolean DO_ON_PAUSE = true;

	/**
	 * Indica si realizar la accion del resume.
	 */
	public static boolean DO_ON_RESUME = false;

	/**
	 * Empieza el media player.
	 */
	@Background
	public void startMediaPlayer()
	{
		if ( null == mPlayer )
		{
			mPlayer = new MediaPlayer();
		}
		try
		{
			mPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
			mPlayer.setLooping( true );
			mPlayer.setDataSource( Constants.PARSE_APPSONG_URL );
			mPlayer.prepare();
			mPlayer.start();
		}
		catch ( Exception ex )
		{
			LogUtils.e( "ERR_START_MEDIA_PLAYER", ex.getMessage() );
		}
	}

	/**
	 * Para el player.
	 */
	public void stopMediaPlayer()
	{
		if  ( null != mPlayer && mPlayer.isPlaying() )
		{
			mPlayer.stop();
			mPlayer = null;
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
			if ( null != mPlayer && mPlayer.isPlaying() )
			{
				mPlayer.pause();
			}
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
			if ( null != mPlayer && !mPlayer.isPlaying() && PreferencesManager.getBoolean( Constants.PREFERENCE_NAME_PLAY_MUSIC, false ) )
			{
				if ( mPlayer.getCurrentPosition() > 0 )
				{
					mPlayer.seekTo( mPlayer.getCurrentPosition() );
					mPlayer.start();
				}
				else if ( null != Constants.PARSE_APPSONG_URL )
				{
					startMediaPlayer();
				}
			}
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
			System.exit( 0 );
        }
		super.onBackPressed();
	}
}
