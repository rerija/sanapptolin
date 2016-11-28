package com.rerijaapps.sanapptolin.Adapter;

import java.util.List;

import com.parse.ParseObject;
import com.rerijaapps.sanapptolin.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jreci on 28/11/2016.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>
{

	/**
	 * Listado de eventos.
	 */
	private List<ParseObject> mEvents;

	/**
	 * Constructor.
	 *
	 * @param events - Lista de eventos.
	 */
	public EventAdapter( List<ParseObject> events )
	{
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
		return null != mEvents ? mEvents.size() : 0;
	}

    /**
     * EventHolder.
     */
	class EventHolder extends RecyclerView.ViewHolder
	{

        /**
         * Constructor.
         *
         * @param itemView - Vista.
         */
		public EventHolder( View itemView )
		{
			super( itemView );
		}
	}

}
