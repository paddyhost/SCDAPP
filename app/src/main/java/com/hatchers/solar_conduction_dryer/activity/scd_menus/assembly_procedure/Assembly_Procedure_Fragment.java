package com.hatchers.solar_conduction_dryer.activity.scd_menus.assembly_procedure;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchers.solar_conduction_dryer.R;


public class Assembly_Procedure_Fragment extends Fragment {

    private ViewPager viewPager;
    private AssemblyAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private ImageButton backBtn;
    private Toolbar toolbar;
    private Button btnSkip, btnNext;


    public Assembly_Procedure_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assembly__procedure, container, false);

        backBtn = (ImageButton) view.findViewById(R.id.btn_back);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);
        btnSkip = (Button)view.findViewById(R.id.btn_skip);
        btnNext = (Button)view.findViewById(R.id.btn_next);


        setHasOptionsMenu(true);
        initToolbar(view);
        clickListeners();


        layouts = new int[]{
                R.layout.step_one,
                R.layout.step_two,
                R.layout.step_three,
                R.layout.step_four,
                R.layout.step_five,
                R.layout.step_six,
                R.layout.step_seven,
                R.layout.step_eight,
                R.layout.step_nine

        };

        // adding bottom dots
        addBottomDots(0);

        myViewPagerAdapter = new AssemblyAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    getActivity().onBackPressed();
                }
            }
        });


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.brown));
        }

        return view;
    }
    private void initToolbar(View view)
    {
        toolbar = (Toolbar)view.findViewById(R.id.toolbar_main);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }


    private void clickListeners() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void addBottomDots(int currentPage) {

        try {
            dots = new TextView[layouts.length];

            int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(getContext());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(25);
                dots[i].setTextColor(colorsInactive[currentPage]);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive[currentPage]);

        }
        catch (Exception e)
        {

        }
        }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //private void launchHomeScreen() {}



    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.Done));
                btnSkip.setVisibility(View.VISIBLE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


      //View pager adapter

    public class AssemblyAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public AssemblyAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
