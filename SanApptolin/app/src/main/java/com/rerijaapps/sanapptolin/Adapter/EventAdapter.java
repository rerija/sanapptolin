package com.rerijaapps.sanapptolin.Adapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.rerijaapps.sanapptolin.Activities.AudioActivity;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.Event;
import com.rerijaapps.sanapptolin.Storage.Constants;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Adaptador de los eventos.
 *
 * Created by jreci on 28/11/2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>
{

	/**
	 * Listado de eventos.
	 */
	private Event[] mEvents;

	/**
	 * Info del dia.
	 */
	private DayInfo mDayInfo;

	/**
	 * Constructor.
	 *
	 * @param dayInfo - Info del dia.
	 * @param events - Lista de eventos.
	 */
	public EventAdapter( DayInfo dayInfo, Event[] events )
	{
		mDayInfo = dayInfo;
		mEvents = events;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public EventAdapter.EventHolder onCreateViewHolder( ViewGroup parent, int viewType )
	{
		View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.adapter_event, parent, false );
		EventHolder eventHolder = new EventHolder( view );
		return eventHolder;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder( EventHolder holder, int position )
	{
		holder.textEventHour
				.setText( null != mEvents[position].getHour() ? new SimpleDateFormat( "HH:mm" , Locale.getDefault() ).format( mEvents[position].getHour() ) : "" );
		holder.textEventDescription.setText( null != mEvents[position].getDescription() ? mEvents[position].getDescription() : "" );
		holder.eventShare.setTag( position );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param recyclerView
	 */
	@Override
	public void onAttachedToRecyclerView( RecyclerView recyclerView )
	{
		super.onAttachedToRecyclerView( recyclerView );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Override
	public int getItemCount()
	{
		return null != mEvents ? mEvents.length : 0;
	}

	/**
	 * EventHolder.
	 */
	class EventHolder extends RecyclerView.ViewHolder
	{

		/**
		 * Texto con la hora del evento.
		 */
		public TextView textEventHour;

		/**
		 * Texto con la descripcion del evento.
		 */
		public TextView textEventDescription;

		/**
		 * Icono para compartir contenido del evento.
		 */
		public View eventShare;

		/**
		 * Constructor.
		 *
		 * @param itemView - Vista.
		 */
		public EventHolder( View itemView )
		{
			super( itemView );
			textEventHour = ( TextView ) itemView.findViewById( R.id.event_hour );
			textEventDescription = ( TextView ) itemView.findViewById( R.id.event_description );
			eventShare = itemView.findViewById( R.id.event_share );
			eventShare.setOnClickListener( new View.OnClickListener()
			{
				@Override
				public void onClick( View view )
				{
					if ( null != view.getTag() )
					{
						Event event = mEvents[Integer.parseInt( view.getTag().toString() )];
						String shareText = view.getContext().getString( R.string.event_share_text,
								null != mDayInfo && null != mDayInfo.getDayName() ? mDayInfo.getDayName() : "", Constants.PARSE_APPNAME,
								null != event.getHour() ? new SimpleDateFormat( "HH:mm" , Locale.getDefault() ).format( event.getHour() ) : "",
								null != event.getDescription() ? event.getDescription() : "" );
						Intent sendIntent = new Intent();
						sendIntent.setAction( Intent.ACTION_SEND );
						sendIntent.putExtra( Intent.EXTRA_TEXT, shareText );
						sendIntent.setType( "text/plain" );
						view.getContext().startActivity( sendIntent );
					}
				}
			} );
		}
	}

}
