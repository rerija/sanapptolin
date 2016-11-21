package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Serializable.Event;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by user on 21/11/2016.
 */
@EActivity ( R.layout.activity_event )
public class EventActivity extends AppCompatActivity
{

	/**
	 * Extra con el evento.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.event" )
	public Event mEvent;

	/**
	 * Image del evento.
	 */
	@ViewById ( R.id.event_image )
	public KenBurnsView mEventImage;

	/**
	 * Vista del background.
	 */
	@ViewById ( R.id.event_background )
	public View mBackgroundView;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		if ( null != mEvent && null != mEvent.getImageDay() )
		{
			if ( null != mEvent.getImageDay() )
			{
				mEventImage.setImageBitmap( BitmapFactory.decodeByteArray( mEvent.getImageDay(), 0, mEvent.getImageDay().length ) );
			}
			if ( null != mEvent.getColorDay() )
			{
				mBackgroundView.setBackgroundColor( Color.parseColor( mEvent.getColorDay() ) );
			}
		}
	}

}
