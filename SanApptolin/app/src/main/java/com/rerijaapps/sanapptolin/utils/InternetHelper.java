package com.rerijaapps.sanapptolin.Utils;

import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.HandlerThread;
import android.os.NetworkOnMainThreadException;

/**
 * Helper para comprobar la conexion a internet.
 * 
 * @author jrecio.
 */
public class InternetHelper
{

	/**
	 * Metodo para comprobar si hay internet y conexión real
	 * 
	 * @param context - Contexto,
	 * @return si existe internet o no.
	 */
	public static Boolean chekInternetAndConnection( Context context )
	{
		ConnectivityManager connectivityManager;
		NetworkInfo wifiInfo, mobileInfo;

		try
		{
			connectivityManager = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
			wifiInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
			mobileInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );

			if ( ( wifiInfo.isConnected() || mobileInfo.isConnected() ) && internetAvailabe() )
			{
				return true;
			}
		}
		catch ( Exception ignored )
		{

		}

		return false;
	}

	/**
	 * Comprueba si hay acceso a internet
	 * 
	 * @return Si se ha realizado correctamente el ping a Google.
	 */
	public static boolean internetAvailabe()
	{
		try
		{
			InetAddress ipAddr = InetAddress.getByName( "google.com" );

			if ( ipAddr.equals( "" ) )
			{
				return false;
			}
			else
			{
				return true;
			}

		}
		catch ( Exception e )
		{
			if ( e instanceof NetworkOnMainThreadException )
			{
				try
				{
					final CountDownLatch latch = new CountDownLatch( 1 );
					final boolean[] value = new boolean[1];
					Thread internetThread = new HandlerThread( "UIHandler" )
					{
						@Override
						public void run()
						{
							value[0] = internetAvailabe();
							latch.countDown();
						}
					};
					internetThread.start();
					latch.await();
					return value[0];
				}
				catch ( Exception ex )
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}

}
