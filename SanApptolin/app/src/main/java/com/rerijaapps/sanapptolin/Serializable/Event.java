package com.rerijaapps.sanapptolin.Serializable;

import java.util.Date;

/**
 * Created by jreci on 28/11/2016.
 */
public class Event
{
	/**
	 * Hora del evento.
	 */
	private Date mHour;

	/**
	 * Descripcion del evento.
	 */
	private String mDescription;

	/**
	 * Constructor.
	 *
	 * @param mHour - Hora.
	 * @param mDescription - Descripcion.
	 */
	public Event( Date mHour, String mDescription )
	{
		this.mHour = mHour;
		this.mDescription = mDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public Date getHour()
	{
		return mHour;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param mHour
	 */
	public void setHour( Date mHour )
	{
		this.mHour = mHour;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	public String getDescription()
	{
		return mDescription;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param mDescription
	 */
	public void setDescription( String mDescription )
	{
		this.mDescription = mDescription;
	}
}
