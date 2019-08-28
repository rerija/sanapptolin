package com.rerijaapps.sanapptolin.Adapter;

import java.util.List;

import com.parse.ParseObject;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adaptador con las tarjetas de los dias.
 *
 * Created by jreci on 10/11/2016.
 */
public class MainEventsAdapter extends RecyclerView.Adapter<MainEventsAdapter.EventViewHolder>
{

	/**
	 * Lista de objetos de PArse referentes a los dias.
	 */
	private List<ParseObject> mDays;

	/**
	 * OnItemClickListener de las vistas.
	 */
	private AdapterView.OnItemClickListener mOnItemClickListener;

	/**
	 * Constructor.
	 *
	 * @param days - Dias.
	 * @param onItemClickListener - OnItemClickListener.
	 */
	public MainEventsAdapter( List<ParseObject> days, AdapterView.OnItemClickListener onItemClickListener )
	{
		this.mDays = days;
		this.mOnItemClickListener = onItemClickListener;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param parent
	 * @param i
	 * @return
	 */
	@Override
	public EventViewHolder onCreateViewHolder( ViewGroup parent, int i )
	{
		View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.adapter_main_events, parent, false );
		return new EventViewHolder( view );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param eventViewHolder
	 * @param i
	 */
	@Override
	public void onBindViewHolder( final EventViewHolder eventViewHolder, int i )
	{
		int colorCardView = Color.parseColor( "#FFFFFF" );
		try
		{
			colorCardView = Color.parseColor( mDays.get( i ).getString( Constants.CLASS_APP_DAYS_COLUMN_COLORDAY_NAME ) );
		}
		catch ( Exception ignored )
		{
		}
		eventViewHolder.getDayText()
				.setText( null != mDays.get( i ).getString( Constants.CLASS_APP_DAYS_COLUMN_DAYNAME_NAME )
						? mDays.get( i ).getString( Constants.CLASS_APP_DAYS_COLUMN_DAYNAME_NAME )
						: "" );
		eventViewHolder.getCardView().setCardBackgroundColor( colorCardView );
		eventViewHolder.itemView.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View view )
			{
				view.setTag( mDays.get( eventViewHolder.getAdapterPosition() ) );
				mOnItemClickListener.onItemClick( null, view, eventViewHolder.getAdapterPosition(), 0 );
			}
		} );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Override
	public int getItemCount()
	{
		return ( null != mDays ? mDays.size() : 0 );
	}

	/**
	 * Clase privada para personalizar el view holder.
	 */
	class EventViewHolder extends RecyclerView.ViewHolder
	{

		/**
		 * Vista Card de Google.
		 */
		private CardView mCardView;

		/**
		 * Vista del texto con el dia.
		 */
		private TextView mDayText;

		/**
		 * Constructor.
		 *
		 * @param view - Vista.
		 */
		public EventViewHolder( View view )
		{
			super( view );
			mCardView = ( CardView ) view;
			mDayText = ( TextView ) view.findViewById( R.id.adapter_main_events_day_text );
		}

		/**
		 * Devuelve la vista de la Card de Google.
		 *
		 * @return - Vista de la Card de Google.
		 */
		public CardView getCardView()
		{
			return mCardView;
		}

		/**
		 * Devuelve la vista del texto del dia.
		 *
		 * @return - Vista del texto del dia.
		 */
		public TextView getDayText()
		{
			return mDayText;
		}
	}
}
