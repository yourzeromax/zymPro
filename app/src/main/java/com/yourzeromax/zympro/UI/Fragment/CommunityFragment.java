package com.yourzeromax.zympro.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourzeromax.zympro.Adapter.CommunityRecyclerVIewAdapter;
import com.yourzeromax.zympro.JavaBeans.Community;
import com.yourzeromax.zympro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yourzeromax on 2017/11/6.
 */

public class CommunityFragment extends Fragment {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public CommunityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewInit();
        return view;
    }

    private void viewInit() {
        recyclerViewInit();
        tabLayoutInit();
    }

    private void recyclerViewInit() {
        List<Community> communities = new ArrayList<>();
        communities.add(new Community());
        communities.add(new Community());
        communities.add(new Community());
        communities.add(new Community());
        communities.add(new Community());
        CommunityRecyclerVIewAdapter adapter = new CommunityRecyclerVIewAdapter(getContext(), communities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void tabLayoutInit() {
        mTabLayout.addTab(mTabLayout.newTab().setText("学生"));
        mTabLayout.addTab(mTabLayout.newTab().setText("心理"));
        mTabLayout.addTab(mTabLayout.newTab().setText("志愿"));
        mTabLayout.addTab(mTabLayout.newTab().setText("学习"));
    }
}
