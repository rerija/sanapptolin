package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Adapter.YoutubeVideosAdapter;
import com.rerijaapps.sanapptolin.Network.Calls;
import com.rerijaapps.sanapptolin.Utils.InternetHelper;
import com.rerijaapps.sanapptolin.Youtube.ParseObjects.Youtube;

import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Pantalla con videos de youtube de telemedina.
 *
 * Created by user on 21/11/2016.
 */
@EActivity ( R.layout.activity_youtube )
public class YoutubeActivity extends BasicActivity implements SwipeRefreshLayout.OnRefreshListener
{

	/**
	 * Recycler para los videos de youtube.
	 */
	@ViewById ( R.id.youtube_recycler )
	public RecyclerView mYoutubeRecycler;

	/**
	 * Swipe refresh del recycler de los videos de youtube.
	 */
	@ViewById ( R.id.youtube_recycler_swipe )
	public SwipeRefreshLayout mSwipe;

	/**
	 * Indica si esta cargando.
	 */
	private boolean isLoading;

	/**
	 * Layout Manager.
	 */
	private RecyclerView.LayoutManager mLayoutManager;

	/**
	 * Adapter para los videos de Youtube.
	 */
	private YoutubeVideosAdapter mYoutubeVideosAdapter;

	/**
	 * Variables necesarias para el paginado.
	 */
	private int totalItemCount;
	private int lastVisibleItem;
	private int visibleThreshold = 5;

	/**
	 * Youtube actual.
	 */
	private Youtube mActualYoutube;

	/**
	 * Listener del Scroll del RecyclerView.
	 */
	private RecyclerView.OnScrollListener mScrollListener;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		mSwipe.setOnRefreshListener( this );

		if ( InternetHelper.chekInternetAndConnection( this ) )
		{
			getTelemedinaListOfYoutubeVideos();
		}
		else
		{
			showInternetErrorAndClose();
		}
	}

	/**
	 * Obtiene los videos de Youtube del canal de Telemedina.
	 */
	@Background
	public void getTelemedinaListOfYoutubeVideos()
	{
		isLoading = true;

		// Pedimos la siguiente pagina.
		if ( null != mActualYoutube )
		{
			mActualYoutube = Calls.getTelemedinaYoutubeVideos( mActualYoutube.getNextPageToken() );
		}
		else
		{
			setSwipeRefreshing( true );
			mActualYoutube = Calls.getTelemedinaYoutubeVideos( null );
		}

		runOnUiThread( new Runnable()
		{
			@Override
			public void run()
			{
				if ( null != mYoutubeVideosAdapter )
				{
					mYoutubeVideosAdapter.setLoadMore( false );
				}
			}
		} );

		isLoading = false;
		initRecyclerYoutubeVideo( mActualYoutube );
	}

	/**
	 * Indica el modo de refresco del swipe layout.
	 *
	 * @param refreshing - Indica si se debe refrecar.
	 */
	@UiThread
	public void setSwipeRefreshing( boolean refreshing )
	{
		mSwipe.setRefreshing( refreshing );
	}

	/**
	 * Inicia el recyclerview para el listado de los videos de Youtube.
	 *
	 * @param youtube - Objeto Youtube.
	 */
	@UiThread
	public void initRecyclerYoutubeVideo( Youtube youtube )
	{
		setSwipeRefreshing( false );

		if ( null != youtube && null != youtube.getItems() && !youtube.getItems().isEmpty() )
		{
			if ( null == mYoutubeVideosAdapter )
			{
				mLayoutManager = new LinearLayoutManager( this , LinearLayoutManager.VERTICAL , false );
				mYoutubeRecycler.setLayoutManager( mLayoutManager );

				mYoutubeVideosAdapter = new YoutubeVideosAdapter( this , youtube.getItems() );
				mYoutubeRecycler.setAdapter( mYoutubeVideosAdapter );

				mScrollListener = new RecyclerView.OnScrollListener()
				{
					@Override
					public void onScrolled( RecyclerView recyclerView, int dx, int dy )
					{
						super.onScrolled( recyclerView, dx, dy );
						totalItemCount = mLayoutManager.getItemCount();
						lastVisibleItem = ( ( LinearLayoutManager ) mLayoutManager ).findLastVisibleItemPosition();
						if ( !isLoading && totalItemCount <= ( lastVisibleItem + visibleThreshold ) && null != mActualYoutube
								&& null != mActualYoutube.getNextPageToken() )
						{
							isLoading = true;

							if ( mYoutubeVideosAdapter != null )
							{
								mYoutubeVideosAdapter.setLoadMore( true );
							}

							getTelemedinaListOfYoutubeVideos();
						}
					}
				};

				mYoutubeRecycler.addOnScrollListener( mScrollListener );
			}
			else
			{
				mYoutubeVideosAdapter.addItems( youtube.getItems() );
			}
		}
	}

	/**
	 * Muestra un error referente a la conexion y cierra la pantalla.
	 */
	@UiThread
	public void showInternetErrorAndClose()
	{
		new AlertDialog.Builder( this ).setTitle( getString( R.string.internet_error_title ) ).setMessage( getString( R.string.error_internet ) ).setCancelable( false )
				.setPositiveButton( getString( R.string.accept ), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick( DialogInterface dialogInterface, int i )
					{
						dialogInterface.cancel();
						finish();
					}
				} ).show();
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
	 * {@inheritDoc}
	 */
	@Override
	public void onRefresh()
	{
		mActualYoutube = null;
		mYoutubeVideosAdapter = null;
		mYoutubeRecycler.setAdapter( null );
		mYoutubeRecycler.removeOnScrollListener( mScrollListener );
		mScrollListener = null;
		getTelemedinaListOfYoutubeVideos();
	}
}