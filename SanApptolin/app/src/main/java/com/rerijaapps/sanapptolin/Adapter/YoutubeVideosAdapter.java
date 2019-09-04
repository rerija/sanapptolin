package com.rerijaapps.sanapptolin.Adapter;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.SanApptolinGlide;
import com.rerijaapps.sanapptolin.Utils.ArrayUtils;
import com.rerijaapps.sanapptolin.Youtube.ParseObjects.Item;
import com.rerijaapps.sanapptolin.Youtube.Player.YoutubePlayerScreen_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Adaptador para los videos de una temporada en la vista de detalle.
 */

public class YoutubeVideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

	/**
	 * Contexto.
	 */
	private Context mContext;

	/**
	 * Listado de videos.
	 */
	private List<Item> mVideos;

	/**
	 * Indica el tipo de modo loading bajo demanda
	 */
	private final int VIEW_TYPE_LOADING = -100;

	/**
	 * Constructor.
	 *
	 * @param context
	 * @param videos
	 */
	public YoutubeVideosAdapter( Context context, List<Item> videos )
	{
		mContext = context;
		mVideos = videos;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
	{
		RecyclerView.ViewHolder viewHolder;

		if ( viewType == VIEW_TYPE_LOADING )
		{
			viewHolder = new YoutubeLoadingViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.youtube_video_loading, parent, false ) );
		}
		else
		{
			View rootView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.adapter_youtube_videos, parent, false );
			viewHolder = new YoutubeVideoViewHolder( rootView );
		}

		return viewHolder;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType( int position )
	{
		return mVideos.get( position ) == null ? VIEW_TYPE_LOADING : -1;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
	{
		if ( holder instanceof YoutubeVideoViewHolder )
		{
			( ( YoutubeVideoViewHolder ) holder ).setData( mVideos.get( position ) );
		}
	}

	/**
	 * Elimina todos los elementos de la lista.
	 */
	public void removeAllItems()
	{
		if ( !ArrayUtils.isNullOrEmpty( mVideos ) )
		{
			mVideos.clear();
			notifyDataSetChanged();
		}
	}

	/**
	 * A?ade/Elimina un elemento con un spinner al final de la lista.
	 *
	 * @param loadMore
	 */
	public void setLoadMore( boolean loadMore )
	{
		if ( loadMore )
		{
			mVideos.add( null );
			notifyItemInserted( mVideos.size() - 1 );
		}
		else if ( mVideos.size() > 0 )
		{
			int loadingIndex = mVideos.indexOf( null );
			if ( loadingIndex != -1 )
			{
				mVideos.remove( loadingIndex );
				notifyItemRemoved( loadingIndex );
			}
		}
	}

	/**
	 * A?ade un array de elementos a la lista.
	 *
	 * @param newVideos
	 */
	public void addItems( List<Item> newVideos )
	{
		if ( mVideos == null )
		{
			mVideos = new ArrayList<>();
		}

		int startPosition = mVideos.size();
		int itemsCount = newVideos.size();
		mVideos.addAll( newVideos );

		notifyItemRangeInserted( startPosition, itemsCount );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Override
	public int getItemCount()
	{
		return mVideos == null ? 0 : mVideos.size();
	}

	/**
	 * ViewHolder para el loading bajo demanda.
	 */
	public class YoutubeLoadingViewHolder extends RecyclerView.ViewHolder
	{

		public YoutubeLoadingViewHolder( View itemView )
		{
			super( itemView );
		}
	}

	/**
	 * ViewHolder para los videos de una temporada.
	 */
	public class YoutubeVideoViewHolder extends RecyclerView.ViewHolder
	{
		private View mRootView;

		/**
		 * Imagen del video.
		 */
		private ImageView mImageView;

		/**
		 * Titulo del video.
		 */
		private TextView mTitle;

		/**
		 * Fecha del video.
		 */
		private TextView mDate;

		/**
		 * Constructor.
		 *
		 * @param itemView
		 */
		public YoutubeVideoViewHolder( View itemView )
		{
			super( itemView );
			mRootView = itemView;
			mImageView = itemView.findViewById( R.id.youtube_video_img );
			mTitle = itemView.findViewById( R.id.youtube_video_title );
			mDate = itemView.findViewById( R.id.youtube_video_date );
		}

		/**
		 * Rellena los campos de la vista.
		 *
		 * @param video
		 */
		public void setData( final Item video )
		{
			if ( null != video && null != video.getSnippet() )
			{
				// Imagen.
				if ( null != video.getSnippet().getThumbnails() && null != video.getSnippet().getThumbnails().getHigh()
						&& null != video.getSnippet().getThumbnails().getHigh().getUrl() )
				{
					SanApptolinGlide.with( mContext ).load( video.getSnippet().getThumbnails().getHigh().getUrl() ).centerCrop().into( mImageView );
				}

				// Titulo.
				mTitle.setText( null != video.getSnippet().getTitle() ? video.getSnippet().getTitle() : "" );

				// Fecha.
				mDate.setText( null != video.getSnippet().getPublishedAt()
						? mContext.getString( R.string.published_at ) + " " + new DateTime( video.getSnippet().getPublishedAt() ).toString( "dd/MM/yyyy HH:mm" )
						: "" );

				mRootView.setOnClickListener( new View.OnClickListener()
				{
					@Override
					public void onClick( View v )
					{
						if ( null != video.getId() && null != video.getId().getVideoId() )
						{
							YoutubePlayerScreen_.intent( mContext ).mVideoId( video.getId().getVideoId() ).start();
						}
					}
				} );
			}
		}
	}
}