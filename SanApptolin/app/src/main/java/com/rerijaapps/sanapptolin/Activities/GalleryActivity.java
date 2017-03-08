package com.rerijaapps.sanapptolin.Activities;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Adapter.GalleryAdapter;
import com.rerijaapps.sanapptolin.Serializable.DayInfo;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

/**
 * Activity de la galeria de fotos.
 *
 * Created by user on 09/11/2016.
 */
@EActivity ( R.layout.activity_gallery )
public class GalleryActivity extends AppCompatActivity
{

	/**
	 * Extra con el evento.
	 */
	@Extra ( "com.rerijaapps.sanapptolin.event" )
	public DayInfo mDayInfo;

	/**
	 * Grid para la galeria de las fotos.
	 */
	@ViewById ( R.id.gallery_recycler )
	public RecyclerView mGalleryRecycler;

	/**
	 * Inicializa las vistas de la pantalla.
	 */
	@AfterViews
	public void setupViews()
	{
		List<String> prueba = new ArrayList<>();
		prueba.add( "http://www.mostolesnegocios.com/wp-content/uploads/2016/02/web_viaje_carnaval.jpg" );
		prueba.add( "https://www.comparaonline.com.co/blog/wp-content/uploads/sites/4/2016/01/barranuilla-carnavales-del-mundo.png" );
		prueba.add( "http://cdne.diariocorreo.pe/thumbs/uploads/articles/images/cuidado-con-carnavales-delitos-jpg_604x0.jpg" );
		prueba.add( "http://todobus.movelia.es/wp-content/uploads/2016/01/carnaval-lisboa.jpg" );
		prueba.add( "http://revistahsm.com/wp-content/uploads/2015/02/carnaval-rio.jpg" );
		prueba.add( "http://labrujulaocioycultura.com/wp-content/uploads/2016/01/16335337878_2b41b14633_k.jpg" );
		mGalleryRecycler.setAdapter( new GalleryAdapter( this , prueba ) );
	}

}
