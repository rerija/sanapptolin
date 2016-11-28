package com.rerijaapps.sanapptolin.Serializable;

import java.io.Serializable;

/**
 * Created by user on 21/11/2016.
 */
public class DayInfo implements Serializable
{
	private static final long serialVersionUID = -1950718680359355593L;

	/**
	 * Contiene la imagen del dia.
	 */
	private byte[] mImageDay;

	/**
	 * Contiene el color del dia.
	 */
	private String mColorDay;

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public byte[] getImageDay()
	{
		return mImageDay;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mImageDay
	 */
	public void setImageDay( byte[] mImageDay )
	{
		this.mImageDay = mImageDay;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getColorDay()
	{
		return mColorDay;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mColorDay
	 */
	public void setColorDay( String mColorDay )
	{
		this.mColorDay = mColorDay;
	}
}
