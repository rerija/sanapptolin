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
	private String mImageDay;

	/**
	 * Contiene el color del dia.
	 */
	private String mColorDay;

	/**
	 * Contiene el nombre del dia.
	 */
	private String mDayName;

	public String getDayName()
	{
		return mDayName;
	}

	public void setDayName( String mDayName )
	{
		this.mDayName = mDayName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getImageDay()
	{
		return mImageDay;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mImageDay
	 */
	public void setImageDay( String mImageDay )
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
