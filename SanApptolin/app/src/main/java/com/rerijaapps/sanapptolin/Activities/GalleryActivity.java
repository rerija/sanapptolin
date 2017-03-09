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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

/**
 * Activity de la galeria de fotos.
 *
 * Created by user on 09/11/2016.
 */
@EActivity ( R.layout.activity_gallery )
public class GalleryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
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
	 * Identificador para identificar que queremos hacer una captura con la
	 * camara.
	 */
	private final int REQUEST_IMAGE_CAPTURE = 0;

	/**
	 * Dialogo de progreso.
	 */
	private MaterialDialog mUploadPhotoProgress;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		mUploadPhotoProgress = new MaterialDialog.Builder( this ).cancelable( false ).title( R.string.adding_photo ).content( R.string.please_wait ).progress( true, 0 )
				.progressIndeterminateStyle( true ).build();
		mGalleryEventDay.setText( null != mDayInfo && null != mDayInfo.getDayName() ? getString( R.string.gallery_event_day, mDayInfo.getDayName() ) : "" );
		mSwipe.setOnRefreshListener( this );
		mSwipe.setColorSchemeResources( R.color.colorPrimary );
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
			}
		}
		else
		{
			setGallery( null );
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
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume()
	{
		if ( null != mGalleryRecycler && null != mSwipe )
		{
			refreshPhotoGallery();
		}
		super.onResume();
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
			if ( resultCode == RESULT_OK && REQUEST_IMAGE_CAPTURE == requestCode && null != mLastCameraPhotoPath )
			{
				mLastCameraBitmap = BitmapFactory.decodeFile( mLastCameraPhotoPath, bmOptions );
			}
			mLastCameraBitmap = ExifImageHelper.getCorrectImageWithExifParams( mLastCameraPhotoPath, mLastCameraBitmap );
			new MaterialDialog.Builder( this ).title( R.string.add_comment ).content( R.string.add_comment_description )
					.inputType( InputType.TYPE_TEXT_FLAG_CAP_SENTENCES ).inputRange( 0, 50, Color.RED ).input( R.string.add_comment_hint, 0, true, new MaterialDialog.InputCallback()
					{
						@Override
						public void onInput( MaterialDialog dialog, CharSequence input )
						{
							uploadPhoto( null != input && !input.toString().isEmpty() ? input.toString() : null );
						}
					} ).cancelable( false ).negativeText( R.string.cancel ).onNegative( new MaterialDialog.SingleButtonCallback()
					{
						@Override
						public void onClick( @NonNull MaterialDialog dialog, @NonNull DialogAction which )
						{
							uploadPhoto( null );
						}
					} ).show();
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
			mUploadPhotoProgress.show();
		}
		else
		{
			mUploadPhotoProgress.dismiss();
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
			showProgressUploadPhoto( false );
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
		if ( !correct )
		{
			new MaterialDialog.Builder( this ).title( R.string.information ).content( R.string.error_upload_photo ).positiveText( R.string.accept ).show();
		}
		else
		{
			new MaterialDialog.Builder( this ).title( R.string.information ).content( R.string.success_upload_photo ).positiveText( R.string.accept ).show();
		}
	}

}
