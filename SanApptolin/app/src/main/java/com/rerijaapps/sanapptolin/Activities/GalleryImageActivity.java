package com.rerijaapps.sanapptolin.Activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Fragment.FragmentImage;
import com.rerijaapps.sanapptolin.Fragment.FragmentImage_;

import android.os.Bundle;
import android.util.TypedValue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Detalle de la imagen de la galeria.
 *
 * Created by user on 09/11/2016.
 */
@EActivity ( R.layout.activity_gallery_image )
public class GalleryImageActivity extends BasicActivity
{

	/**
	 * Pager.
	 */
	@ViewById ( R.id.gallery_image_pager )
	public ViewPager mPager;

	/**
	 * Listado con las urls de las imagenes.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.extra_gallery_image_activity_url_images" )
	public String[] mUrlImages;

	/**
	 * Indice de la foto seleccinada anteriormente.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.extra_gallery_image_activity_photo_selected" )
	public int mPhotoSelected;

	/**
	 * Inicializa las vistas.
	 */
	@AfterViews
	public void setupViews()
	{
		if ( null != mUrlImages )
		{
			mPager.setOffscreenPageLimit( mUrlImages.length );
			mPager.setPageMargin( ( Math.round( TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics() ) ) ) );
			mPager.setPageMarginDrawable( android.R.color.black );
			mPager.setAdapter( new ImagePageAdapter( getSupportFragmentManager() , mUrlImages ) );
			mPager.setCurrentItem( mPhotoSelected, false );
		}
	}

	/**
	 * Adaptador para el pager con las imagenes.
	 */
	private class ImagePageAdapter extends FragmentStatePagerAdapter
	{

		/**
		 * Listado con las url de las imagenes.
		 */
		private String[] mImagesUrlList;

		/**
		 * Constructor.
		 *
		 * @param fm - FragmentManager.
		 * @param imagesUrlList - Listado con las url de las imagenes.
		 */
		public ImagePageAdapter( FragmentManager fm, String[] imagesUrlList )
		{
			super( fm );
			mImagesUrlList = imagesUrlList;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @param position
		 * @return
		 */
		@Override
		public Fragment getItem( int position )
		{
			FragmentImage fragmentImage = new FragmentImage_();
			Bundle bundle = new Bundle();
			bundle.putString( FragmentImage.URL_IMAGE_ARGUMENT, mImagesUrlList[position] );
			fragmentImage.setArguments( bundle );
			return fragmentImage;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @return
		 */
		@Override
		public int getCount()
		{
			return null != mImagesUrlList ? mImagesUrlList.length : 0;
		}
	}

}
