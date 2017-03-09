package com.rerijaapps.sanapptolin.Serializable;

import java.io.Serializable;

/**
 * Clase para la imagen de la galeria de fotos.
 *
 * Created by jreci on 09/03/2017.
 */
public class GalleryImage implements Serializable
{
	private static final long serialVersionUID = -92070123342566915L;

	/**
	 * Fecha de publicacion.
	 */
	private String mPubDate;

	/**
	 * Hora de publicacion.
	 */
	private String mPubDateHour;

	/**
	 * Url de la foto.
	 */
	private String mUrl;

	/**
	 * Comentario.
	 */
	private String mComment;

	/**
	 * Constructor.
	 *
	 * @param mPubDate - Fecha de publicacion.
	 * @param mPubDateHour - Hora de publicacion.
	 * @param mUrl - Url.
	 * @param mComment - Comentario.
	 */
	public GalleryImage( String mPubDate, String mPubDateHour, String mUrl, String mComment )
	{
		this.mPubDate = mPubDate;
		this.mPubDateHour = mPubDateHour;
		this.mUrl = mUrl;
		this.mComment = mComment;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getPubDateHour()
	{
		return mPubDateHour;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mPubDateHour
	 */
	public void setPubDateHour( String mPubDateHour )
	{
		this.mPubDateHour = mPubDateHour;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getPubDate()
	{
		return mPubDate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mPubDate
	 */
	public void setPubDate( String mPubDate )
	{
		this.mPubDate = mPubDate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getUrl()
	{
		return mUrl;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mUrl
	 */
	public void setUrl( String mUrl )
	{
		this.mUrl = mUrl;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getComment()
	{
		return mComment;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mComment
	 */
	public void setComment( String mComment )
	{
		this.mComment = mComment;
	}
}
