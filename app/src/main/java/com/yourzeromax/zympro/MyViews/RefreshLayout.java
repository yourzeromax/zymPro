package com.yourzeromax.zympro.MyViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import com.yourzeromax.zympro.R;

/**
 * Created by yourzeromax on 2017/11/8.
 */

public class RefreshLayout extends ViewGroup {

    View mHeaderLayout;
    View mContentLayout;
    View mFootLayout;

    int mScreenHeight, mScreenWidth;
    int mHeaderHeight;

    Scroller mScroller;

    TextView textView;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mHeaderHeight = mScreenHeight / 4;
        mScroller = new Scroller(getContext());
        initViews();
        findViews();
    }

    private void initViews() {
        mHeaderLayout = LayoutInflater.from(getContext()).inflate(R.layout.refresh_header, null);
        mContentLayout = LayoutInflater.from(getContext()).inflate(R.layout.refresh_content, null);
        mFootLayout = LayoutInflater.from(getContext()).inflate(R.layout.refresh_footer, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContentLayout.setLayoutParams(params);
        LayoutParams headerparams = new LayoutParams(LayoutParams.MATCH_PARENT, mHeaderHeight);
        mHeaderLayout.setLayoutParams(headerparams);
        mFootLayout.setLayoutParams(headerparams);
    }

    private void findViews(){
        textView = (TextView)mHeaderLayout.findViewById(R.id.tv_view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(mHeaderLayout);
        addView(mContentLayout);
        addView(mFootLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            mHeight += child.getMeasuredHeight();
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount = getChildCount();
        int top = 0;

        for (int j = 0; j < childCount; j++) {
            View child = getChildAt(j);
            child.layout(0, top, child.getMeasuredWidth(), top + child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }
        this.scrollTo(0, mHeaderHeight);
    }

    int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int dy;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                dy = y - mLastY;
                scrollBy(0, -dy / 3);
                if(getScrollY()<mHeaderHeight/2){
                    textView.setText("松开刷新");
                }else {
                    textView.setText("下拉刷新");
                }
                break;
            case MotionEvent.ACTION_UP:
                mScroller.startScroll(0, getScrollY(), 0, mHeaderHeight - getScrollY(), 500);
                invalidate();
                //     scrollTo(0, mHeaderHeight);
                break;
        }
        mLastY = y;
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
