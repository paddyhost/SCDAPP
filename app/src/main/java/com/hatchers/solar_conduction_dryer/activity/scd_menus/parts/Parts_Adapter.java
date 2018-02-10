package com.hatchers.solar_conduction_dryer.activity.scd_menus.parts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchers.solar_conduction_dryer.R;

/**
 * Created by Nikam on 24/11/2017.
 */
public class Parts_Adapter extends BaseAdapter {

    Context context;

    public Integer[] part_images = {
            R.mipmap.main_metal ,R.mipmap.central_black,
            R.mipmap.c_channels, R.mipmap.slider,
            R.mipmap.insects_protection, R.mipmap.top_hood,
            R.mipmap.pc_sheets, R.mipmap.four_scd_trays
    };



    public Parts_Adapter(Context context)
    {
        this.context=context;
    }
    @Override
    public int getCount() {
        return  part_images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return  part_images[position];
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.scd_partview,null);
        String[] part_array=context.getResources().getStringArray(R.array.Parts_Name);
        ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
        TextView textView=(TextView)view.findViewById(R.id.txt_one);
        imageView.setImageResource( part_images[position]);
        textView.setText(part_array[position]);

        return view;
        }
    }

