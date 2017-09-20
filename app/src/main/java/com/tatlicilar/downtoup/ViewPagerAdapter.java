package com.tatlicilar.downtoup;

/**
 * Created by sezinkokum on 4.09.2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return new Arkadaslar();
            case 1 :
                return new Arama();
            case 2 :
                return new Neredeyim();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
