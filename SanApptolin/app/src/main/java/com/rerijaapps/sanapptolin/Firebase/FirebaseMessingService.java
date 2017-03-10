package com.rerijaapps.sanapptolin.Firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Activities.SplashActivity_;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

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
			Intent notificationIntent = new Intent( this , SplashActivity_.class );
			notificationIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
			PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT );
			NotificationCompat.Builder builder = new NotificationCompat.Builder( this ).setSmallIcon( R.drawable.ic_stat_notification_icon )
					.setContentTitle( getString( R.string.app_name ) ).setContentText( messageBody ).setAutoCancel( true )
					.setSound( RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION ) ).setContentIntent( pendingIntent );
			NotificationManager notificationManager = ( NotificationManager ) getSystemService( Context.NOTIFICATION_SERVICE );
			notificationManager.notify( 0, builder.build() );
		}
	}

}
