package com.sunny.sunnyday.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunny.sunnyday.Adapters.HistoryRecViewAdapter;
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.Model.History;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.databinding.FragmentHistoryBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private FragmentHistoryBinding fragmentHistoryBinding;
    private static HistoryRecViewAdapter adapter;
    private static RecyclerView recyclerView;
    private static ArrayList<History> histories;
    private SavedArticleDAO savedArticleDAO;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        View view = fragmentHistoryBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        savedArticleDAO = new SavedArticleDAO(getActivity());
        int numberOfColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        //gridLayoutManager.setReverseLayout(true);
        //gridLayoutManager.setStackFromEnd(true);
        fragmentHistoryBinding.HistoryRecyclerView.setLayoutManager(gridLayoutManager);
        fragmentHistoryBinding.HistoryRecyclerView.setHasFixedSize(true);
        fragmentHistoryBinding.HistoryRecyclerView.setItemAnimator(new DefaultItemAnimator());


        histories = new ArrayList<History>();

        histories.addAll(savedArticleDAO.getHistory());

        if(histories.size()>0){
          //  Toast.makeText(getActivity(),savedArticleDAO.getHistory().get(0).getHistoryStartDate(),Toast.LENGTH_LONG).show();
            adapter = new HistoryRecViewAdapter(histories,getActivity());
            fragmentHistoryBinding.HistoryRecyclerView.setAdapter(adapter);
        }
        else {
            Toast.makeText(getActivity(),"No history recorded yet",Toast.LENGTH_LONG).show();
        }



    }
}
