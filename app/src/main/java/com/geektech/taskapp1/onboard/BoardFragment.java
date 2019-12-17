package com.geektech.taskapp1.onboard;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geektech.taskapp1.MainActivity;
import com.geektech.taskapp1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        int pos = getArguments().getInt("pos");
        TextView textView = view.findViewById(R.id.text_view);
        ImageView imageView = view.findViewById(R.id.imageView);
        Button button = view.findViewById(R.id.btn_start);
        LinearLayout frg = view.findViewById(R.id.frg_board);

        switch (pos) {
            case 0:
                button.setVisibility(View.INVISIBLE);
                textView.setText("Привет");
                imageView.setImageResource(R.drawable.pic1);
                frg.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:

                button.setVisibility(View.INVISIBLE);
                textView.setText("Как дела?");
                imageView.setImageResource(R.drawable.pic2);
                frg.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));

                break;
            case 2:
                button.setVisibility(View.VISIBLE);
                textView.setText("Что делаешь?");
                imageView.setImageResource(R.drawable.pic3);
                frg.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));

                break;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("isShown", true).apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
