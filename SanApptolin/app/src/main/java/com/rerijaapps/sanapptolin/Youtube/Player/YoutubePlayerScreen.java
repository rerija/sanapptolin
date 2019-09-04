package com.rerijaapps.sanapptolin.Youtube.Player;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rerijaapps.sanapptolin.R;
import com.rerijaapps.sanapptolin.Storage.Constants;

/**
 * Pantalla del player de Youtube.
 */
@EActivity( R.layout.youtube_player_screen )
public class YoutubePlayerScreen extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{

	/**
	 * Extra para el identificador del video.
	 */
	@Extra
	public String mVideoId;

	/**
	 * Player de Youtube.
	 */
	@ViewById( R.id.youtube_player_view )
	public YouTubePlayerView mYoutubePlayerView;

	/**
	 * Evento que se ejecuta tras inicializar las vistas.
	 */
	@AfterViews
	public void afterViews()
	{
		mYoutubePlayerView.initialize( Constants.YOUTUBE_API_KEY, this );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param provider
	 * @param youTubePlayer
	 * @param wasRestored
	 */
	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored )
	{
		if ( null != mVideoId && !wasRestored )
		{
			youTubePlayer.setFullscreen( true );
			youTubePlayer.setShowFullscreenButton( false );
			youTubePlayer.loadVideo( mVideoId );
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param provider
	 * @param youTubeInitializationResult
	 */
	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult )
	{

	}
}
