package com.rerijaapps.sanapptolin.Firebase;

import java.util.Random;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Activities.SplashActivity_;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Utils.LogHelper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * Servicio de mensajeria de Firebase.
 *
 * Created by jreci on 10/03/2017.
 */
public class FirebaseMessingService extends FirebaseMessagingService
{

	/**
	 * {@inheritDoc}
	 *
	 * @param remoteMessage
	 */
	@Override
	public void onMessageReceived( RemoteMessage remoteMessage )
	{
		if ( remoteMessage.getNotification() != null )
		{
			sendNotification( remoteMessage.getNotification().getBody() );
		}
	}

	/**
	 * Envia una notiificacion.
	 *
	 * @param messageBody - Cuerpo del mensaje.
	 */
	private void sendNotification( String messageBody )
	{
		if ( null != messageBody && !messageBody.isEmpty() )
		{
			Intent intent = new Intent( getApplicationContext() , SplashActivity_.class );
			intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );

			PendingIntent pendingIntent = PendingIntent.getActivity( this, ( int ) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT );

			NotificationManager notificationManager = ( NotificationManager ) getSystemService( Context.NOTIFICATION_SERVICE );

			if ( null != notificationManager )
			{
				NotificationCompat.Builder builder;

				// Configuramos la notificacion para Android O.
				if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
				{
					NotificationChannel androidChannel = new NotificationChannel( Constants.NOTIFICATION_CHANNEL_ID , Constants.NOTIFICATION_CHANNEL_NAME ,
							NotificationManager.IMPORTANCE_DEFAULT );
					androidChannel.enableLights( true );
					androidChannel.enableVibration( true );
					androidChannel.setLockscreenVisibility( Notification.VISIBILITY_PUBLIC );
					notificationManager.createNotificationChannel( androidChannel );
					builder = new NotificationCompat.Builder( this , Constants.NOTIFICATION_CHANNEL_ID ).setSmallIcon( R.drawable.ic_stat_notification_icon )
							.setContentTitle( getString( R.string.app_name ) ).setContentText( messageBody ).setAutoCancel( true )
							.setSound( RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION ) ).setContentIntent( pendingIntent );
				}
				else
				{
					builder = new NotificationCompat.Builder( this ).setSmallIcon( R.drawable.ic_stat_notification_icon )
							.setContentTitle( getString( R.string.app_name ) ).setContentText( messageBody ).setAutoCancel( true )
							.setSound( RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION ) ).setContentIntent( pendingIntent );
				}

				Notification notification = builder.build();
				notificationManager.notify( new Random().nextInt(), notification );
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param token
	 */
	@Override
	public void onNewToken( String token )
	{
		LogHelper.i( "FirebaseMessingService", "TOKEN: " + token );
		super.onNewToken( token );
	}
}
