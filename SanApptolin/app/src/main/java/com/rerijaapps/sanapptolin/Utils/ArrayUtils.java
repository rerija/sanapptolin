package com.rerijaapps.sanapptolin.Utils;

import java.util.List;

public class ArrayUtils
{
	public static boolean isNullOrEmpty( List list )
	{
		return ! ( list != null && list.size() > 0 );
	}

	/**
	 * Indica si un array contiene un valor.
	 *
	 * @param array - Array.
	 * @param value - Valor.
	 * @return Si contiene ese valor.
	 */
	public static boolean containsValue(String[] array, String value )
	{
		if ( null != array && null != value )
		{
			for ( String val : array )
			{
				if ( val.equals( value ) )
				{
					return true;
				}
			}
		}
		return false;
	}
}