package com.rerijaapps.sanapptolin.Storage;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;

/**
 * Constantes.
 *
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
	public static final String CLASS_PARSE_COLUMN_CREATED_AT = "createdAt";
	public static final String CLASS_APP_STATE_NAME = "AppState";
	public static final String CLASS_APP_STATE_COLUMN_ACTIVE_NAME = "Active";
	public static final String CLASS_APP_STATE_COLUMN_APPNAME_NAME = "AppName";
	public static final String CLASS_APP_STATE_COLUMN_APPIMAGE_NAME = "AppImage";
	public static final String CLASS_APP_STATE_COLUMN_RADIO_STREAMING_URL = "RadioStreamingUrl";
	public static final String CLASS_APP_DAYS_NAME = "Days";
	public static final String CLASS_APP_DAYS_COLUMN_DAYNAME_NAME = "DayName";
	public static final String CLASS_APP_DAYS_COLUMN_COLORDAY_NAME = "ColorDay";
	public static final String CLASS_IMAGES_NAME = "Images";
	public static final String CLASS_IMAGES_COLUMN_IMAGEDAY_NAME = "ImageDay";
	public static final String CLASS_IMAGES_COLUMN_IMAGE_NAME = "Image";
	public static final String CLASS_EVENT_NAME = "Event";
	public static final String CLASS_EVENT_COLUMN_DAY = "Day";
	public static final String CLASS_EVENT_COLUMN_HOUR = "Hour";
	public static final String CLASS_EVENT_COLUMN_DESCRIPTION = "Description";
	public static final String CLASS_DAY_IMAGES_NAME = "DayImages";
	public static final String CLASS_DAY_IMAGES_COLUMN_COMMENT = "Comment";
	public static final String CLASS_DAY_IMAGES_COLUMN_PHOTO = "Photo";
	public static final String CLASS_DAY_IMAGES_COLUMN_DAY = "Day";

	/**
	 * Identificador y nombre del channel para las notificaciones en android O.
	 */
	public static final String NOTIFICATION_CHANNEL_ID = "com.rerijaapps.sanapptolin.NOTIFICATION_CHANNEL_ID";
	public static final String NOTIFICATION_CHANNEL_NAME = "com.rerijaapps.sanapptolin.NOTIFICATION_CHANNEL_NAME";

	/**
	 * Constantes para definir los valores que se mantienen en la app obtenido de
	 * Parse.
	 */
	public static String PARSE_APPNAME = "";
	public static String PARSE_APPIMAGE = "";
	public static String PARSE_APPSONG_URL = "";
	public static List<ParseObject> PARSE_DAYS = new ArrayList<>();

}
