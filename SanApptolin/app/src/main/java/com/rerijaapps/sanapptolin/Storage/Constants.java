package com.rerijaapps.sanapptolin.Storage;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;

/**
 * Created by jreci on 09/11/2016.
 */
public class Constants
{

	/**
	 * Identificadores del proyecto Parser.
	 */
	public static final String PARSE_APP_ID = "PkE8dNkjW6aGAK6F9hSDslPVtpNbOQekRmH0BRrp";
	public static final String PARSE_APP_CLIENT_KEY = "GcKnrjB9AyEdBzaMrissWIoWYwvfbItyNdqgBKvp";
	public static final String PARSE_HOST_URL = "https://parseapi.back4app.com/";

	/**
	 * Name de la preference para indicar si el user ha visto el tutorial o no.
	 */
	public static final String PREFERENCE_NAME_SHOW_TUTORIAL = "com.rerijaapps.sanapptolin.preference_name_show_tutorial";

	/**
	 * Indican que la app esta en modo programancion.
	 */
	public static final boolean DEBUG_MODE = true;

	/**
	 * PARSE VALUES.
	 */
	public static final String CLASS_APP_STATE_NAME = "AppState";
	public static final String CLASS_APP_STATE_COLUMN_ACTIVE_NAME = "Active";
	public static final String CLASS_APP_STATE_COLUMN_APPNAME_NAME = "AppName";
	public static final String CLASS_APP_DAYS_NAME = "Days";
	public static final String CLASS_APP_DAYS_COLUMN_DAYNAME_NAME = "DayName";
	public static final String CLASS_APP_DAYS_COLUMN_COLORDAY_NAME = "ColorDay";

	/**
	 * Constantes para definir los valores que se mantienen en la app obtenido
	 * de Parse.
	 */
	public static String PARSE_APPNAME = "";
	public static List<ParseObject> PARSE_DAYS = new ArrayList<>();

}
