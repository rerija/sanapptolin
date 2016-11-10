package com.rerijaapps.sanapptolin.Adapter;

import java.util.List;

import com.parse.ParseObject;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		 * Constructor.
		 *
		 * @param view - Vista.
		 */
		public CustomViewHolder( View view )
		{
			super( view );
			mCardView = ( CardView ) view;
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
	}
}
