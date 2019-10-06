package com.example.dictionary.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.model.WordRepository;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeDialogFragment extends DiaLogFragment {
    private static final String ENGLISH_FROM_FRAGMENTS = "com.example.dictionary.fragments.titleFromToDoFragment";
    private static final String PERSIAN_FROM_FRAGMENTS = "com.example.dictionary.fragments.discriptionFromToDoFragment";
    private static final String ID_OF_TASKS = "com.example.dictionary.fragments.indexoffragments";
    private EditText mEnglishEditText;
    private EditText mPersianEditText;
    private Word mWord;
    private Button mSaveButton;
    private Button mEditButton;
    private Button mDeleteButton;
    public static ChangeDialogFragment newInstance(String englishNameFromFragments, String persianNameFromFragments
    ,UUID idOfTasks) {

        Bundle args = new Bundle();
        args.putString(ENGLISH_FROM_FRAGMENTS, englishNameFromFragments);
        args.putString(PERSIAN_FROM_FRAGMENTS, persianNameFromFragments);
        args.putSerializable(ID_OF_TASKS, idOfTasks);
        ChangeDialogFragment fragment = new ChangeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public ChangeDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        UUID idOfTasks = (UUID) getArguments().getSerializable(ID_OF_TASKS);
           mWord = WordRepository.getInstance(getContext()).getWord(idOfTasks);
        mEnglishEditText.setText(getArguments().getString(ENGLISH_FROM_FRAGMENTS));
        mPersianEditText.setText(getArguments().getString(PERSIAN_FROM_FRAGMENTS));
        mEnglishEditText.setEnabled(false);
        mPersianEditText.setEnabled(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_change_dialog, null, false);
        init(view);
        final AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        mEnglishEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWord.setEnglishNAME(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPersianEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWord.setPersianNAME(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnglishEditText.setEnabled(true);
               mPersianEditText.setEnabled(true);
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String englishEditText = mEnglishEditText.getText().toString();
                String persianEditText =mPersianEditText.getText().toString();
                if (englishEditText.equals("") || persianEditText.equals("")) {
                    Toast.makeText(getActivity(), "You Must Complete Eeach Field!", Toast.LENGTH_LONG).show();
                } else {
                    setObjectsOfFragments();
                    sendResult();
                }

                alertDialog.dismiss();
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    deleteReposiory();
                    sendResult();
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }
    private void setObjectsOfFragments() {
            updateReposiory();
    }

    private void init(View view) {
       mEnglishEditText = view.findViewById(R.id.change_dialog_english_edittext);
        mPersianEditText = view.findViewById(R.id.change_dialog_persian_edittext);
        mSaveButton = view.findViewById(R.id.change_dialog_button_save);
        mEditButton = view.findViewById(R.id.change_dialog_button_edit);
        mDeleteButton = view.findViewById(R.id.change_dialog_button_delete);
    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    private void deleteReposiory() {
                try {
                WordRepository.getInstance(getContext()).deleteWord(mWord);
            } catch (Exception e) {
            }
    }

    private void updateReposiory() {

        try {
           WordRepository.getInstance(getContext()).updateWord(mWord);
        } catch (Exception e) {
        }
    }
}
