package com.rerijaapps.sanapptolin.Utils;

import java.util.Random;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Singleton.SanApptolinAudioPlayer;
import com.rerijaapps.sanapptolin.Storage.Constants;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Util para las notificaciones del audio.
 * 
 * Created by jreci on 21/06/2017.
 */
public class AudioNotificationUtils
{

	/**
	 * Notification para el audio.
	 */
	private static Notification mAudioNotification;

	/**
	 * Notification builder.
	 */
	private static NotificationCompat.Builder mAudioNotificationBuilder;

	/**
	 * Notification manager.
	 */
	private static NotificationManager mNotificationManager;

	/**
	 * RemoteViews.
	 */
	private static RemoteViews mRemoteViews;

	/**
	 * Identificador de la notificacion.
	 */
	private static int mNotificationId;

	/**
	 * Crea la notificacion para el audio.
	 *
	 * @param activity - Activity.
	 */
	public static void createAudioNotification( final Activity activity )
	{
		if ( null != activity )
		{
			if ( null == mNotificationManager )
			{
				mNotificationManager = ( NotificationManager ) activity.getSystemService( Activity.NOTIFICATION_SERVICE );
			}

			if ( null != mAudioNotification )
			{
				cancelAudioNotification();
			}

			if ( null == mRemoteViews )
			{
				mRemoteViews = new RemoteViews( activity.getPackageName() , R.layout.audio_notification );

				Intent playPauseIntent = new Intent( activity , SanApptolinAudioPlayer.PlayPauseNotificationService.class );
				final PendingIntent pendingPlayPauseIntent = PendingIntent.getService( activity, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT );
				mRemoteViews.setOnClickPendingIntent( R.id.play_pause, pendingPlayPauseIntent );
			}

			mRemoteViews.setImageViewResource( R.id.play_pause, R.drawable.ic_play_circle_filled_white_36dp );

			Intent intent = new Intent( activity , activity.getClass() );
			intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );

			PendingIntent pendingIntent = PendingIntent.getActivity( activity, mNotificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT );

			// Configuramos la notificacion para Android O.
			if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
			{
				NotificationChannel androidChannel = new NotificationChannel( Constants.NOTIFICATION_CHANNEL_NAME , Constants.NOTIFICATION_CHANNEL_ID ,
						NotificationManager.IMPORTANCE_DEFAULT );
				androidChannel.enableLights( true );
				androidChannel.enableVibration( false );
				androidChannel.setLockscreenVisibility( Notification.VISIBILITY_PUBLIC );
				androidChannel.setSound( null, null );
				mNotificationManager.createNotificationChannel( androidChannel );
				mAudioNotificationBuilder = new NotificationCompat.Builder( activity , Constants.NOTIFICATION_CHANNEL_NAME )
						.setSmallIcon( R.drawable.ic_stat_notification_icon ).setAutoCancel( false ).setCustomContentView( mRemoteViews ).setOngoing( true )
						.setContentIntent( pendingIntent );
			}
			else
			{
				mAudioNotificationBuilder = new NotificationCompat.Builder( activity ).setSmallIcon( R.drawable.ic_stat_notification_icon ).setAutoCancel( false )
						.setCustomContentView( mRemoteViews ).setOngoing( true ).setContentIntent( pendingIntent );
			}

			if ( null == mAudioNotification )
			{
				mAudioNotification = mAudioNotificationBuilder.build();
				mAudioNotification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
			}

			mNotificationId = new Random().nextInt();

			mNotificationManager.notify( mNotificationId, mAudioNotification );
		}
	}

	/**
	 * Actualiza el estado del reproductor de la notificacion.
	 *
	 * @param isPlaying - Indica si se esta reproduciendo o no.
	 */
	public static void updateAudioNotificationPlayStatus( boolean isPlaying )
	{
		if ( null != mRemoteViews && null != mAudioNotification && null != mNotificationManager )
		{
			mRemoteViews.setImageViewResource( R.id.play_pause, isPlaying ? R.drawable.ic_pause_circle_filled_white_36dp : R.drawable.ic_play_circle_filled_white_36dp );
			mNotificationManager.notify( mNotificationId, mAudioNotification );
		}
	}

	/**
	 * Cancela la notificacion de audio.
	 */
	public static void cancelAudioNotification()
	{
		if ( null != mNotificationManager )
		{
			mNotificationManager.cancelAll();
		}
	}

}
