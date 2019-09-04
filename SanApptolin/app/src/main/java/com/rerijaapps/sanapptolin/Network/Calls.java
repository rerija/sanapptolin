package com.rerijaapps.sanapptolin.Network;

import com.rerijaapps.sanapptolin.Network.Clients.HttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase para realizar llamadas externas.
 */
public class Calls
{

	/**
	 * Url base para youtube.
	 */
	private final static String YOUTUBE_API_BASE_URL = "https://www.googleapis.com";

	/**
	 * Consigue el objeto retrofit de Youtube.
	 *
	 * @return Objeto retrofit.
	 */
	private static Retrofit getRetrofitYoutube()
	{
		return new Retrofit.Builder().client( HttpClient.getRetrofitHttpClient() ).baseUrl( YOUTUBE_API_BASE_URL ).addConverterFactory( GsonConverterFactory.create() )
				.build();
	}

}
