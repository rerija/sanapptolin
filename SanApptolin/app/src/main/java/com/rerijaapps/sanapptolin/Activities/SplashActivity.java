package com.rerijaapps.sanapptolin.Activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Storage.PreferencesManager;
import com.rerijaapps.sanapptolin.Utils.InternetHelper;
import com.rerijaapps.sanapptolin.Utils.LogUtils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jreci on 09/11/2016.
 */
@EActivity ( R.layout.activity_splash )
public class SplashActivity extends AppCompatActivity
{

	/**
	 * Inicializa las vistas.
	 */
	@AfterViews
	public void setupViews()
	{
		doThreadDelay();
	}

	/**
	 * Realiza la operacion del delay y comprobacion del estado de la app.
	 */
	@Background
	public void doThreadDelay()
	{
		if ( InternetHelper.chekInternetAndConnection( this ) )
		{
			boolean appActive = false;
			String appName = null;
			String appImage = null;
			List<ParseObject> appDays = null;
			try
			{
				ParseQuery<ParseObject> parseQueryAppState = ParseQuery.getQuery( Constants.CLASS_APP_STATE_NAME );
				List<ParseObject> parseObjectAppStateList = parseQueryAppState.find();
				if ( null != parseObjectAppStateList )
				{
					appActive = parseObjectAppStateList.get( 0 ).getBoolean( Constants.CLASS_APP_STATE_COLUMN_ACTIVE_NAME );
					appName = parseObjectAppStateList.get( 0 ).getString( Constants.CLASS_APP_STATE_COLUMN_APPNAME_NAME );
					ParseFile appImageParseFile = parseObjectAppStateList.get( 0 ).getParseFile( Constants.CLASS_APP_STATE_COLUMN_APPIMAGE_NAME );
					if ( null != appImageParseFile )
					{
						appImage = appImageParseFile.getUrl();
					}
					if ( appActive )
					{
						ParseQuery<ParseObject> parseQueryDays = ParseQuery.getQuery( Constants.CLASS_APP_DAYS_NAME )
								.orderByAscending( Constants.CLASS_APP_DAYS_COLUMN_DAYNAME_NAME );
						appDays = parseQueryDays.find();
					}
				}
			}
			catch ( Exception ex )
			{
				LogUtils.e( "ERROR_GET_APP_ACTIVE", ex.getMessage() );
			}

			if ( !appActive || null == appDays )
			{
				AppNotActiveActivity_.intent( this ).start();
				finish();
			}
			else
			{
				Constants.PARSE_APPNAME = appName;
				Constants.PARSE_APPIMAGE = appImage;
				Constants.PARSE_DAYS = appDays;
				if ( PreferencesManager.getBoolean( Constants.PREFERENCE_NAME_SHOW_TUTORIAL, false ) )
				{
					goToMainActivity();
				}
				else
				{
					goToTutorialActivity();
				}
			}
		}
		else
		{
			showInternetErrorAndCloseApp();
		}
	}

	/**
	 * Muestra un error referente a la conexion y cierra la aplicacion.
	 */
	@UiThread
	public void showInternetErrorAndCloseApp()
	{
		new MaterialDialog.Builder( this ).title( R.string.internet_error_title ).content( R.string.error_internet ).positiveText( R.string.accept )
				.onPositive( new MaterialDialog.SingleButtonCallback()
				{
					@Override
					public void onClick( @NonNull MaterialDialog dialog, @NonNull DialogAction which )
					{
						finish();
					}
				} ).show();
	}

	/**
	 * Navega a la activity principal.
	 */
	@UiThread
	public void goToMainActivity()
	{
		MainActivity_.intent( this ).start();
		finish();
	}

	/**
	 * Navega a la activity de presentacion.
	 */
	@UiThread
	public void goToTutorialActivity()
	{
		startActivity( new Intent( this , TutorialActivity.class ) );
		finish();
	}

}
