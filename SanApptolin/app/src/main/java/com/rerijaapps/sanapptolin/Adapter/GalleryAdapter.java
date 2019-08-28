package com.rerijaapps.sanapptolin.Adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.github.library.bubbleview.BubbleTextView;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Activities.BasicActivity;
import com.rerijaapps.sanapptolin.Activities.GalleryImageActivity_;
import com.rerijaapps.sanapptolin.SanApptolinGlide;
import com.rerijaapps.sanapptolin.Serializable.GalleryImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Adaptador de la galeria de fotos.
 *
 * Created by jreci on 08/03/2017.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.EventHolder>
{

	/**
	 * Lista con las fotos.
	 */
	private List<GalleryImage> mPhotos;

	/**
	 * Contexto de la app.
	 */
	private Context mContext;

	/**
	 * Constructor.
	 *
	 * @param context - Contexto de la app.
	 * @param urlPhotos - Listado con las fotos.
	 */
	public GalleryAdapter( Context context, List<GalleryImage> urlPhotos )
	{
		mContext = context;
		mPhotos = urlPhotos;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public GalleryAdapter.EventHolder onCreateViewHolder( ViewGroup parent, int viewType )
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
		SanApptolinGlide.with( mContext ).load( mPhotos.get( position ).getUrl() ).into( holder.galleryPhotoImage );
		holder.pubDate.setText( null != mPhotos.get( position ).getPubDate() && null != mPhotos
				? mContext.getString( R.string.published_at, mPhotos.get( position ).getPubDate(), mPhotos.get( position ).getPubDateHour() )
				: "" );
		holder.comment.setVisibility( null == mPhotos.get( position ).getComment() || mPhotos.get( position ).getComment().isEmpty() ? View.GONE : View.VISIBLE );
		holder.comment.setText( null != mPhotos.get( position ).getComment() ? mPhotos.get( position ).getComment() : "" );
		holder.comment.setTag( position );
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
		return null != mPhotos ? mPhotos.size() : 0;
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
		 * Fecha de publicacion.
		 */
		public TextView pubDate;

		/**
		 * Comentario.
		 */
		public BubbleTextView comment;

		/**
		 * Constructor.
		 *
		 * @param itemView - Vista.
		 */
		public EventHolder( View itemView )
		{
			super( itemView );
			galleryPhotoImage = ( ImageView ) itemView.findViewById( R.id.gallery_image );
			pubDate = ( TextView ) itemView.findViewById( R.id.gallery_pubdate );
			comment = ( BubbleTextView ) itemView.findViewById( R.id.gallery_comment );
			itemView.setOnClickListener( new View.OnClickListener()
			{
				@Override
				public void onClick( View view )
				{
					if ( null != mPhotos && !mPhotos.isEmpty() && null != comment && null != comment.getTag() )
					{
						String[] photosArray = new String[mPhotos.size()];

						for ( int i = 0; i < mPhotos.size(); i++ )
						{
							photosArray[i] = mPhotos.get( i ).getUrl();
						}

						BasicActivity.DO_ON_PAUSE = false;
						BasicActivity.DO_ON_RESUME = false;
						GalleryImageActivity_.intent( mContext ).mUrlImages( photosArray ).mPhotoSelected( Integer.parseInt( comment.getTag().toString() ) ).start();
					}
				}
			} );
		}
	}

}
