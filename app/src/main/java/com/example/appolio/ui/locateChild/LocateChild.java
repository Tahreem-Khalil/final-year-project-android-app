package com.example.appolio.ui.locateChild;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appolio.AddChildRecord;
import com.example.appolio.MapViewofChild;
import com.example.appolio.R;

public class LocateChild extends Fragment implements View.OnClickListener {

    private LocateChildViewModel mViewModel;

    public static LocateChild newInstance() {
        return new LocateChild();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.locate_child_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LocateChildViewModel.class);
        // TODO: Use the ViewModel

        Button searchChildOnMap=(Button) getActivity().findViewById(R.id.searchChildOnMapButton);
        searchChildOnMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent toSearchChildMapActivity=new Intent(getActivity(), MapViewofChild.class);
        startActivity(toSearchChildMapActivity);
    }
}
