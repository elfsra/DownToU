package com.tatlicilar.downtoup;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatlicilar.downtoup.EgitimKategori;
import com.tatlicilar.downtoup.fragment.Egitim;

import java.util.List;

/**
 * Created by sezinkokum on 4.08.2017.
 */

public class EgitimAdapter extends RecyclerView.Adapter<EgitimAdapter.MyViewHolder>  {
    private List<EgitimKategori> egitimList;
    private AdapterListener listener;
    private Context context;

    public EgitimAdapter(List<EgitimKategori> egitimList, AdapterListener listener, Context context) {
        this.egitimList = egitimList;
        this.listener = listener;
        this.context=context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, author,publishYear;
        public ImageView coverImage;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.egitimIsmi);
            author = (TextView) view.findViewById(R.id.egitmenIsmi);
            publishYear = (TextView) view.findViewById(R.id.icerik);
            coverImage = (ImageView) view.findViewById(R.id.coverImg);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.egitim_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EgitimKategori egitim = egitimList.get(position);
        holder.name.setText(egitim.getEgitimIsmi());
        holder.author.setText(egitim.getEgitmenIsmi());
        holder.publishYear.setText(egitim.getIcerik());
        holder.coverImage.setImageResource(egitim.getCoverImage());

        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickImage(egitim);
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickName(egitim);
            }
        });

        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Eğitimi almak ister misin?");
                builder.setCancelable(true);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.name.setTextColor(Color.RED);
                    }
                });

                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.name.setTextColor(Color.BLACK);
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return egitimList.size();
    }

    public interface AdapterListener {
        public void onClickImage(EgitimKategori egitim);

        public void onClickName(EgitimKategori egitim);
    }
}