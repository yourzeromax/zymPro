package com.yourzeromax.zympro.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourzeromax.zympro.R;

/**
 * Created by yourzeromax on 2017/11/6.
 */

public class FunctionFragment extends Fragment {
    public FunctionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function, null);
        return view;
    }
}
