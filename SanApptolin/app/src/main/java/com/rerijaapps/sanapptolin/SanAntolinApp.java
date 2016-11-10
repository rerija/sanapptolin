package com.rerijaapps.sanapptolin;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Storage.PreferencesManager;

import android.app.Application;

import org.androidannotations.annotations.sharedpreferences.Pref;

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
		PreferencesManager.init( this );
		Parse.initialize( new Parse.Configuration.Builder( this ).applicationId( Constants.PARSE_APP_ID ).clientKey( Constants.PARSE_APP_CLIENT_KEY )
				.server( Constants.PARSE_HOST_URL ).build() );
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
}
