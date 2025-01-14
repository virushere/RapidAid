package com.example.rapidaid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class PoliceHome extends Fragment {

    VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_police_home, container, false);

        videoView = view.findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://"+getActivity().getPackageName()+"/"+R.raw.video);

        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        return view;
    }
}