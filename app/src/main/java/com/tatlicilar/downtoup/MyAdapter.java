package com.tatlicilar.downtoup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tatlicilar.downtoup.fragment.FragmentProfil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.facebook.GraphRequest.TAG;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<AramaKisiler> kisiler,filterList;
    CustomFilter filter;
    Intent intent;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase mFirebaseDatabase; // access database
    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference DatabaseReference;
    private String userId;

    public MyAdapter(Context ctx,ArrayList<AramaKisiler> kisiler)
    {
        this.c=ctx;
        this.kisiler=kisiler;
        this.filterList=kisiler;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
        //HOLDER
        MyHolder holder=new MyHolder(v);
        return holder;
    }
    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //BIND DATA
        holder.posTxt.setText(kisiler.get(position).getPos());
        holder.nameTxt.setText(kisiler.get(position).getName());
        holder.img.setImageResource(kisiler.get(position).getImg());
        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Snackbar.make(v,kisiler.get(pos).getName(),Snackbar.LENGTH_SHORT).show();
            }
        });
        final AlertDialog.Builder builder=new AlertDialog.Builder(c);
        final String[] mCurrent_state = {"not friends"};

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference = mFirebaseDatabase.getReference("users");
        userId = DatabaseReference.push().getKey();
        DatabaseReference = mFirebaseDatabase.getReference("users").child(userId);

        mFriendReqDatabase = mFirebaseDatabase.getReference().child("Friend req");
        mFriendDatabase = mFirebaseDatabase.getReference().child("Friends");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
//        final String current_uid = mCurrentUser.getUid();

        //FRIEND LIST / REQUEST FEATURE
        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String ad = dataSnapshot.child("ad").getValue().toString();
                mFriendReqDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(userId)){
                        String req_type = dataSnapshot.child(userId).child("requst type").getValue().toString();
                        if(req_type.equals("received")){
//                            mProfileSendReqBtn.setEnabled(true);
                            mCurrent_state[0] = "req_received";
//                                            mProfileSendReqBtn.setText("Cancel friend request");
                            builder.setMessage("Arkadaşlık isteğini kabul et");
                        }
                        else if(req_type.equals("sent")){
                            mCurrent_state[0]="req_sent";
                            builder.setMessage("Arkadaşlık isteğini iptal et");
                        }
                    }
//                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrent_state[0].equals("not friends")){
                builder.setMessage("Arkadaş olarak eklemek ister misin?");
                builder.setCancelable(true);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).child("request type") .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).child("request type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).child("request type")
                                            .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>(){
                                        @Override
                                        public void onSuccess(Void avoid){
//                                            mProfileSendReqBtn.setEnabled(true);
                                            mCurrent_state[0] = "req_sent";
//                                            mProfileSendReqBtn.setText("Cancel friend request");
                                            builder.setMessage("Arkadaşlık isteğini iptal et");
//                                            Toast.makeText(MyAdapter.this,"request send successfully", Toast.LENGTH_SHORT).show();
                                        }});}
                                else{
                                }
                            }
                        });
                    }
                });
                }

                //CANCEL REQUEST
                if(mCurrent_state.equals("req_sent")){
                    mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    mProfileSendReqBtn.setEnabled(true);
                                    mCurrent_state[0] = "not friends";
//                                            mProfileSendReqBtn.setText("Cancel friend request");
                                    builder.setMessage("Arkadaşlık isteği gönder");
                                }
                            });}
                    });}
//                request received state
                if(mCurrent_state.equals(("received"))){
                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    mFriendDatabase.child(mCurrentUser.getUid()).child(userId).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendDatabase.child(userId).child(mCurrentUser.getUid()).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
//                                    mProfileSendReqBtn.setEnabled(true);
                                                    mCurrent_state[0] = "friends";
//                                            mProfileSendReqBtn.setText("Cancel friend request");
                                                    builder.setMessage("Arkadaşlıktan çıkar");
                                                }
                                            });}
                                    });
                                }
                            });
                        }
                    });
                }
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        holder.name.setTextColor(Color.BLACK);
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });

        holder.posTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c,FriendProfile.class);
                c.startActivity(intent);
            }
        });
    }
    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {
        return kisiler.size();
    }
    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }
}
