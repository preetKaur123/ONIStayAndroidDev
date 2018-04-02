package com.example.codemaven3015.onistayandroiddev;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/25/2018.
 */
public class Card_layout extends RecyclerView.Adapter<Card_layout.ViewHolder> {
    private Context context;
    private  String fromWhere;
    private JSONArray list;



    public Card_layout(Context context,String fromWhere,JSONArray list) {
        this.context = context;
        this.fromWhere = fromWhere;
        this.list = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentItem;
        public RelativeLayout card_relativeLayout;
        public TextView premium_Textview, price_textView, save_textView, cost_textView, hotel_textView, address_textView, rate_textView, rating_textView;
        ImageButton fav_imageBtn, show_imageBtn, rating_imgbtn;
        RelativeLayout imgBg_relativeLayout;
        ImageView hotel_image;
        Button wish_butn;
        public ViewHolder(View itemView) {
            super(itemView);

            imgBg_relativeLayout=(RelativeLayout)itemView.findViewById(R.id.imgBg_relativeLayout) ;
            wish_butn=itemView.findViewById(R.id.wish_butn);
            premium_Textview = (TextView) itemView.findViewById(R.id.premium_Textview);
            price_textView = (TextView) itemView.findViewById(R.id.price_textView);
            save_textView = (TextView) itemView.findViewById(R.id.save_textView);
            cost_textView = (TextView) itemView.findViewById(R.id.cost_textView);
            hotel_textView = (TextView) itemView.findViewById(R.id.hotel_textView);
            address_textView = (TextView) itemView.findViewById(R.id.address_textView);
            rate_textView = (TextView) itemView.findViewById(R.id.rate_textView);
            rating_textView = (TextView) itemView.findViewById(R.id.rating_textView);
            fav_imageBtn = (ImageButton) itemView.findViewById(R.id.fav_imageBtn);
            show_imageBtn = (ImageButton) itemView.findViewById(R.id.show_imageBtn);
            rating_imgbtn = (ImageButton) itemView.findViewById(R.id.rating_imgbtn);
            fav_imageBtn.setTag("blank");
            if(fromWhere.equals("SiteList")){
                fav_imageBtn.setVisibility(View.VISIBLE);
                show_imageBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.eye));
                wish_butn.setVisibility(View.GONE);
                show_imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,Site_Image_View.class);
                        i.putExtra("IN_VIEW_IMAGE","false");
                        context.startActivity(i);
                    }
                });
            }else if(fromWhere.equals("Wishlist")){
                fav_imageBtn.setVisibility(View.GONE);
                show_imageBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.remove_wishlist));
                show_imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int position = getAdapterPosition();
                        delete(position);
                    }

                });
                wish_butn.setVisibility(View.VISIBLE);
            }
            fav_imageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ImageButton btn = (ImageButton)v;
                    //btn.setImageDrawable(context.getResources().getDrawable(R.drawable.heart2));

                    String tagValve=fav_imageBtn.getTag().toString();
                    if (tagValve.equals("filled")) {
                        fav_imageBtn.setBackgroundResource(R.drawable.heart);
                        fav_imageBtn.setTag("blank");
                        //isFavourite = true;
                        //saveState(isFavourite);

                    } else {
                        fav_imageBtn.setBackgroundResource(R.drawable.heart2);
                        fav_imageBtn.setTag("filled");
                        //isFavourite = false;
                        //saveState(isFavourite);

                    }}
            });






            hotel_image=itemView.findViewById(R.id.hotel_image) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detect on item" + position, Snackbar.LENGTH_LONG);

                }
            });
            //show_imageBtn=itemView.findViewById(R.id.show_imageBtn);

        }
    }
    public void delete(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_layout, null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            JSONObject obj = list.getJSONObject(i);
            viewHolder.hotel_image.setBackgroundResource(obj.getInt("image"));
            viewHolder.price_textView.setText(obj.getString("price"));
            viewHolder.price_textView.setPaintFlags(viewHolder.price_textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.hotel_textView.setText(obj.getString("hotel"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        viewHolder.hotel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailsActivity();
            }
        });
        viewHolder.rating_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.rating_box,null);
                final PopupWindow popup = new PopupWindow(context);
                popup.setContentView(layout);
                popup.setFocusable(true);
                popup.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.transparent));
                popup.showAsDropDown(v);

            }
        });


    }
    public void startDetailsActivity(){
        Intent i=new Intent(context,Product_Image_page.class);
        context.startActivity(i);
    }
    @Override
    public int getItemCount() {
        return list.length();
    }
}


