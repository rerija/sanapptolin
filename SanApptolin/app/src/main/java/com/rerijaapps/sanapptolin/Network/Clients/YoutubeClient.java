package com.rerijaapps.sanapptolin.Network.Clients;

import com.rerijaapps.sanapptolin.Youtube.ParseObjects.Youtube;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaz para las llamadas al Api de Youtube.
 */
public interface YoutubeClient
{

	/**
	 * Metodo GET para obtener un objeto Youtube con la lista de videos.
	 */
	@GET ( "/youtube/v3/search" )
	Call<Youtube> getVideoList( @Query ( "part" ) String part, @Query ( "channelId" ) String channelId, @Query ( "maxResults" ) String maxResults,
			@Query ( "order" ) String order, @Query ( "type" ) String type, @Query ( "key" ) String key );

	/**
	 * Metodo GET para obtener un objeto Youtube con la lista de videos.
	 */
	@GET ( "/youtube/v3/search" )
	Call<Youtube> getVideoList( @Query ( "part" ) String part, @Query ( "channelId" ) String channelId, @Query ( "maxResults" ) String maxResults,
			@Query ( "order" ) String order, @Query ( "type" ) String type, @Query ( "key" ) String key, @Query ( "pageToken" ) String pageToken );

}
