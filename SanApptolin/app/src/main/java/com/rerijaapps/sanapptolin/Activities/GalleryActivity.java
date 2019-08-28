package com.rerijaapps.sanapptolin.Activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Adapter.GalleryAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;
import com.rerijaapps.sanapptolin.Serializable.GalleryImage;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Utils.ExifImageHelper;
import com.rerijaapps.sanapptolin.Utils.InternetHelper;
import com.rerijaapps.sanapptolin.Utils.LogHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Activity de la galeria de fotos.
 *
 * Created by user on 09/11/2016.
 */
@EActivity ( R.layout.activity_gallery )
public class GalleryActivity extends BasicActivity implements SwipeRefreshLayout.OnRefreshListener
{

	/**
	 * Extra con el evento.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.event" )
	public DayInfo mDayInfo;

	/**
	 * Grid para la galeria de las fotos.
	 */
	@ViewById ( R.id.gallery_recycler )
	public RecyclerView mGalleryRecycler;

	/**
	 * Texto con el dia del evento.
	 */
	@ViewById ( R.id.gallery_event_day )
	public TextView mGalleryEventDay;

	/**
	 * Swipe.
	 */
	@ViewById ( R.id.gallery_swipe )
	public SwipeRefreshLayout mSwipe;

	/**
	 * Contenedor para el texto de subir foto.
	 */
	@ViewById ( R.id.upload_photo_container )
	public View mUploadPhotoContainer;

	/**
	 * Boton para anadir foto.
	 */
	@ViewById ( R.id.fab_add_photo )
	public FloatingActionButton mAddPhotoButton;

	/**
	 * Objeto estatico con el objeto parse del dia.
	 */
	public static ParseObject mParseDayObj;

	/**
	 * Ultima ruta conocida de la camara.
	 */
	private String mLastCameraPhotoPath;

	/**
	 * Ultimo bitmap tomado con la camara.
	 */
	private Bitmap mLastCameraBitmap;

	/**
	 * Identificador para identificar que queremos hacer una captura con la camara.
	 */
	private final int REQUEST_IMAGE_CAPTURE = 0;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		mGalleryEventDay.setText( null != mDayInfo && null != mDayInfo.getDayName() ? getString( R.string.gallery_event_day, mDayInfo.getDayName() ) : "" );
		mSwipe.setOnRefreshListener( this );
		mSwipe.setColorSchemeResources( R.color.colorPrimary );
		refreshPhotoGallery();
	}

	/**
	 * Refresca la galeria de fotos.
	 */
	@Background
	public void refreshPhotoGallery()
	{
		setSwipeRefreshing( true );
		if ( InternetHelper.chekInternetAndConnection( this ) && null != mDayInfo && null != mParseDayObj )
		{
			ParseQuery<ParseObject> queryGallery = ParseQuery.getQuery( Constants.CLASS_DAY_IMAGES_NAME );
			queryGallery.whereEqualTo( Constants.CLASS_DAY_IMAGES_COLUMN_DAY, mParseDayObj );
			queryGallery.orderByDescending( Constants.CLASS_PARSE_COLUMN_CREATED_AT );
			try
			{
				List<ParseObject> listQueryGallery = queryGallery.find();
				if ( null != listQueryGallery && !listQueryGallery.isEmpty() )
				{
					List<GalleryImage> galleryImageList = new ArrayList<>();
					for ( ParseObject galleryParseObject : listQueryGallery )
					{
						galleryImageList.add( new GalleryImage( new SimpleDateFormat( "dd/MM/yyyy" , Locale.getDefault() ).format( galleryParseObject.getCreatedAt() ) ,
								new SimpleDateFormat( "HH:mm" , Locale.getDefault() ).format( galleryParseObject.getCreatedAt() ) ,
								galleryParseObject.getParseFile( Constants.CLASS_DAY_IMAGES_COLUMN_PHOTO ).getUrl() ,
								galleryParseObject.getString( Constants.CLASS_DAY_IMAGES_COLUMN_COMMENT ) ) );
					}
					setGallery( galleryImageList );
				}
			}
			catch ( Exception ex )
			{
				LogHelper.e( "ERROR_REFREH_PHOTO_GALLERY", ex.getMessage() );
			}
		}
		setSwipeRefreshing( false );
	}

	/**
	 * Establece la galeria.
	 *
	 * @param listGalleryImage - Lista de imagenes de la galeria.
	 */
	@UiThread
	public void setGallery( List<GalleryImage> listGalleryImage )
	{
		if ( null != listGalleryImage )
		{
			mGalleryRecycler.setAdapter( new GalleryAdapter( this , listGalleryImage ) );
		}
	}

	/**
	 * Establece el refresh para la lista de la galeria.
	 *
	 * @param refreshing - Indica si esta refrescado.
	 */
	@UiThread
	public void setSwipeRefreshing( boolean refreshing )
	{
		mSwipe.setRefreshing( refreshing );
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
	 * Click para anadir una foto.
	 *
	 * @param view - Boton back.
	 */
	@Click ( R.id.fab_add_photo )
	public void clickAddPhoto( View view )
	{
		mLastCameraPhotoPath = null;
		mLastCameraBitmap = null;
		if ( InternetHelper.chekInternetAndConnection( this ) )
		{
			dispatchTakePictureIntent();
		}
		else
		{
			showInternetError();
		}
	}

	/**
	 * Muestra un error referente a la conexion.
	 */
	@UiThread
	public void showInternetError()
	{
		new AlertDialog.Builder( this ).setTitle( getString( R.string.internet_error_title ) ).setMessage( getString( R.string.error_internet ) ).setCancelable( false )
				.setPositiveButton( getString( R.string.accept ), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick( DialogInterface dialogInterface, int i )
					{
						dialogInterface.cancel();
					}
				} ).show();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRefresh()
	{
		refreshPhotoGallery();
	}

	/**
	 * pone a disposicion la pantalla de captura de una foto con la camara.
	 */
	private void dispatchTakePictureIntent()
	{
		Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		if ( null != takePictureIntent.resolveActivity( getPackageManager() ) )
		{
			File photoFile = null;
			try
			{
				String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss" , Locale.getDefault() ).format( new Date() );
				String imageFileName = "JPEG_" + timeStamp + "_";
				File storageDir = getExternalFilesDir( Environment.DIRECTORY_PICTURES );
				photoFile = File.createTempFile( imageFileName, ".jpg", storageDir );
				mLastCameraPhotoPath = photoFile.getAbsolutePath();
				if ( null != photoFile )
				{
					Uri photoUri = FileProvider.getUriForFile( this, "com.rerijaapps.sanapptolin.fileprovider", photoFile );
					List<ResolveInfo> resolvedIntentActivities = getPackageManager().queryIntentActivities( takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY );
					if ( null != resolvedIntentActivities )
					{
						for ( ResolveInfo resolveInfo : resolvedIntentActivities )
						{
							String packageName = resolveInfo.activityInfo.packageName;
							getApplicationContext().grantUriPermission( packageName, photoUri,
									Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
						}
						takePictureIntent.setFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
						takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, photoUri );
						BasicActivity.DO_ON_PAUSE = false;
						BasicActivity.DO_ON_RESUME = false;
						startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
					}
				}
			}
			catch ( Exception ignored )
			{

			}
		}
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		try
		{
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inScaled = false;
			if ( resultCode == RESULT_OK && REQUEST_IMAGE_CAPTURE == requestCode && null != mLastCameraPhotoPath )
			{
				mLastCameraBitmap = BitmapFactory.decodeFile( mLastCameraPhotoPath, bmOptions );
				mLastCameraBitmap = ExifImageHelper.getCorrectImageWithExifParams( mLastCameraPhotoPath, mLastCameraBitmap );

				if ( InternetHelper.chekInternetAndConnection( this ) )
				{
					uploadPhoto( null );
				}
				else
				{
					showInternetError();
				}

			}
		}
		catch ( Exception ignored )
		{

		}
		super.onActivityResult( requestCode, resultCode, data );
	}

	/**
	 * Muestra el cuadro de progreso para subir una foto.
	 *
	 * @param show - Indica si lo muestra u oculta.
	 */
	@UiThread
	public void showProgressUploadPhoto( boolean show )
	{
		if ( show )
		{
			mAddPhotoButton.setEnabled( false );
			mUploadPhotoContainer.setVisibility( View.VISIBLE );
			mUploadPhotoContainer.startAnimation( AnimationUtils.loadAnimation( this, R.anim.fade_in ) );
		}
		else
		{
			mAddPhotoButton.setEnabled( true );
			mUploadPhotoContainer.setVisibility( View.GONE );
			mUploadPhotoContainer.startAnimation( AnimationUtils.loadAnimation( this, R.anim.fade_out ) );
		}
	}

	/**
	 * Sube una foto.
	 *
	 * @param comment - Comentario (opcional).
	 */
	@Background
	public void uploadPhoto( String comment )
	{
		if ( null != mParseDayObj && null != mLastCameraBitmap )
		{
			showProgressUploadPhoto( true );
			try
			{
				ParseObject dayImagesParseObjet = ParseObject.create( Constants.CLASS_DAY_IMAGES_NAME );
				if ( null != comment && !comment.isEmpty() )
				{
					dayImagesParseObjet.put( Constants.CLASS_DAY_IMAGES_COLUMN_COMMENT, comment );
				}
				dayImagesParseObjet.put( Constants.CLASS_DAY_IMAGES_COLUMN_DAY, mParseDayObj );

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				mLastCameraBitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream );

				ParseFile photoParseFile = new ParseFile( new SimpleDateFormat( "yyyyMMdd_HHmmss" , Locale.getDefault() ).format( new Date() ) , stream.toByteArray() );

				dayImagesParseObjet.put( Constants.CLASS_DAY_IMAGES_COLUMN_PHOTO, photoParseFile );

				dayImagesParseObjet.save();

				Thread.sleep( 1500 ); // Sleep para que le de tiempo a procesar
									  // los datos.

				postUploadPhoto( true );

				refreshPhotoGallery();
			}
			catch ( Exception ex )
			{
				postUploadPhoto( false );
			}
		}
		else
		{
			postUploadPhoto( false );
		}
	}

	/**
	 * Metodo posterior al de subir una foto.
	 *
	 * @param correct - Indica si es correcto o no.
	 */
	@UiThread
	public void postUploadPhoto( boolean correct )
	{
		showProgressUploadPhoto( false );

		if ( !correct )
		{
			new AlertDialog.Builder( this ).setTitle( getString( R.string.information ) ).setMessage( getString( R.string.error_upload_photo ) ).setCancelable( false )
					.setPositiveButton( getString( R.string.accept ), new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick( DialogInterface dialogInterface, int i )
						{
							dialogInterface.cancel();
						}
					} ).show();
		}
		else
		{
			new AlertDialog.Builder( this ).setTitle( getString( R.string.information ) ).setMessage( getString( R.string.success_upload_photo ) )
					.setPositiveButton( getString( R.string.accept ), new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick( DialogInterface dialogInterface, int i )
						{
							dialogInterface.cancel();
						}
					} ).setCancelable( false ).show();
		}
	}

}
