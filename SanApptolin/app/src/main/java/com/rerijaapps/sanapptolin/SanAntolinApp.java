package com.rerijaapps.sanapptolin;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.rerijaapps.sanapptolin.Storage.Constants;

import android.app.Application;

/**
 * Created by jreci on 09/11/2016.
 */
public class SanAntolinApp extends Application
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		Parse.initialize( new Parse.Configuration.Builder( this ).applicationId( Constants.PARSE_APP_ID ).clientKey( Constants.PARSE_APP_CLIENT_KEY )
				.server( Constants.PARSE_HOST_URL ).build() );
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
}
