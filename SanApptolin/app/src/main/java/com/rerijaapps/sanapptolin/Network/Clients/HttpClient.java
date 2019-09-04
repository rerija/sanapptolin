package com.rerijaapps.sanapptolin.Network.Clients;

import java.util.concurrent.TimeUnit;

import com.rerijaapps.sanapptolin.Storage.Constants;

import okhttp3.OkHttpClient;

/**
 * Cliente para las llamadas de RTVE.
 */
public class HttpClient
{
	private static OkHttpClient OK_HTTP_CLIENT;

	private static void buildHttpClient()
	{
		if ( null == OK_HTTP_CLIENT )
		{
			OK_HTTP_CLIENT = new OkHttpClient().newBuilder().connectTimeout( Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS )
					.readTimeout( Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS ).writeTimeout( Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS ).build();
		}
	}

	public static OkHttpClient getRetrofitHttpClient()
	{
		buildHttpClient();
		return OK_HTTP_CLIENT;
	}
}