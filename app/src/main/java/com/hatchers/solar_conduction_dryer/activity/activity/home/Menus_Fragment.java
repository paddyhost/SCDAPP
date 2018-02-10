package com.hatchers.solar_conduction_dryer.activity.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hatchers.solar_conduction_dryer.R;
import com.hatchers.solar_conduction_dryer.activity.Preference_Manager.PrefManager;
import com.hatchers.solar_conduction_dryer.activity.profile.Profile_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.assembly_procedure.Assembly_Procedure_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.rent.Rent_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.introduction.Introduction_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.loading_tray.LoadingTray_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.parts.SCD_Parts_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.whether.Whether_Fragment;
import com.hatchers.solar_conduction_dryer.activity.user_registration.UserRegistration;

public class Menus_Fragment extends Fragment implements
        ViewPagerEx.OnPageChangeListener,View.OnClickListener
{

    private ImageView imageIntro,imagePart,imageAssembly,imageLoading,imageWhether,imageCare;
    private Toolbar toolbar;
    private  FragmentTransaction fragmentTransaction;
    private PrefManager prefManager;

    public Menus_Fragment() {

        // Required empty public constructor
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_menu, container, false);

        setHasOptionsMenu(true);

        prefManager=new PrefManager(getActivity());
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        imageIntro = (ImageView) view.findViewById(R.id.image_intro);
        imagePart =(ImageView)view.findViewById(R.id.image_part);
        imageAssembly =(ImageView)view.findViewById(R.id.image_assembly);
        imageLoading =(ImageView)view.findViewById(R.id.image_loading);
        imageWhether =(ImageView)view.findViewById(R.id.image_whether);
        imageCare =(ImageView)view.findViewById(R.id.image_care);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        imageIntro.setOnClickListener(this);
        imagePart.setOnClickListener(this);
        imageAssembly.setOnClickListener(this);
        imageLoading.setOnClickListener(this);
        imageWhether.setOnClickListener(this);
        imageCare.setOnClickListener(this);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.care));
        }

        return view;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.image_intro:
                Introduction_Fragment introductionFragment = new Introduction_Fragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setSharedElementReturnTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.change_image_trans));
                    setExitTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.fade));

                    introductionFragment.setSharedElementEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.change_image_trans));
                    introductionFragment.setEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.fade));
                }

                fragmentTransaction.replace(R.id.frame,introductionFragment)
                        .addSharedElement(imageIntro, getString(R.string.simple_fragment_transition))
                        .addToBackStack(null).commit();
                break;
            case R.id.image_part:
                SCD_Parts_Fragment scdPartsFragment = new SCD_Parts_Fragment();
                fragmentTransaction.replace(R.id.frame,scdPartsFragment ).addToBackStack(null).commit();
                break;
            case R.id.image_assembly:
                Assembly_Procedure_Fragment assemblyProcedureFragment = new Assembly_Procedure_Fragment();
                fragmentTransaction.replace(R.id.frame,assemblyProcedureFragment).addToBackStack(null).commit();
                break;
            case R.id.image_loading:
                LoadingTray_Fragment loadingTrayFragment = new LoadingTray_Fragment();
                fragmentTransaction.replace(R.id.frame,loadingTrayFragment).addToBackStack(null).commit();
                break;
            case R.id.image_whether:
                Whether_Fragment whetherFragment = new Whether_Fragment();
                fragmentTransaction.replace(R.id.frame,whetherFragment).addToBackStack(null).commit();
                break;
            case R.id.image_care:
                Rent_Fragment careFragment = new Rent_Fragment();
                fragmentTransaction.replace(R.id.frame,careFragment).addToBackStack(null).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menus,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.profile:
                if(prefManager.isSkippedRegistration())
                {
                    Intent intent = new Intent(getActivity(), UserRegistration.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    Profile_Fragment profileFragment = new Profile_Fragment();
                    fragmentTransaction.replace(R.id.frame,profileFragment).addToBackStack(null).commit();
                }

                break;

            case R.id.logout:
                prefManager.setLoggedOut();
                Intent intent=new Intent(getActivity(),UserRegistration.class);
                startActivity(intent);
                getActivity().finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
