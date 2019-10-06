package com.example.dictionary.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.model.WordRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private final int REQUEST_CODE_FOR_CHANGE_FRAGMENT = 1;
    private final String CHANGE_DIALOG_FRAGMENT_TAG = "com.example.dictionary.fragments.changedialogfragment";
    private final String DIALOG_FRAGMENT_TAG = "com.example.completetask.fragments.dialogFragmentTag";
    private final int REQUEST_FOR_DIALOGFRAGMENT = 0;
    private FloatingActionButton mFloatingActionButton;
    private List<Word> mWordList;
    private Word mWord;
    private RecyclerView mRecyclerView;
    private WordAdaptor mWordAdaptor;
    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_fragment_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {
            case R.id.menue_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        creatRecycler();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLogFragment diaLogFragment = DiaLogFragment.newInstance();
                diaLogFragment.setTargetFragment(MainFragment.this, REQUEST_FOR_DIALOGFRAGMENT);
                diaLogFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });

        return view;
    }
    private void init(View view) {
        mFloatingActionButton = view.findViewById(R.id.main_fab);
        mRecyclerView = view.findViewById(R.id.main_recycler);

    }
    private class WordHolder extends RecyclerView.ViewHolder {
        private TextView mItemTitleTextView;
        private TextView mItemShapeTextView;
        private TextView mItemPersianTextView;
        private Word mWord;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            mItemTitleTextView = itemView.findViewById(R.id.item_title);
            mItemShapeTextView = itemView.findViewById(R.id.item_shape_text);
            mItemPersianTextView=itemView.findViewById(R.id.item_persian);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeDialogFragment changeDialogFragment = ChangeDialogFragment.newInstance(
                             mWord.getEnglishNAME(),mWord.getPersianNAME(),mWord.getUUID());
                    changeDialogFragment.setTargetFragment(MainFragment.this, REQUEST_CODE_FOR_CHANGE_FRAGMENT);
                    changeDialogFragment.show(getFragmentManager(), CHANGE_DIALOG_FRAGMENT_TAG);

                }
            });
        }

        public void bind(Word word) {
            Character shapeText = word.getEnglishNAME().charAt(0);
            String stringForShapeText = shapeText.toString();
            mItemPersianTextView.setText(word.getPersianNAME());
            mItemTitleTextView.setText(word.getEnglishNAME());
            mItemShapeTextView.setText(stringForShapeText);
            mWord = word;
        }
    }

    private class WordAdaptor extends RecyclerView.Adapter<WordHolder> {
        private List<Word> mWordList;

        public WordAdaptor(List<Word> wordList) {
            mWordList = wordList;
        }

        @NonNull
        @Override
        public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, parent, false);
            WordHolder wordHolder = new WordHolder(view);
            return wordHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WordHolder holder, int position) {
            holder.bind(mWordList.get(position));
        }

        @Override
        public int getItemCount() {
            if (mWordList.size() != 0)
                return mWordList.size();


            return 0;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String[] valueFromDiaLogFragment;
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_FOR_DIALOGFRAGMENT) {
            valueFromDiaLogFragment = data.getStringArrayExtra(DiaLogFragment.getTitleAndDiscription());
            setToDoClass(valueFromDiaLogFragment);
        }
        if (requestCode == REQUEST_CODE_FOR_CHANGE_FRAGMENT) {
            creatRecycler();
        }
    }

    private void setToDoClass(String[] valueFromDiaLogFragment) {
       mWord = new Word();
        if (valueFromDiaLogFragment != null) {
            mWord.setEnglishNAME(valueFromDiaLogFragment[0]);
            mWord.setPersianNAME(valueFromDiaLogFragment[1]);
        }
        WordRepository.getInstance(getContext()).insertWord(mWord);
        creatRecycler();
    }

    private void creatRecycler() {
        mWordList = WordRepository.getInstance(getContext()).getWords();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordAdaptor = new WordAdaptor(mWordList);
        mRecyclerView.setAdapter(mWordAdaptor);
    }
}
