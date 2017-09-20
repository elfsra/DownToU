package com.tatlicilar.downtoup.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatlicilar.downtoup.R;
import com.tatlicilar.downtoup.SearchActivity;
import com.tatlicilar.downtoup.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfil extends android.support.v4.app.Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private OnFragmentInteractionListener mListener;

    private FragmentActivity myContext;
    Intent myIntent2;
    public FragmentProfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfil newInstance() {
        FragmentProfil fragment = new FragmentProfil();
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fragment_profil, container, false);

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

    public void onViewCreated(View view,Bundle savedInstancestate){

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolBar);
        toolbar.setTitle("App Bar Layout");
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        /*
        Creating Adapter and setting that adapter to the viewPager
        setSupportActionBar method takes the toolbar and sets it as
        the default action bar thus making the toolbar work like a normal
        action bar.
         */
        FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4
        adapter = new ViewPagerAdapter(fragManager);

//        adapter = new ViewPagerAdapter(getFragmentManager());

        viewPager.setAdapter(adapter);
        /*
        TabLayout.newTab() method creates a tab view, Now a Tab view is not the view
        which is below the tabs, its the tab itself.
         */
        //we also set the Test of the Tabs
        final TabLayout.Tab test01 = tabLayout.newTab();
        final TabLayout.Tab test02 = tabLayout.newTab();
        final TabLayout.Tab test03 = tabLayout.newTab();
        test01.setIcon(R.drawable.twof);
        test02.setIcon(R.drawable.cloud);
        test03.setIcon(R.drawable.placeholder);
        tabLayout.addTab(test01,0);
        tabLayout.addTab(test02,1);
        tabLayout.addTab(test03,2);
        
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(),R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                              @Override
                                              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                              }
                                              @Override
                                              public void onPageSelected(int position) {
                                                  switch (position) {
                                                      case 0:
                                                          test01.setIcon(R.drawable.twof);
                                                          test02.setIcon(R.drawable.cloud);
                                                          test03.setIcon(R.drawable.placeholder);
                                                          break;
                                                      case 1:
                                                          test01.setIcon(R.drawable.twof);
                                                          test02.setIcon(R.drawable.cloud);
                                                          test03.setIcon(R.drawable.placeholder);
                                                          Intent myIntent2 = new Intent(getActivity(), SearchActivity.class);
                                                          getActivity().startActivity(myIntent2);
                                                          break;
                                                      case 2:
                                                          test01.setIcon(R.drawable.twof);
                                                          test02.setIcon(R.drawable.cloud);
                                                          test03.setIcon(R.drawable.placeholder);
                                                          break;
                                                  }
                                              }
                                              @Override
                                              public void onPageScrollStateChanged(int state) {}
                                          }
        );
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override

            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}