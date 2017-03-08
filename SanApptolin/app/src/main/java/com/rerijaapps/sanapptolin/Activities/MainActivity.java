package com.rerijaapps.sanapptolin.Activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Adapter.MainEventsAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.Event;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Utils.InternetHelper;
import com.rerijaapps.sanapptolin.Utils.LogUtils;
import com.ufreedom.uikit.FloatingText;
import com.ufreedom.uikit.effect.CurveFloatingPathEffect;
import com.ufreedom.uikit.effect.CurvePathFloatingAnimator;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by jreci on 09/11/2016.
 */
@EActivity ( R.layout.activity_main )
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

	/**
	 * Recycler View.
	 */
	@ViewById ( R.id.main_recycler_view )
	public RecyclerView mRecyclerView;

	/**
	 * TextView para el nombre de la app.
	 */
	@ViewById ( R.id.main_app_name )
	public TextView mAppName;

	/**
	 * Vista del loader.
	 */
	@ViewById ( R.id.main_loader )
	public ProgressBar mLoader;

	/**
	 * Image de la app.
	 */
	@ViewById ( R.id.app_image )
	public KenBurnsView mAppImage;

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
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		mFloatingLoadingText = new FloatingText.FloatingTextBuilder( MainActivity.this ).textColor( Color.WHITE ).textSize( 50 )
				.floatingAnimatorEffect( new CurvePathFloatingAnimator() ).floatingPathEffect( new CurveFloatingPathEffect() )
				.textContent( getString( R.string.loading_programation ) ).build();
		mFloatingLoadingText.attach2Window();
		mAppName.setText( Constants.PARSE_APPNAME );
		Glide.with( this ).load( Constants.PARSE_APPIMAGE ).into( mAppImage );
		FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings.newBuilder( this ).withFanRadius( true ).withAngleItemBounce( 5 )
				.withViewWidthDp( 200 ).withViewHeightDp( ( getResources().getDisplayMetrics().heightPixels / getResources().getDisplayMetrics().density ) / 2 ).build();
		mFanLayoutManager = new FanLayoutManager( this , fanLayoutManagerSettings );
		mRecyclerView.setLayoutManager( mFanLayoutManager );
		MainEventsAdapter mainEventsAdapter = new MainEventsAdapter( Constants.PARSE_DAYS , this );
		mRecyclerView.setAdapter( mainEventsAdapter );
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
						loadingProgramation( ( ParseObject ) view.getTag() );
					}
				};
				new Handler().postDelayed( postRunnable, 1000 );
			}
		}
		else
		{
			showInternetErrorAndCloseApp();
		}
	}

	/**
	 * Muestra un error referente a la conexion y cierra la aplicacion.
	 */
	@UiThread
	public void showInternetErrorAndCloseApp()
	{
		new MaterialDialog.Builder( this ).title( R.string.internet_error_title ).content( R.string.error_internet ).positiveText( R.string.accept )
				.onPositive( new MaterialDialog.SingleButtonCallback()
				{
					@Override
					public void onClick( @NonNull MaterialDialog dialog, @NonNull DialogAction which )
					{
						finish();
					}
				} ).show();
	}

	/**
	 * Carga la programacion para el dia seleccionado.
	 *
	 * @param day - Dia seleccionado.
	 */
	@Background
	public void loadingProgramation( ParseObject day )
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
						postLoadingProgramation( dayInfo, eventArray );
					}
					else
					{
						postLoadingProgramation( null, null );
					}
				}
				else
				{
					postLoadingProgramation( null, null );
				}
			}
			catch ( Exception ex )
			{
				LogUtils.e( "ERROR_LOAADING_PROGRAMACION", ex.getMessage() );
				postLoadingProgramation( null, null );
			}
		}
		else
		{
			postLoadingProgramation( null, null );
		}
	}

	/**
	 * Realiza las operaciones necesarias tras cargar el evento.
	 *
	 * @param dayInfo - Info del dia.
	 * @param eventList - Lista de eventos.
	 */
	@UiThread
	public void postLoadingProgramation( DayInfo dayInfo, Event[] eventList )
	{
		isLoadingProgramation = false;
		mLoader.setVisibility( View.GONE );
		if ( null != dayInfo )
		{
			EventActivity_.intent( MainActivity.this ).mDayInfo( dayInfo ).mEventList( eventList ).start();
		}
	}
}
