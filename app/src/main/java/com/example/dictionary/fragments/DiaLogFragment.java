package com.example.dictionary.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictionary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaLogFragment extends DialogFragment {
    private EditText mNameEditText;
    private EditText mDiscriptionEditText;
    private static final String TITLE_AND_DISCRIPTION = "com.example.dictionary.fragments.title and discription";
    public static DiaLogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DiaLogFragment fragment = new DiaLogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public DiaLogFragment() {
        // Required empty public constructor
    }

    public static String getTitleAndDiscription() {
        return TITLE_AND_DISCRIPTION;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dia_log, null, false);
        init(view);
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nameEditText = mNameEditText.getText().toString();
                        String discriptionEditText = mDiscriptionEditText.getText().toString();
                        if (nameEditText.equals("") || discriptionEditText.equals("")) {
                            Toast.makeText(getActivity(), "You Must Complete Eeach Field!", Toast.LENGTH_LONG).show();
                        } else {
                            String[] forExtra = {nameEditText, discriptionEditText};
                            if (!nameEditText.equals("") && !discriptionEditText.equals("")) {
                                sendResult(forExtra);
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
                .setView(view)
                .create();

    }
    private void init(View view) {
        mNameEditText = view.findViewById(R.id.dialog_title);
        mDiscriptionEditText = view.findViewById(R.id.dialog_discription);
    }
    private void sendResult(String[] forExtra) {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(TITLE_AND_DISCRIPTION, forExtra);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

}
