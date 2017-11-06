package com.yourzeromax.zympro.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yourzeromax.zympro.R;

/**
 * Created by yourzeromax on 2017/11/5.
 */

public class BasicFragment extends Fragment {
    TextView tvshow;
    public BasicFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic,null);
        tvshow = (TextView)view.findViewById(R.id.choose_speciality);
        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }
}
