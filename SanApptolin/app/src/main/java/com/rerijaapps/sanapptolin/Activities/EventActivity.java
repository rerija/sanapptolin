package com.rerijaapps.sanapptolin.Activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.Event;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
	public DayInfo mDayInfo;

	/**
	 * Listado de eventos.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.event_list" )
	public Event[] mEventList;

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
	 * Recycler para los eventos.
	 */
	@ViewById ( R.id.event_recycler )
	public RecyclerView mEventRecycler;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		if ( null != mDayInfo && null != mDayInfo.getImageDay() )
		{
			if ( null != mDayInfo.getImageDay() )
			{
				mEventImage.setImageBitmap( BitmapFactory.decodeByteArray( mDayInfo.getImageDay(), 0, mDayInfo.getImageDay().length ) );
			}
			if ( null != mDayInfo.getColorDay() )
			{
				mBackgroundView.setBackgroundColor( Color.parseColor( mDayInfo.getColorDay() ) );
			}
		}
	}

}