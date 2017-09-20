package com.tatlicilar.downtoup;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.MyViewHolder>{
    private List<EbookIcerik> ebooks;
    private EbookAdapter.AdapterListener listener;
    private Context context;
    @Override
    public EbookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public EbookAdapter(List<EbookIcerik> ebook, EbookAdapter.AdapterListener listener, Context context) {
        this.ebooks = ebook;
        this.listener = listener;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        return 0;
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
    public void onBindViewHolder(final EbookAdapter.MyViewHolder holder, int position) {
        final EbookIcerik ebook = ebooks.get(position);
        holder.name.setText(ebook.getpdfIsmi());
        holder.author.setText(ebook.getEgitmenIsmi());
        holder.publishYear.setText(ebook.getIcerik());
        holder.coverImage.setImageResource(ebook.getCoverImage());

        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickImage(ebook);
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickName(ebook);
            }
        });

        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("pdf indirmek ister misin?");
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

    public interface AdapterListener {
        public void onClickImage(EbookIcerik ebook);

        public void onClickName(EbookIcerik ebook);
    }
}
