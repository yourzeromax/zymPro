package com.yourzeromax.zympro.UI.Fragment;

import android.content.Context;
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
    TextView tvShow1, tvShow2, tvShow3;
    private Context context;

    public BasicFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, null);
        tvShow1 = (TextView) view.findViewById(R.id.check_schools);
        tvShow2 = (TextView) view.findViewById(R.id.choose_speciality);
        tvShow3 = (TextView) view.findViewById(R.id.check_score);
        tvShow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1=new Intent(getActivity(), SearchSchoolActivity.class);
//                startActivity(intent1);
            }
                });
        tvShow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "智", Toast.LENGTH_SHORT).show();
            }
        });
        tvShow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "谋", Toast.LENGTH_SHORT).show();
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
