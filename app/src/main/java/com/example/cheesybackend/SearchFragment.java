package com.example.cheesybackend;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerview_tasks);
//        getRestaurants();
        return rootView;

    }
//    private void getRestaurants() {
//        class getRestaurants extends AsyncTask<Void, Void, List<Restaurant>> {
//
//            @Override
//            protected List<Restaurant> doInBackground(Void... voids) {
//                List<Restaurant> noteList = DatabaseClient
//                        .getInstance(getApplicationContext())
//                        .getAppDatabase()
//                        .noteOrgDAO()
//                        .getAll();
//                return noteList;
//            }
//
//            @Override
//            protected void onPostExecute(List<Restaurant> nts) {
//                super.onPostExecute(nts);
//                RestrauntOrgAdapter adapter = new RestrauntOrgAdapter(getActivity(), nts);
//                recyclerView.setAdapter(adapter);
//            }
//        }
//
//        getRestaurants gt = new getRestaurants();
//        gt.execute();
//    }
}