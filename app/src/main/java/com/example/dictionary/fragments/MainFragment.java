package com.example.dictionary.fragments;


import android.app.Activity;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.model.WordRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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
    private ImageView mBackgroundImageView;
    private RecyclerView mRecyclerView;
    private WordAdaptor mWordAdaptor;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView;
    private boolean mIsSubtitleVisible = false;
    public static final String KEY_IS_SUBTITLE_VISIBLE = "mIsSubtitleVisible";

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
        MenuItem searchItem = menu.findItem(R.id.menue_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    mWordAdaptor.getFilter().filter(newText);

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mWordAdaptor.getFilter().filter(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menue_search:
                return true;
            case R.id.delete_menu:
                ShowMsgDialog(getContext());
                return true;

        }
        updateSubtitle();
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSubtitle();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_IS_SUBTITLE_VISIBLE, mIsSubtitleVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            mIsSubtitleVisible = savedInstanceState.getBoolean(KEY_IS_SUBTITLE_VISIBLE);
        }
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
        mBackgroundImageView=view.findViewById(R.id.background_image);

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
            mItemPersianTextView = itemView.findViewById(R.id.item_persian);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeDialogFragment changeDialogFragment = ChangeDialogFragment.newInstance(
                            mWord.getEnglishNAME(), mWord.getPersianNAME(), mWord.getUUID());
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

    private class WordAdaptor extends RecyclerView.Adapter<WordHolder> implements Filterable {
        private List<Word> mWordList;
        private List<Word> mWordListFiltered;

        public WordAdaptor(List<Word> wordList) {
            mWordList = wordList;
            mWordListFiltered = wordList;
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
            holder.bind(mWordListFiltered.get(position));
        }

        @Override
        public int getItemCount() {
            if (mWordListFiltered.size() != 0)
                return mWordListFiltered.size();


            return 0;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mWordListFiltered = mWordList;
                    } else {
                        List<Word> filteredList = new ArrayList<>();
                        for (Word row : mWordList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getEnglishNAME().toLowerCase().contains(charString.toLowerCase()) || row.getPersianNAME().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        mWordListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mWordListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mWordListFiltered = (ArrayList<Word>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
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
        updateSubtitle();
    }

    private void setToDoClass(String[] valueFromDiaLogFragment) {
        mWord = new Word();
        if (valueFromDiaLogFragment != null) {
            mWord.setEnglishNAME(valueFromDiaLogFragment[0]);
            mWord.setPersianNAME(valueFromDiaLogFragment[1]);
        }
        WordRepository.getInstance(getContext()).insertWord(mWord);
        updateSubtitle();
        creatRecycler();
    }

    private void updateSubtitle() {
        int wordSize = WordRepository.getInstance(getContext()).getWords().size();
        String subtitle = "Words:" + wordSize;

        AppCompatActivity activity = (AppCompatActivity) getActivity();


        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void creatRecycler() {
        mWordList = WordRepository.getInstance(getContext()).getWords();
        if(mWordList.size()==0)
        {
            mBackgroundImageView.setVisibility(View.VISIBLE);
        }else
        {
            mBackgroundImageView.setVisibility(View.GONE);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordAdaptor = new WordAdaptor(mWordList);
        mRecyclerView.setAdapter(mWordAdaptor);
    }

    public void ShowMsgDialog(Context self) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(self);
        dlgAlert.setMessage("Are you Sure?");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    List<Word> mToDoList = WordRepository.getInstance(getContext()).getWords();
                    WordRepository.getInstance(getContext()).deleteWords(mToDoList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateSubtitle();
                creatRecycler();
            }
        });
        dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dlgAlert.show();
    }
}
