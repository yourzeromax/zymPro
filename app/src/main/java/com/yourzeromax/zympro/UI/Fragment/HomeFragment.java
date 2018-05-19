package com.yourzeromax.zympro.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourzeromax.zympro.MyViews.DrawableTextView;
import com.yourzeromax.zympro.R;
import com.yourzeromax.zympro.UI.ProfessionActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
//    @Bind(R.id.home_schools)
//    DrawableTextView mHomeSchools;
//    @Bind(R.id.home_profession)
//    DrawableTextView mHomeProfession;
//    @Bind(R.id.home_students)
//    DrawableTextView mHomeStudents;
//    @Bind(R.id.home_volunteer)
//    DrawableTextView mHomeVolunteer;

    DrawableTextView mHomeSchools;
    DrawableTextView mHomeProfession;
    DrawableTextView mHomeStudents;
    DrawableTextView mHomeVolunteer;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mHomeSchools = (DrawableTextView)view.findViewById(R.id.home_schools);
        mHomeProfession = (DrawableTextView)view.findViewById(R.id.home_profession);
        mHomeStudents = (DrawableTextView)view.findViewById(R.id.home_students);
        mHomeVolunteer = (DrawableTextView)view.findViewById(R.id.home_volunteer);
        initViewsLitenser();
        return view;
    }

   private void initViewsLitenser(){

       mHomeSchools.setOnClickListener(this);
       mHomeProfession.setOnClickListener(this);
       mHomeStudents.setOnClickListener(this);
       mHomeVolunteer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_schools:
                Log.d(TAG, "onClick: "+"home_schools");
                break;
            case R.id.home_profession:
Intent intent = new Intent(getActivity(),ProfessionActivity.class);
startActivity(intent);
                break;
            case R.id.home_students:
                Log.d(TAG, "onClick: "+"home_schools");
                break;
            case R.id.home_volunteer:
                Log.d(TAG, "onClick: "+"home_schools");
                break;
        }
    }
}
