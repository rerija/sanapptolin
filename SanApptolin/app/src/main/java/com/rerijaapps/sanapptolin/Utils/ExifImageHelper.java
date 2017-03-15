package com.rerijaapps.sanapptolin.Utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * Helper para tratar las imagenes mediante Exif.
 *
 * Created by jreci on 09/03/2017.
 */
public class ExifImageHelper
{

	// Maximo tamano en alto de las imagenes.
	private static final int MAX_HEIGHT_IMAGE = 1080;

	/**
	 * Consigue la imagen correcta considerando los parametros Exif.
	 *
	 * @param path - Ruta.
	 * @param bitmap - Bitmap.
	 * @return - Imagen correcta con los parametros Exif.
	 */
	public static Bitmap getCorrectImageWithExifParams( String path, Bitmap bitmap )
	{
		try
		{
			ExifInterface exifInterface = new ExifInterface( path );
			Matrix matrix = new Matrix();
			int orientation = exifInterface.getAttributeInt( ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL );
			switch ( orientation )
			{
			case ExifInterface.ORIENTATION_ROTATE_270:
				matrix.postRotate( 270 );
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				matrix.postRotate( 180 );
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				matrix.postRotate( 90 );
				break;
			}

			bitmap = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false );

			if ( bitmap.getHeight() > MAX_HEIGHT_IMAGE )
			{
				bitmap = scaleDown( bitmap, MAX_HEIGHT_IMAGE, false );
			}
		}
		catch ( Exception ignored )
		{

		}
		return bitmap;
	}

	/**
	 * Escala la imagen dado un maximo tamano de alto.
	 * 
	 * @param realImage - Bitmap.
	 * @param maxImageSize - Maximo tamano.
	 * @param filter - Filtro.
	 * @return Imagen escalada.
	 */
	private static Bitmap scaleDown( Bitmap realImage, float maxImageSize, boolean filter )
	{
		float ratio = Math.min( maxImageSize / realImage.getWidth(), maxImageSize / realImage.getHeight() );
		double width = Math.round( ratio * realImage.getWidth() );
		double height = Math.round( ratio * realImage.getHeight() );
		return Bitmap.createScaledBitmap( realImage, ( int ) width, ( int ) height, filter );
	}

}
