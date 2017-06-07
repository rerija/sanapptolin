package com.rerijaapps.sanapptolin.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.bumptech.glide.Glide;
import com.rerijaapps.sanapptolin.R;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Fragment para las imagenes.
 *
 * Created by javierrecio on 7/6/17.
 */
@EFragment ( R.layout.fragment_image )
public class FragmentImage extends Fragment
{

	/**
	 * Imagen de la galeria.
	 */
	@ViewById ( R.id.fragment_image_photoview )
	public ImageView mImage;

	/**
	 * ImageView attacher.
	 */
	private PhotoViewAttacher mAttacher;

	/**
	 * Argumento con la url de la imagen a exponer.
	 */
	public static final String URL_IMAGE_ARGUMENT = "com.rerijaapps.sanapptolin.url_image_argument";

	/**
	 * Inicializa las vistas.
	 */
	@AfterViews
	public void afterViews()
	{
		if ( null != getArguments() )
		{
			String urlImage = getArguments().getString( URL_IMAGE_ARGUMENT );
			if ( null != urlImage && !urlImage.isEmpty() )
			{
				Glide.with( this ).load( urlImage ).into( mImage );
				mAttacher = new PhotoViewAttacher( mImage );
				mAttacher.update();
			}
		}
	}

}
