package com.example.news_api_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
//custom ArrayAdapter
public class Adapter extends ArrayAdapter<item> {
    public Adapter( Context context, int resource,List<item> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        item t=getItem(position);
        View v=convertView;
        if(v == null) {
            v = LayoutInflater.from(getContext()).inflate(
                    R.layout.item, parent, false);
        }
        ImageView img=v.findViewById(R.id.img);
        TextView txt=v.findViewById(R.id.name);
        TextView date=v.findViewById(R.id.date);
        txt.setText(t.gettitle());
        date.setText(t.getPublishedAt());

        //loading img using picasso libary for deatils visit : http://square.github.io/picasso/
        //t.getImg() the url of image
        //placeholder is temporary image until image loading is complete
        Picasso.get().load(t.getImg()).placeholder(R.mipmap.image_load).into(img);

        return v;
    }
}
