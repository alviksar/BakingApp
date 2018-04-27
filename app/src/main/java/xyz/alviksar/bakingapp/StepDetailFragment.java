package xyz.alviksar.bakingapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A fragment representing a video and description for a step.
 */
public class StepDetailFragment extends Fragment {

    private static final String ARG_VIDEO_URL = "videoURL";
    private static final String ARG_DESCRIPTION = "description";

    private static final String BUNDLE_PLAYBACK_POSITION = "playback_position";
    private static final String BUNDLE_CURRENT_WINDOW_INDEX = "current_window_index";
    private static final String BUNDLE_PLAY_WHEN_READY_STATE = "bundle_play_when_ready_state";

    private String mVideoUrl;
    private String mDescription;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;

    private boolean mPlayWhenReadyState = true;
    private long mPlaybackPosition = C.TIME_UNSET;
    private int mCurrentWindow;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param videoUrl    Parameter 1.
     * @param description Parameter 2.
     * @return A new instance of fragment StepDetailFragment.
     */
    public static StepDetailFragment newInstance(String videoUrl, String description) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVideoUrl = getArguments().getString(ARG_VIDEO_URL);
            setDescription(getArguments().getString(ARG_DESCRIPTION));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(BUNDLE_PLAYBACK_POSITION, C.TIME_UNSET);
            mCurrentWindow = savedInstanceState.getInt(BUNDLE_CURRENT_WINDOW_INDEX, 0);
            mPlayWhenReadyState
                    = savedInstanceState.getBoolean(BUNDLE_PLAY_WHEN_READY_STATE, true);
        }
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        TextView descriptionTextView = rootView.findViewById(R.id.tv_step_description);
        descriptionTextView.setText(getDescription());
        // Bind the player to the view.
        mPlayerView = rootView.findViewById(R.id.pv_video);
        if (mVideoUrl != null && !TextUtils.isEmpty(mVideoUrl)) {
            mPlayerView.setVisibility(View.VISIBLE);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putLong(BUNDLE_PLAYBACK_POSITION, mPlaybackPosition);
        outState.putInt(BUNDLE_CURRENT_WINDOW_INDEX, mCurrentWindow);
        outState.putBoolean(BUNDLE_PLAY_WHEN_READY_STATE, mPlayWhenReadyState);

        super.onSaveInstanceState(outState);
    }


    /**
     * Initializes ExoPlayer.
     */
    private void initializePlayer() {
        if (mPlayer == null) {
            if (mVideoUrl != null && !TextUtils.isEmpty(mVideoUrl)) {

                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                mPlayerView.setPlayer(mPlayer);

                // Produces DataSource instances through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                        Util.getUserAgent(getContext(), getString(R.string.app_name)), null);

                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ExtractorMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mVideoUrl));
                mPlayer.prepare(videoSource);

                mPlayer.setPlayWhenReady(mPlayWhenReadyState);
                if (mPlaybackPosition != C.TIME_UNSET)
                    mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
            }
        }
    }

    private void getPlaybackState() {
        mPlayWhenReadyState = mPlayer.getPlayWhenReady();
        if (mPlayer.getPlaybackState() == Player.STATE_ENDED)
            mPlaybackPosition = 0;
        else
            mPlaybackPosition = mPlayer.getCurrentPosition();
        mCurrentWindow = mPlayer.getCurrentWindowIndex();
    }

    /**
     * Releases player.
     */
    private void releasePlayer() {
        if (mPlayer != null) {
            getPlaybackState();
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        /*
        Show full screen video on landscape oriented phone only
        https://stackoverflow.com/a/9308284/9682456
         */
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (!isTablet &&  getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi();
        }
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}
