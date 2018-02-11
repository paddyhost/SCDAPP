package com.hatchers.solar_conduction_dryer.activity.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchers.solar_conduction_dryer.R;

/**
 * Created by Nikam on 13/11/2017.
 */
public class Menu_Adapter  extends BaseAdapter {

    Context context;


    public Integer[] menu_images = {
            R.drawable.introduction2, R.drawable.parts,
            R.drawable.assembly, R.drawable.loading_tray,
            R.drawable.weather, R.drawable.rent_plus
    };

    public Integer[] colors = {
            R.color.intro, R.color.parts,
            R.color.assembly, R.color.loading,
            R.color.weather_greed, R.color.care
    };



    @Override
    public int getCount() {

        return menu_images.length;
    }

    @Override
    public Object getItem(int position) {

        //return position;
        return menu_images[position];
    }

    @Override
    public long getItemId(int position) {

       // return position;
        return 0;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }


   /* public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        final SCD_Menu menus = menuslist.get(position);

        rowView = inflater.inflate(R.layout.menu_card, null);
        holder.tv = (TextView) rowView.findViewById(R.id.txt_title);
        holder.img = (ImageView) rowView.findViewById(R.id.icon);

        holder.tv.setText(menus.getName());
        holder.img.setImageResource(menus.getIcon());

        return rowView;
    }
*/
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_row,null);
        view.setBackgroundColor(context.getResources().getColor(colors[position]));
        ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
        TextView textView=(TextView)view.findViewById(R.id.txt_one);
        imageView.setImageResource(  menu_images[position]);


        return view;
    }
}