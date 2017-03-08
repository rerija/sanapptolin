package com.rerijaapps.sanapptolin.Activities;

import com.rerijaapps.sanapptolin.R;

import android.os.Bundle;
import android.widget.TextView;

import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

/**
 * Clase para modificar la pantalla de tutorial.
 *
 * Created by jreci on 08/03/2017.
 */
public class AppTutorialActivity extends MaterialTutorialActivity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		( ( TextView ) findViewById( za.co.riggaroo.materialhelptutorial.R.id.activity_help_skip_textview ) ).setText( R.string.skip );
		( ( TextView ) findViewById( za.co.riggaroo.materialhelptutorial.R.id.activity_tutorial_done ) ).setText( R.string.finalize );
	}
}
