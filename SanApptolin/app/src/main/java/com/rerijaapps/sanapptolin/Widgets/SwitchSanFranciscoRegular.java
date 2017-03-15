package com.rerijaapps.sanapptolin.Widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Switch;

/**
 * Textview con font San Francisco.
 */
public class SwitchSanFranciscoRegular extends Switch
{

	/**
	 * Typeface
	 */
	private static Typeface tfAntennaRegular;

	/**
	 * {@inheritDoc}
	 *
	 * @param context
	 */
	public SwitchSanFranciscoRegular( Context context )
	{
		super( context );
		init( context );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param context
	 * @param attrs
	 */
	public SwitchSanFranciscoRegular( Context context, AttributeSet attrs )
	{
		super( context, attrs );
		init( context );
	}

	/**
	 * Inicializar
	 * 
	 * @param context
	 */
	private void init( Context context )
	{
		if ( !isInEditMode() )
		{
			if ( null == tfAntennaRegular )
			{
				tfAntennaRegular = Typeface.createFromAsset( context.getAssets(), "fonts/san_francisco_regular.ttf" );
			}
			setTypeface( tfAntennaRegular );
		}
	}
}
