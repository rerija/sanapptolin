package com.rerijaapps.sanapptolin.Activities;

import java.util.ArrayList;
import java.util.List;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Storage.PreferencesManager;

import android.os.Bundle;

/**
 * Created by jreci on 09/11/2016.
 */
public class TutorialActivity extends AhoyOnboarderActivity
{

	/**
	 * {@inheritDoc}
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard( R.string.tutorial_title1 , R.string.tutorial_text1 , R.drawable.analytics_icon );
		ahoyOnboarderCard1.setBackgroundColor( R.color.black_transparent );
		AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard( R.string.tutorial_title2 , R.string.tutorial_text2 , R.drawable.checklist_icon );
		ahoyOnboarderCard2.setBackgroundColor( R.color.black_transparent );
		AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard( R.string.tutorial_title3 , R.string.tutorial_text3 , R.drawable.camera_front_icon );
		ahoyOnboarderCard3.setBackgroundColor( R.color.black_transparent );
		AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard( R.string.tutorial_title4 , R.string.tutorial_text4 , R.drawable.baby_mobile_icon );
		ahoyOnboarderCard4.setBackgroundColor( R.color.black_transparent );
		List<AhoyOnboarderCard> pages = new ArrayList<>();
		pages.add( ahoyOnboarderCard1 );
		pages.add( ahoyOnboarderCard2 );
		pages.add( ahoyOnboarderCard3 );
		pages.add( ahoyOnboarderCard4 );
		for ( AhoyOnboarderCard page : pages )
		{
			page.setTitleColor( R.color.white );
			page.setDescriptionColor( R.color.grey_200 );
		}
		setFinishButtonTitle( R.string.tutorial_finish );
		showNavigationControls( true );
		setGradientBackground();
		setOnboardPages( pages );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onFinishButtonPressed()
	{
		PreferencesManager.setBoolean( Constants.PREFERENCE_NAME_SHOW_TUTORIAL, true );
		MainActivity_.intent( this ).start();
		finish();
	}
}
