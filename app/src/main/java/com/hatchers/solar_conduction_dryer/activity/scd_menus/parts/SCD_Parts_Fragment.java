package com.hatchers.solar_conduction_dryer.activity.scd_menus.parts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;

import com.hatchers.solar_conduction_dryer.R;

public class SCD_Parts_Fragment extends Fragment {

    private GridView gridView;
    private Toolbar toolbar;

    public SCD_Parts_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_scd__parts, container, false);

        gridView=(GridView)view.findViewById(R.id.gridView_menus);

        gridView.setAdapter(new Parts_Adapter(getContext()));

        initToolbar(view);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.parts1));
        }
        return view;
    }

    private void initToolbar(View view)
    {
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ImageButton btn_back=(ImageButton)view.findViewById(R.id.btn_back);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

}
