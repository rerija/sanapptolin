package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.SanApptolinGlide;
import com.rerijaapps.sanapptolin.Adapter.EventAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.Event;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Pantalla con los eventos.
 *
 * Created by user on 21/11/2016.
 */
@EActivity ( R.layout.activity_event )
public class EventActivity extends BasicActivity
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
	 * Texto con el nombre del dia.
	 */
	@ViewById ( R.id.event_day )
	public TextView mDayNameText;

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
		mDayNameText.setText( null != mDayInfo.getDayName() ? mDayInfo.getDayName() : "" );

		if ( null != mDayInfo )
		{
			SanApptolinGlide.with( this ).load( mDayInfo.getImageDay() ).into( mEventImage );
			if ( null != mDayInfo.getColorDay() )
			{
				mBackgroundView.setBackgroundColor( Color.parseColor( mDayInfo.getColorDay() ) );
				( ( GradientDrawable ) mDayNameText.getBackground() ).setColor( Color.parseColor( mDayInfo.getColorDay() ) );
			}
		}
		if ( null != mEventList && 0 < mEventList.length )
		{
			mEventRecycler.setAdapter( new EventAdapter( mDayInfo , mEventList ) );
		}
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

	/**
	 * Click para el boton de galeria de fotos.
	 *
	 * @param view - Boton de galeria de fotos.
	 */
	@Click ( R.id.photos )
	public void clickPhotos( View view )
	{
		BasicActivity.DO_ON_PAUSE = false;
		BasicActivity.DO_ON_RESUME = false;
		GalleryActivity_.intent( this ).mDayInfo( mDayInfo ).start();
	}

}