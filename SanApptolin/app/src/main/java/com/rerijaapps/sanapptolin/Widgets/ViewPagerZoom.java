package com.rerijaapps.sanapptolin.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager que soporta correctamente el Zoom.
 *
 * Created by javierrecio on 8/6/17.
 */
public class ViewPagerZoom extends android.support.v4.view.ViewPager
{

	/**
	 * {@inheritDoc}
	 *
	 * @param context
	 */
	public ViewPagerZoom( Context context )
	{
		super( context );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param context
	 * @param attrs
	 */
	public ViewPagerZoom( Context context, AttributeSet attrs )
	{
		super( context, attrs );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onTouchEvent( MotionEvent ev )
	{
		try
		{
			return super.onTouchEvent( ev );
		}
		catch ( IllegalArgumentException ex )
		{
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onInterceptTouchEvent( MotionEvent ev )
	{
		try
		{
			return super.onInterceptTouchEvent( ev );
		}
		catch ( IllegalArgumentException ex )
		{
			ex.printStackTrace();
		}
		return false;
	}
}
