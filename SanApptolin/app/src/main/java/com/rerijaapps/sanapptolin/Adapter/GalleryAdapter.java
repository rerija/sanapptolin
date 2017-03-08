package com.rerijaapps.sanapptolin.Adapter;

import java.util.List;
import java.util.Random;

import com.bumptech.glide.Glide;
import com.rerijaapps.sanapptolin.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by jreci on 08/03/2017.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.EventHolder>
{

	/**
	 * Lista con las url de las fotos.
	 */
	private List<String> mUrlPhotos;

	/**
	 * Contexto de la app.
	 */
	private Context mContext;

	/**
	 * Constructor.
	 *
	 * @param context - Contexto de la app.
	 * @param urlPhotos - Listado con la url de las fotos.
	 */
	public GalleryAdapter(Context context, List<String> urlPhotos )
	{
		mContext = context;
		mUrlPhotos = urlPhotos;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public GalleryAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType )
	{
		View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.adapter_gallery, parent, false );
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
		Glide.with( mContext ).load( mUrlPhotos.get( position ) ).into( holder.galleryPhotoImage );
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
		return null != mUrlPhotos ? mUrlPhotos.size() : 0;
	}

	/**
	 * EventHolder.
	 */
	class EventHolder extends RecyclerView.ViewHolder
	{

		/**
		 * ImageView para la foto.
		 */
		public ImageView galleryPhotoImage;

		/**
		 * Constructor.
		 *
		 * @param itemView - Vista.
		 */
		public EventHolder( View itemView )
		{
			super( itemView );
			galleryPhotoImage = ( ImageView ) itemView.findViewById( R.id.gallery_image );
		}
	}

}
