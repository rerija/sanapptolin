package com.rerijaapps.sanapptolin.Interfaces;

/**
 * Listener para el Statys del AudioPlayer.
 *
 * Created by jreci on 16/10/2017.
 */
public interface IOnAudioPlayerStatusListener
{

	/**
	 * Evento que se ejecuta cuando se llama al listenerPlay.
	 */
	void listenerPlay();

	/**
	 * Evento que se ejecuta cuando se llama al listenerPause.
	 */
	void listenerPause();

}
