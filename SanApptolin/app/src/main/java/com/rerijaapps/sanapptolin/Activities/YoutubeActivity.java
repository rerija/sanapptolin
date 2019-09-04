package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.SanApptolinGlide;
import com.rerijaapps.sanapptolin.Adapter.EventAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.Event;
import com.rerijaapps.sanapptolin.Utils.InternetHelper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Pantalla con videos de youtube de telemedina.
 *
 * Created by user on 21/11/2016.
 */
@EActivity ( R.layout.activity_youtube )
public class YoutubeActivity extends BasicActivity
{

	/**
	 * Recycler para los videos de youtube.
	 */
	@ViewById ( R.id.youtube_recycler )
	public RecyclerView mYoutubeRecycler;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		if ( InternetHelper.chekInternetAndConnection( this ) )
		{

		}
		else
		{
			showInternetErrorAndClose();
		}
	}

	/**
	 * Muestra un error referente a la conexion y cierra la pantalla.
	 */
	@UiThread
	public void showInternetErrorAndClose()
	{
		new AlertDialog.Builder( this ).setTitle( getString( R.string.internet_error_title ) ).setMessage( getString( R.string.error_internet ) ).setCancelable( false )
				.setPositiveButton( getString( R.string.accept ), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick( DialogInterface dialogInterface, int i )
					{
						dialogInterface.cancel();
						finish();
					}
				} ).show();
	}

	/**
	 * Click para el boton back.
	 *
	 * @param view - Boton back.
	 */
	@Click ( R.id.back )
	public void clickBack( View view )
	{
		onBackPressed();
	}

}