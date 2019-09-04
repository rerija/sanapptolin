package com.rerijaapps.sanapptolin.Network;

import com.rerijaapps.sanapptolin.Network.Clients.HttpClient;
import com.rerijaapps.sanapptolin.Network.Clients.YoutubeClient;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Utils.LogHelper;
import com.rerijaapps.sanapptolin.Youtube.ParseObjects.Youtube;

import java.io.IOException;

import retrofit2.Response;
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
	 * Consigue la lista de videos de telemedina de Youtube.
	 *
	 * @return Lista de videos de playZ de Youtube.
	 */
	public static Youtube getTelemedinaYoutubeVideos(String nextPageToken )
	{
		Youtube youtube = null;
		try
		{

			String part = "snippet";
			String channelId = "UC6M4TvYHaFQ30IpVvjLVviw";
			String maxResults = "50";
			String order = "date";
			String type = "video";
			String key = Constants.YOUTUBE_API_KEY;

			Response<Youtube> response;

			if ( null == nextPageToken )
			{
				response = getRetrofitYoutube().create( YoutubeClient.class ).getVideoList( part, channelId, maxResults, order, type, key ).execute();
			}
			else
			{
				response = getRetrofitYoutube().create( YoutubeClient.class ).getVideoList( part, channelId, maxResults, order, type, key, nextPageToken ).execute();
			}

			if ( response != null && response.body() != null )
			{
				youtube = response.body();
			}
		}
		catch ( IOException e )
		{
			LogHelper.e( "getTelemedinaYoutubeVideos", e.getMessage() );
		}

		return youtube;
	}

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
