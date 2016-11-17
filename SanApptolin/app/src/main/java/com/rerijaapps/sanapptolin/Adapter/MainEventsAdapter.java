package com.rerijaapps.sanapptolin.Adapter;

import java.util.List;

import com.bhargavms.dotloader.DotLoader;
import com.parse.ParseObject;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jreci on 10/11/2016.
 */
public class MainEventsAdapter extends RecyclerView.Adapter<MainEventsAdapter.CustomViewHolder>
{

	/**
	 * Lista de objetos de PArse referentes a los dias.
	 */
	private List<ParseObject> mDays;

	/**
	 * Contexto.
	 */
	private Context mContext;

	/**
	 * Indica si alguna vista en proceso de cargado de datos.
	 */
	private boolean isLoading = false;

	/**
	 * Constructor.
	 *
	 * @param context - Contexto.
	 * @param days - Dias.
	 */
	public MainEventsAdapter( Context context, List<ParseObject> days )
	{
		this.mDays = days;
		this.mContext = context;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param viewGroup
	 * @param i
	 * @return
	 */
	@Override
	public CustomViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
	{
		View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.adapter_main_events, null );
		CustomViewHolder viewHolder = new CustomViewHolder( view );
		return viewHolder;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param customViewHolder
	 * @param i
	 */
	@Override
	public void onBindViewHolder( CustomViewHolder customViewHolder, int i )
	{
		int colorCardView = Color.parseColor( "#FFFFFF" );
		try
		{
			colorCardView = Color.parseColor( mDays.get( i ).getString( Constants.CLASS_APP_DAYS_COLUMN_COLORDAY_NAME ) );
		}
		catch ( Exception ignored )
		{
		}
		customViewHolder.getDayText().setText( null != mDays.get( i ).getString( Constants.CLASS_APP_DAYS_COLUMN_DAYNAME_NAME )
				? mDays.get( i ).getString( Constants.CLASS_APP_DAYS_COLUMN_DAYNAME_NAME ) : "" );
		customViewHolder.getCardView().setCardBackgroundColor( colorCardView );
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
	class CustomViewHolder extends RecyclerView.ViewHolder
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
		public CustomViewHolder( View view )
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
