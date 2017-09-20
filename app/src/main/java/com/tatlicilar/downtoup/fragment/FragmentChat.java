package com.tatlicilar.downtoup.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tatlicilar.downtoup.ChatActivity;
import com.tatlicilar.downtoup.ChatConversationActivity;
import com.tatlicilar.downtoup.CircleTransform;
import com.tatlicilar.downtoup.Login;
import com.tatlicilar.downtoup.Melek;
import com.tatlicilar.downtoup.R;
import com.tatlicilar.downtoup.Show_Chat_Activity_Data_Items;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentChat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChat extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView person_name,person_email;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<Melek, Show_Chat_ViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;
    public FragmentChat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentChat.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentChat newInstance() {
        FragmentChat fragment = new FragmentChat();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fragment_chat, container, false);
//        return inflater.inflate(R.layout.show_chat_layout, container, false);

    }

    public void onViewCreated(View view,Bundle savedInstancestate){
        Log.d(TAG, "1=onCreateview deyim");
//        View view = inflater.inflate(R.layout.fragment_fragment_chat,container,false);
        Log.d(TAG, "2=oview aldım");
        progressBar = (ProgressBar) view.findViewById(R.id.show_chat_progressBar2);
        Log.d(TAG, "3=progressbar aldım");
        recyclerView = (RecyclerView) view.findViewById(R.id.show_chat_recyclerView);
        Log.d(TAG, "4=rview aldım");
        // Inflate the layout for this fragment
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("users");
        RecyclerView.LayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        Log.d(TAG, "4=lman");
        recyclerView.setLayoutManager(mLinearLayoutManager);
        onStart();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        //Log.d("LOGGED", "Will Start Calling populateViewHolder : ");
        //Log.d("LOGGED", "IN onStart ");

        Log.d(TAG, "5=lman");

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_Chat_Activity_Data_Items, Show_Chat_ViewHolder>(Show_Chat_Activity_Data_Items.class, R.layout.show_chat_single_item, Show_Chat_ViewHolder.class, myRef) {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Melek, Show_Chat_ViewHolder>(Melek.class, R.layout.show_chat_single_item, Show_Chat_ViewHolder.class, myRef) {

            public void populateViewHolder(final Show_Chat_ViewHolder viewHolder, Melek model, final int position) {
                //Log.d("LOGGED", "populateViewHolder Called: ");
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d(TAG, "6" + model.getAd());
                if (!model.getAd().equals("Null")) {
                    viewHolder.Person_Name(model.getAd());
//                    viewHolder.Person_Image(model.getImage_Url());
                    viewHolder.Person_Email(model.getEmail());
                    if(model.getEmail().equals(Login.mail_adres))
                    {
                        //viewHolder.itemView.setVisibility(View.GONE);
                        viewHolder.Layout_hide();

                        //recyclerView.getChildAdapterPosition(viewHolder.itemView.getRootView());
                        // viewHolder.itemView.set;


                    }
                    else
                        viewHolder.Person_Email(model.getEmail());
                }


                //OnClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String retrieve_name = dataSnapshot.child("ad").getValue(String.class);
                                String retrieve_Email = dataSnapshot.child("email").getValue(String.class);
//                                String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);
//                                Intent intent = new Intent(getApplicationContext(), ChatConversationActivity.class);
                                Intent intent = new Intent(getActivity(), ChatConversationActivity.class);

//                                intent.putExtra("image_id", retrieve_url);
                                intent.putExtra("email", retrieve_Email);
                                intent.putExtra("ad", retrieve_name);

                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        Log.d(TAG, "7=lman");

        recyclerView.setAdapter(mFirebaseAdapter);
    }


    //View Holder For Recycler View
    public static class Show_Chat_ViewHolder extends RecyclerView.ViewHolder {
        private final TextView person_name, person_email;
        private final ImageView person_image;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public Show_Chat_ViewHolder(final View itemView) {
            super(itemView);
            person_name = (TextView) itemView.findViewById(R.id.chat_persion_name);
            person_email = (TextView) itemView.findViewById(R.id.chat_persion_email);
            person_image = (ImageView) itemView.findViewById(R.id.chat_persion_image);
            layout = (LinearLayout)itemView.findViewById(R.id.show_chat_single_item_layout);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        private void Person_Name(String title) {
            // Log.d("LOGGED", "Setting Name: ");
            person_name.setText(title);
        }
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params);
            layout.setLayoutParams(params);

        }


        private void Person_Email(String title) {
            person_email.setText(title);
        }


        private void Person_Image(String url) {

            if (!url.equals("Null")) {
                Glide.with(itemView.getContext())
                        .load(url)
                        .crossFade()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.loading)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(person_image);
            }

        }


    }
}
