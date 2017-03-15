package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.bumptech.glide.Glide;
import com.rerijaapps.sanapptolin.R;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Detalle de la imagen de la galeria.
 *
 * Created by user on 09/11/2016.
 */
@EActivity ( R.layout.activity_gallery_image )
public class GalleryImageActivity extends AudioActivity
{

	/**
	 * Imagen de la galeria.
	 */
	@ViewById ( R.id.gallery_image )
	public ImageView mImage;

	/**
	 * Url image.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.url_image" )
	public String mUrlImage;

	/**
	 * ImageView attacher.
	 */
	private PhotoViewAttacher mAttacher;

	/**
	 * Inicializa las vistas.
	 */
	@AfterViews
	public void setupViews()
	{
		if ( null != mUrlImage && !mUrlImage.isEmpty() )
		{
			Glide.with( this ).load( mUrlImage ).into( mImage );
			mAttacher = new PhotoViewAttacher( mImage );
			mAttacher.update();
		}
	}

}
