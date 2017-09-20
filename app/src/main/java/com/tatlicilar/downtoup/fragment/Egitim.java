package com.tatlicilar.downtoup.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tatlicilar.downtoup.EgitimActivity;
import com.tatlicilar.downtoup.EgitimAdapter;
import com.tatlicilar.downtoup.EgitimIcerik1;
import com.tatlicilar.downtoup.EgitimKategori;
import com.tatlicilar.downtoup.R;
import com.tatlicilar.downtoup.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Egitim.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Egitim#newInstance} factory method to
 * create an instance of this fragment.
 */
//sezin
public class Egitim extends Fragment implements EgitimAdapter.AdapterListener,RecyclerView.OnItemTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<EgitimKategori> egitimList = new ArrayList<>();
    EgitimAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private static final String TAG = "1" ;

    public Egitim() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Egitim newInstance( ) {
        Egitim fragment = new Egitim();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_egitim,container,false);
        return inflater.inflate(R.layout.fragment_egitim, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d(TAG, "1=======");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        Log.d(TAG, "2=======");
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        Log.d(TAG, "5=======");
        recyclerView.setLayoutManager(mLayoutManager);
        Log.d(TAG, "6=======");
        mAdapter = new EgitimAdapter(egitimList,this,this.getContext());
        Log.d(TAG, "3=======");
        recyclerView.setAdapter(mAdapter);
        Log.d(TAG, "4=======");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.d(TAG, "7=======");
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        Log.d(TAG, "8=======");
        recyclerView.addItemDecoration(itemDecoration);
        Log.d(TAG, "9=======");
//        FragmentEducation questionFragment = (FragmentEducation) findViewById(fragment_question);
        egitimkategorileri();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                switch (position){
                    case 0:
                        Intent myIntent = new Intent(getActivity(), EgitimIcerik1.class);
                        getActivity().startActivity(myIntent);
                    case 1:

                    case 2:
                    case 3:
                    case 4:

                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void egitimkategorileri() {
        egitimList.add(new EgitimKategori("Okulöncesi Eğitim Türkçe Oyun","Neval Göksel","Bütün objeler Türkçe seslendirilmiştir.",R.drawable.egitim1,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=34&search_term=&filter_7_5=1"));
        egitimList.add(new EgitimKategori("Tadya Mutlu Bir Gün","Neval Göksel","Günlük yaşamda kullandığımız nesneleri, eylemleri, sosyal aktiviteleri kapsamlı şekilde öğretimi amaçlar",R.drawable.egitim2,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=22&search_term=&filter_7_5=1"));
        egitimList.add(new EgitimKategori("Şekiller - Çocuk Oyunu", "Neval Göksel","\tŞekillerin eğlenceli şekilde öğretimi amaçlanmaktadır.",R.drawable.egitim3,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=23&search_term=&filter_7_5=1"));
        egitimList.add(new EgitimKategori("Kavram Öğretimi", "Neval Göksel","İletişim, akademik ve günlük yaşamda kullanabilecekleri kavramları eğlenceli şekilde öğretmen için tasarlanmıştır.",R.drawable.egitim4,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=18&search_term=&filter_7_5=1"));
        egitimList.add(new EgitimKategori("Tohum 2", "Neval Göksel","\t4 yaş ve üzeri çocuklar için, nesne fotolarını eşleme, vücut bölümlerini tanıma ve eylemleri tanıma içeren işlevsel uygulamadır.",R.drawable.egitim5,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=16&search_term=&filter_7_5=1"));

        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClickImage(EgitimKategori egitim) {
//        Toast.makeText(this, egitim.getEgitimIsmi(),
//                Toast.LENGTH_LONG).show();
//        questionFragment.openProfile(v);
    }
    @Override
    public void onClickName(EgitimKategori egitim) {

        Intent newIntent=new Intent(getActivity(),EgitimActivity.class);
        newIntent.putExtra("url",egitim.getUrl());
        startActivity(newIntent);
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
