package com.yourzeromax.zympro.MyViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    TextView headerTextView;
    ListView contentListView;

    boolean isTop = false;

    OnRefreshListener onRefreshListener;

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
        listViewInit();
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

    private void findViews() {
        headerTextView = (TextView) mHeaderLayout.findViewById(R.id.tv_view);
        contentListView = (ListView) mContentLayout.findViewById(R.id.content_listView);
    }

    private void listViewInit() {
        String[] strings = new String[20];
        for (int i = 0; i < 20; i++) {
            strings[i] = "重庆大学";
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, strings);
        contentListView.setAdapter(adapter);
        mContentLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (contentListView.getFirstVisiblePosition() == 0) {
                    int mostTop = (getChildCount() > 0) ? contentListView.getChildAt(0)
                            .getTop() : 0;
                    if (mostTop >= 0) {
                        isTop = true;
                        return true;
                    }
                }
                isTop = false;
                return false;
            }
        });
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.onRefreshListener = refreshListener;
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                return false;
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) ev.getY() - mLastY;
                if (isTop() && dy > 0) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean isTop() {
        return contentListView.getFirstVisiblePosition() == 0 && contentListView.getChildAt(0).getTop() == 0 && getScrollY() <= mHeaderLayout.getMeasuredHeight();
    }

    private boolean isBottom(){
        return contentListView.getLastVisiblePosition()==contentListView.getAdapter().getCount()-1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int dy;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                dy = y - mLastY;
                scrollBy(0, -dy / 2);
                if (getScrollY() < mHeaderHeight / 2) {
                    headerTextView.setText("松开刷新");
                } else {
                    headerTextView.setText("下拉刷新");
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

    interface OnRefreshListener<T> {
        public T onrefresh();
    }
}
