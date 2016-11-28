package com.rerijaapps.sanapptolin.Activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.john.waveview.WaveView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Adapter.MainEventsAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.ufreedom.uikit.FloatingText;
import com.ufreedom.uikit.effect.CurveFloatingPathEffect;
import com.ufreedom.uikit.effect.CurvePathFloatingAnimator;

import android.graphics.Color;
import android.os.Handler;
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
	 * Vista del Wave.
	 */
	@ViewById ( R.id.main_wave )
	public WaveView mWaveView;

	/**
	 * Vista del loader.
	 */
	@ViewById ( R.id.main_loader )
	public ProgressBar mLoader;

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
		FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings.newBuilder( this ).withFanRadius( true ).withAngleItemBounce( 5 )
				.withViewWidthDp( 200 ).withViewHeightDp( ( getResources().getDisplayMetrics().heightPixels / getResources().getDisplayMetrics().density ) / 2 ).build();
		mFanLayoutManager = new FanLayoutManager( this , fanLayoutManagerSettings );
		mRecyclerView.setLayoutManager( mFanLayoutManager );
		MainEventsAdapter mainEventsAdapter = new MainEventsAdapter( Constants.PARSE_DAYS , this );
		mRecyclerView.setAdapter( mainEventsAdapter );
		mWaveView.setProgress( 55 );
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
		if ( !isLoadingProgramation && null != view.getTag() )
		{
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

	/**
	 * Carga la programacion para el dia seleccionado.
	 *
	 * @param day - Dia seleccionado.
	 */
	@Background
	public void loadingProgramation( ParseObject day )
	{
		isLoadingProgramation = true;
		if ( null != day )
		{
			try
			{
				ParseQuery<ParseObject> imageDay = ParseQuery.getQuery( Constants.CLASS_IMAGES_NAME );
				imageDay.whereEqualTo( Constants.CLASS_IMAGES_COLUMN_IMAGEDAY_NAME, day );
				List<ParseObject> parseObjectList = imageDay.find();
				if ( null != parseObjectList && !parseObjectList.isEmpty() )
				{
					byte[] imageByteArray = parseObjectList.get( 0 ).getParseFile( Constants.CLASS_IMAGES_COLUMN_IMAGE_NAME ).getData();
					DayInfo dayInfo = new DayInfo();
					dayInfo.setImageDay( imageByteArray );
					dayInfo.setColorDay( day.getString( Constants.CLASS_APP_DAYS_COLUMN_COLORDAY_NAME ) );
					postLoadingProgramation(dayInfo);
				}
			}
			catch ( Exception ignored )
			{
				postLoadingProgramation( null );
			}
		}
	}

	/**
	 * Realiza las operaciones necesarias tras cargar el evento.
	 *
	 * @param dayInfo - Evento.
	 */
	@UiThread
	public void postLoadingProgramation( DayInfo dayInfo)
	{
		isLoadingProgramation = false;
		mLoader.setVisibility( View.GONE );
		if ( null != dayInfo)
		{
			EventActivity_.intent( MainActivity.this ).mDayInfo(dayInfo).start();
		}
	}
}
