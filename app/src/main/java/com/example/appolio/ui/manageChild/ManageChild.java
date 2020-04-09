package com.example.appolio.ui.manageChild;

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
import android.widget.Switch;

import com.example.appolio.AddChildRecord;
import com.example.appolio.DeleteChildRecord;
import com.example.appolio.R;
import com.example.appolio.UpdateChildRecord;

public class ManageChild extends Fragment implements View.OnClickListener {

    private ManageChildViewModel mViewModel;

    public static ManageChild newInstance() {
        return new ManageChild();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manage_child_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ManageChildViewModel.class);
        // TODO: Use the ViewModel
        Button addChildRecord= getActivity().findViewById(R.id.addRecordButton);
        addChildRecord.setOnClickListener(this);

        Button updateChildRecord= getActivity().findViewById(R.id.UpdateChildReordButton);
        updateChildRecord.setOnClickListener(this);

        Button deleteChildRecord= getActivity().findViewById(R.id.deleteChildRecordButton);
        deleteChildRecord.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addRecordButton:
            Intent toAddChildRecActivity = new Intent(getActivity(), AddChildRecord.class);
            startActivity(toAddChildRecActivity);
            break;

            case R.id.UpdateChildReordButton:
                Intent toUpdateChildRecordActivity = new Intent(getActivity(), UpdateChildRecord.class);
            startActivity(toUpdateChildRecordActivity);
            break;

            case R.id.deleteChildRecordButton:
                Intent toDeleteChildRecordActivity = new Intent(getActivity(), DeleteChildRecord.class);
            startActivity(toDeleteChildRecordActivity);
            break;
        }

    }

}