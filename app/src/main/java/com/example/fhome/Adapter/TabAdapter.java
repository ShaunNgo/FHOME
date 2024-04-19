package com.example.fhome.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fhome.Fragment.IncomePortfolioFragment;
import com.example.fhome.Fragment.IncomeReportFragment;
import com.example.fhome.Fragment.SpendingPortfolioFragment;
import com.example.fhome.Fragment.SpendingReportFragment;

public class TabAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    int isReport;

    public TabAdapter(Context context, FragmentManager fm, int totalTabs, int isReport) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.isReport = isReport;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        if (isReport==0){
            switch (position) {
                case 0:
                    SpendingPortfolioFragment spendingPortfolioFragment = new SpendingPortfolioFragment();
                    return spendingPortfolioFragment;
                case 1:
                    IncomePortfolioFragment incomePortfolioFragment = new IncomePortfolioFragment();
                    return incomePortfolioFragment;
                default:
                    return null;
            }
        }else {
            switch (position) {
                case 0:
                    SpendingReportFragment spendingReportFragment = new SpendingReportFragment();
                    return spendingReportFragment;
                case 1:
                    IncomeReportFragment incomeReportFragment = new IncomeReportFragment();
                    return incomeReportFragment;
                default:
                    return null;
            }
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}
