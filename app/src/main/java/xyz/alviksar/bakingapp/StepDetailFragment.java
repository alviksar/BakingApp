package xyz.alviksar.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment {

    private static final String ARG_VIDEO_URL = "videoURL";
    private static final String ARG_DESCRIPTION = "description";

    private static final String PLAYBACK_POSITION = "playback_position";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";

    private String mVideoUrl;
    private String mDescription;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;

    long mPlaybackPosition;
    private int mCurrentWindow;

    private OnFragmentInteractionListener mListener;

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
            setmDescription(getArguments().getString(ARG_DESCRIPTION));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
        }
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        TextView descriptionTextView = rootView.findViewById(R.id.tv_step_description);
        descriptionTextView.setText(getmDescription());
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

        outState.putLong(PLAYBACK_POSITION, mPlaybackPosition);
        outState.putInt(CURRENT_WINDOW_INDEX, mCurrentWindow);

        super.onSaveInstanceState(outState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Initialize ExoPlayer.
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
                mPlayer.setPlayWhenReady(true);
                mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
            }
        }
    }
    private void getPlaybackPosition() {
        //   mPlayer.stop();
        if (mPlayer.getPlaybackState() == Player.STATE_ENDED)
            mPlaybackPosition = 0;
        else
            mPlaybackPosition = mPlayer.getCurrentPosition();
        mCurrentWindow = mPlayer.getCurrentWindowIndex();
    }

    /**
     * Release player.
     */
    private void releasePlayer() {
        if (mPlayer != null) {
            getPlaybackPosition();
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
