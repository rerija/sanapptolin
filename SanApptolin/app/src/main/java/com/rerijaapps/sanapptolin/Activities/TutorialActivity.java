package com.rerijaapps.sanapptolin.Activities;

import java.util.ArrayList;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;
import com.rerijaapps.sanapptolin.Storage.PreferencesManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

/**
 * Pantalla con el tutorial inicial.
 *
 * Created by jreci on 09/11/2016.
 */
public class TutorialActivity extends AppCompatActivity
{

	private static final int REQUEST_CODE = 1234;

	/**
	 * {@inheritDoc}
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		loadTutorial();
	}

	/**
	 * Carga el tutorial de la App.
	 */
	public void loadTutorial()
	{
		Intent mainAct = new Intent( this , AppTutorialActivity.class );
		mainAct.putParcelableArrayListExtra( MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems() );
		startActivityForResult( mainAct, REQUEST_CODE );

	}

	/**
	 * Consigue los items del tutorial.
	 *
	 * @return Items del tutorial.
	 */
	private ArrayList<TutorialItem> getTutorialItems()
	{
		TutorialItem tutorialItem1 = new TutorialItem( R.string.tutorial_title1 , R.string.tutorial_text1 , R.color.tutorialColor1 , R.drawable.tut_page_1_bg , 0 );
		TutorialItem tutorialItem2 = new TutorialItem( R.string.tutorial_title2 , R.string.tutorial_text2 , R.color.tutorialColor2 , R.drawable.tut_page_2_bg ,
				R.drawable.tut_page_2_front );
		TutorialItem tutorialItem3 = new TutorialItem( R.string.tutorial_title3 , R.string.tutorial_text3 , R.color.tutorialColor3 , R.drawable.tut_page_3_bg , 0 );
		TutorialItem tutorialItem4 = new TutorialItem( R.string.tutorial_title4 , R.string.tutorial_text4 , R.color.tutorialColor4 , R.drawable.tut_page_4_bg , 0 );

		ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
		tutorialItems.add( tutorialItem1 );
		tutorialItems.add( tutorialItem2 );
		tutorialItems.add( tutorialItem3 );
		tutorialItems.add( tutorialItem4 );
		return tutorialItems;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		if ( resultCode == RESULT_OK && requestCode == REQUEST_CODE )
		{
			PreferencesManager.setBoolean( Constants.PREFERENCE_NAME_SHOW_TUTORIAL, true );
			MainActivity_.intent( this ).start();
			finish();
		}
	}

}
