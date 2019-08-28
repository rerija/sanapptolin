package com.rerijaapps.sanapptolin.Activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.SanApptolinGlide;
import com.rerijaapps.sanapptolin.Adapter.MainEventsAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.Event;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Utils.InternetHelper;
import com.rerijaapps.sanapptolin.Utils.LogHelper;
import com.ufreedom.uikit.FloatingText;
import com.ufreedom.uikit.effect.CurveFloatingPathEffect;
import com.ufreedom.uikit.effect.CurvePathFloatingAnimator;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import spencerstudios.com.ezdialoglib.EZDialog;

/**
 * Pantalla principal.
 * <p>
 * Created by jreci on 09/11/2016.
 */
@EActivity ( R.layout.activity_main )
public class MainActivity extends BasicActivity implements AdapterView.OnItemClickListener
{

	/**
	 * Recycler View.
	 */
	@ViewById ( R.id.main_recycler_view )
	public RecyclerView mRecyclerView;

	/**
	 * Image de la app.
	 */
	@ViewById ( R.id.app_image )
	public KenBurnsView mAppImage;

	/**
	 * Loader.
	 */
	@ViewById ( R.id.loader )
	public View mLoader;

	/**
	 * Layout manager para el recycler view.
	 */
	private FanLayoutManager mFanLayoutManager;

	/**
	 * Indica si esta cargando la programacion.
	 */
	private boolean isLoadingProgramation;

	/**
	 * Floating Text para la carga de los elementos del listado.
	 */
	private FloatingText mFloatingLoadingText;

	/**
	 * Interstitial Ad.
	 */
	private InterstitialAd mInterstitialAd;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		mFloatingLoadingText = new FloatingText.FloatingTextBuilder( MainActivity.this ).textColor( Color.WHITE ).textSize( 50 )
				.floatingAnimatorEffect( new CurvePathFloatingAnimator() ).floatingPathEffect( new CurveFloatingPathEffect() )
				.textContent( getString( R.string.loading_programation ) ).build();
		mFloatingLoadingText.attach2Window();
		SanApptolinGlide.with( this ).load( Constants.PARSE_APPIMAGE ).into( mAppImage );
		FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings.newBuilder( this ).withFanRadius( true ).withAngleItemBounce( 5 )
				.withViewWidthDp( 200 ).withViewHeightDp( ( getResources().getDisplayMetrics().heightPixels / getResources().getDisplayMetrics().density ) / 2 ).build();
		mFanLayoutManager = new FanLayoutManager( this , fanLayoutManagerSettings );
		mRecyclerView.setLayoutManager( mFanLayoutManager );
		MainEventsAdapter mainEventsAdapter = new MainEventsAdapter( Constants.PARSE_DAYS , this );
		mRecyclerView.setAdapter( mainEventsAdapter );

		// Configuramos el SDK.
		MobileAds.initialize( this, getString( R.string.banner_ad_app_id ) );
		mInterstitialAd = new InterstitialAd( this );
		mInterstitialAd.setAdUnitId( getString( R.string.banner_ad_unit_id ) ); // PRO
		// mInterstitialAd.setAdUnitId( "ca-app-pub-3940256099942544/1033173712" ); //
		// PRE
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param adapterView
	 * @param view
	 * @param position
	 * @param l
	 */
	@Override
	public void onItemClick( AdapterView<?> adapterView, final View view, int position, long l )
	{
		mFanLayoutManager.scrollToPosition( position );
		if ( InternetHelper.chekInternetAndConnection( this ) )
		{
			// Resto de operaciones.
			if ( !isLoadingProgramation && null != view.getTag() )
			{
				isLoadingProgramation = true;
				mLoader.setVisibility( View.VISIBLE );
				mFloatingLoadingText.startFloating( view );
				final Runnable postRunnable = new Runnable()
				{
					@Override
					public void run()
					{
						// Cargamos el AD.
						mInterstitialAd.setAdListener( new AdListener()
						{
							@Override
							public void onAdLoaded()
							{
								loadingProgramation( ( ParseObject ) view.getTag(), true );
							}

							@Override
							public void onAdFailedToLoad( int errorCode )
							{
								loadingProgramation( ( ParseObject ) view.getTag(), false );
							}

							@Override
							public void onAdOpened()
							{
							}

							@Override
							public void onAdLeftApplication()
							{
							}

							@Override
							public void onAdClosed()
							{

							}
						} );

						AdRequest adRequest = new AdRequest.Builder().build(); // PRO
						// AdRequest adRequest = new AdRequest.Builder().addTestDevice(
						// "C0D93FB09B0B0B3B0DD11312D0F874BE" ).build(); // PRE
						mInterstitialAd.loadAd( adRequest );
					}
				};
				new Handler().postDelayed( postRunnable, 1000 );
			}
		}
		else
		{
			showInternetError();
		}
	}

	/**
	 * Muestra un anuncio interstitial.
	 */
	private void showInterstitial()
	{
		if ( null != mInterstitialAd && mInterstitialAd.isLoaded() )
		{
			mInterstitialAd.show();
		}
	}

	/**
	 * Muestra un error referente a la conexion.
	 */
	@UiThread
	public void showInternetError()
	{
		new EZDialog.Builder( this ).setTitle( getString( R.string.internet_error_title ) ).setMessage( getString( R.string.error_internet ) ).setCancelableOnTouchOutside( false )
				.setPositiveBtnText( getString( R.string.accept ) ).build();
	}

	/**
	 * Carga la programacion para el dia seleccionado.
	 *
	 * @param day - Dia seleccionado.
	 * @param showAd - Indica si cargar el ad.
	 */
	@Background
	public void loadingProgramation( ParseObject day, boolean showAd )
	{
		if ( null != day )
		{
			try
			{
				ParseQuery<ParseObject> imageDay = ParseQuery.getQuery( Constants.CLASS_IMAGES_NAME );
				imageDay.whereEqualTo( Constants.CLASS_IMAGES_COLUMN_IMAGEDAY_NAME, day );
				List<ParseObject> parseImageDayList = imageDay.find();
				if ( null != parseImageDayList && !parseImageDayList.isEmpty() )
				{
					String urlImage = parseImageDayList.get( 0 ).getParseFile( Constants.CLASS_IMAGES_COLUMN_IMAGE_NAME ).getUrl();
					DayInfo dayInfo = new DayInfo();
					dayInfo.setImageDay( urlImage );
					dayInfo.setColorDay( day.getString( Constants.CLASS_APP_DAYS_COLUMN_COLORDAY_NAME ) );
					dayInfo.setDayName( day.getString( Constants.CLASS_APP_DAYS_COLUMN_DAYNAME_NAME ) );

					ParseQuery<ParseObject> eventList = ParseQuery.getQuery( Constants.CLASS_EVENT_NAME );
					eventList.whereEqualTo( Constants.CLASS_EVENT_COLUMN_DAY, day );
					eventList.orderByAscending( Constants.CLASS_EVENT_COLUMN_HOUR );

					List<ParseObject> parseEventList = eventList.find();

					if ( null != parseEventList && !parseEventList.isEmpty() )
					{
						Event[] eventArray = new Event[parseEventList.size()];
						for ( int i = 0; i < parseEventList.size(); i++ )
						{
							eventArray[i] = new Event( parseEventList.get( i ).getDate( Constants.CLASS_EVENT_COLUMN_HOUR ) ,
									parseEventList.get( i ).getString( Constants.CLASS_EVENT_COLUMN_DESCRIPTION ) );
						}
						GalleryActivity.mParseDayObj = day;
						postLoadingProgramation( dayInfo, eventArray, showAd );
					}
					else
					{
						postLoadingProgramation( null, null, showAd );
					}
				}
				else
				{
					postLoadingProgramation( null, null, showAd );
				}
			}
			catch ( Exception ex )
			{
				LogHelper.e( "ERROR_LOAADING_PROGRAMACION", ex.getMessage() );
				postLoadingProgramation( null, null, showAd );
			}
		}
		else
		{
			postLoadingProgramation( null, null, showAd );
		}
	}

	/**
	 * Realiza las operaciones necesarias tras cargar el evento.
	 *
	 * @param dayInfo - Info del dia.
	 * @param eventList - Lista de eventos.
	 * @param showAd - Indica si cargar el ad.
	 */
	@UiThread
	public void postLoadingProgramation( DayInfo dayInfo, Event[] eventList, boolean showAd )
	{
		isLoadingProgramation = false;
		if ( null != dayInfo )
		{
			mLoader.setVisibility( View.GONE );
			BasicActivity.DO_ON_PAUSE = false;
			BasicActivity.DO_ON_RESUME = false;
			EventActivity_.intent( MainActivity.this ).mDayInfo( dayInfo ).mEventList( eventList ).start();
			if ( showAd )
			{
				showInterstitial();
			}
		}
	}

	/**
	 * Evento que se genera al pulsar sobre el texto de escuchar la musica.
	 */
	@Click ( R.id.main_listen_music )
	public void clickListenMusic()
	{
		if ( null != Constants.PARSE_APPSONG_URL )
		{
			CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
			builder.setToolbarColor( getResources().getColor( R.color.colorPrimary ) );
			CustomTabsIntent customTabsIntent = builder.build();
			customTabsIntent.launchUrl( this, Uri.parse( Constants.PARSE_APPSONG_URL ) );
		}
	}
}
