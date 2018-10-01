package com.aequilibrium.assignment.transfarena.gallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.gallery.adapter.GalleryListAdapter;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryPageFragment extends Fragment {

    private static final String TRANSFORMERS_KEY = "TRANSFORMERS_KEY";

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.empty_team)
    TextView emptyTeam;

    public static GalleryPageFragment getInstance(ArrayList<Transformer> transformers) {
        GalleryPageFragment galleryPageFragment = new GalleryPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TRANSFORMERS_KEY, transformers);
        galleryPageFragment.setArguments(bundle);
        return galleryPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_list, container, false);
        ButterKnife.bind(this, view);
        setupList();
        return view;
    }

    private void setupList() {
        emptyTeam.setVisibility(View.GONE);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(new GalleryListAdapter(getContext(), getTransformers()));
    }

    private List<Transformer> getTransformers() {
        return getArguments() != null ? (List<Transformer>) getArguments().getSerializable(TRANSFORMERS_KEY) : Collections.emptyList();
    }
}
