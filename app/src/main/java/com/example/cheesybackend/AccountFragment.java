package com.example.cheesybackend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private Button pay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);

    }
//    public void Pay(View view){
//        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
//                .findFragmentById(R.id.nav_host_fragment);
//        navHostFragment.findNavController(this).navigate(R.id.action_a_to_b);
//
//    }

}