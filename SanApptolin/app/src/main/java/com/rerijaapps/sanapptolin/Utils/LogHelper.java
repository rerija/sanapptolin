package com.rerijaapps.sanapptolin.Utils;

import com.rerijaapps.sanapptolin.Storage.Constants;

import android.util.Log;

/**
 * Clase personalizada del Log de Android.
 * 
 * @author javier
 * 
 */
public class LogHelper
{

	/**
	 * Metodo para mostrar informacion de alguna tarea.
	 * 
	 * @param tag - TAG.
	 * @param log - LOG.
	 */
	public static void i( String tag, String log )
	{
		if ( Constants.DEBUG_MODE && tag != null && log != null )
		{
			Log.i( tag, log );
		}
	}

	/**
	 * Metodo para mostrar informacion de algun error.
	 * 
	 * @param tag - TAG.
	 * @param log - LOG.
	 */
	public static void e( String tag, String log )
	{
		if ( Constants.DEBUG_MODE && tag != null && log != null )
		{
			Log.e( tag, log );
		}
	}

	/**
	 * Metodo para mostrar informacion de alguna tarea.
	 *
	 * @param tag - TAG.
	 * @param log - LOG.
	 */
	public static void d( String tag, String log )
	{
		if ( Constants.DEBUG_MODE && tag != null && log != null )
		{
			Log.d( tag, log );
		}
	}

}
