package com.rerijaapps.sanapptolin.Widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Textview con font San Francisco Bold.
 */
public class TextViewSanFranciscoBold extends TextView
{

	/**
	 * Typeface
	 */
	private static Typeface tfAntennaBold;

	/**
	 * {@inheritDoc}
	 *
	 * @param context
	 */
	public TextViewSanFranciscoBold(Context context )
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
	public TextViewSanFranciscoBold(Context context, AttributeSet attrs )
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
			if ( null == tfAntennaBold )
			{
				tfAntennaBold = Typeface.createFromAsset( context.getAssets(), "fonts/san_francisco_bold.ttf" );
			}
			setTypeface( tfAntennaBold );
		}
	}
}
