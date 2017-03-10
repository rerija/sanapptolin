package com.rerijaapps.sanapptolin.Firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.rerijaapps.sanapptolin.Utils.LogUtils;

/**
 * Servicio que se llama al obtener el token de firebase.
 *
 * Created by jreci on 10/03/2017.
 */
public class FirebaseIIDService extends FirebaseInstanceIdService
{

	/**
	 * TAG.
	 */
	private final String TAG = "FirebaseIIDService";

	@Override
	public void onTokenRefresh()
	{
		LogUtils.i( TAG, "TOKEN: " + FirebaseInstanceId.getInstance().getToken() );
		super.onTokenRefresh();
	}
}
