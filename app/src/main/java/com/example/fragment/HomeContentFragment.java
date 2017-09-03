package com.example.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adapter.ContentAdapter;
import com.example.utils.CacheUtils;
import com.example.widgets.SimplePaddingDecoration;
import com.example.http.HttpDataRepository;
import com.example.jokeeassy.R;
import com.example.model.ContentResponse;
import com.example.model.Record;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class HomeContentFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = "HomeRecommendFragment";
    public static String TITLE_KEY = "title";
    public static String TYPE_KEY = "content_type";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mContentListView;
    private ContentAdapter mContentAdapter;
    private String mTitle;
    private String mContentType;

    public HomeContentFragment(){

    }

    public static HomeContentFragment newInstance(String title,String contentType){
        HomeContentFragment contentFragment = new HomeContentFragment();
        contentFragment.setTitle(title);
        contentFragment.setContentType(contentType);
        /*Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY,title);
        bundle.putString(TYPE_KEY,contentType);
        contentFragment.setArguments(bundle);*/
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        if(args != null){
//            setTitle(args.getString(TITLE_KEY));
//            setContentType(args.getString(TYPE_KEY));
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContentAdapter = new ContentAdapter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.content_swipe_refresh_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mContentListView = (RecyclerView) view.findViewById(R.id.content_list_view);
        mContentListView.setAdapter(mContentAdapter);
        mContentListView.addOnScrollListener(new MyScrollListener());
        mContentListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mContentListView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mContentListView.addItemDecoration(new SimplePaddingDecoration());

        mContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContentListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int contentWidth = mContentListView.getWidth()
                        - mContentListView.getPaddingLeft() - mContentListView.getPaddingRight();
                mContentAdapter.setMaxWidth(contentWidth);

                List<Record> recordList = CacheUtils.getContentCache(mContentType);
                mContentAdapter.addRecords(true,recordList);
            }
        });
        return view;
    }



    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"监测到下拉，开始刷新推荐内容",Toast.LENGTH_SHORT).show();
        fetchContent(null);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType){
        mContentType = contentType;
    }

    public void fetchContent(OnFetchCompleteListener listener){
        pullContent(true,mContentType,listener);
    }

    private void pullContent(final boolean topRefresh,String contentType, final OnFetchCompleteListener listener){
        HttpDataRepository.getInstance().getContents(contentType,new Observer<ContentResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ContentResponse jsonResponse) {
                if(jsonResponse == null){
                    Log.e(TAG,"jsonResponse为空");
                    return;
                }
                List<Record> recordList = jsonResponse.getData().getData();
                mContentAdapter.addRecords(topRefresh,recordList);
                CacheUtils.saveContentCache(mContentType,recordList);
                if(listener != null){
                    listener.onFetchComplete(0);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError: " + mTitle + "页内容加载错误");
                mSwipeRefreshLayout.setRefreshing(false);
                if(listener != null){
                    listener.onFetchComplete(1);
                }
            }

            @Override
            public void onComplete() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public interface OnFetchCompleteListener{
        /**
         * 0:成功
         * 1:失败
         * @param result
         */
        void onFetchComplete(int result);
    }

    private class MyScrollListener extends RecyclerView.OnScrollListener{
        public MyScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState){
                case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                    Glide.with(getActivity()).resumeRequests();
                    break;

                case RecyclerView.SCROLL_STATE_DRAGGING://滚动做出了抛的动作
                    Glide.with(getActivity()).pauseRequests();
                    break;

                case RecyclerView.SCROLL_STATE_SETTLING://正在滚动
                    Glide.with(getActivity()).pauseRequests();
                    break;
            }

            //判断滑动到了底部
            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
            int visibleItemCount = recyclerView.getChildCount();

            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition == totalItemCount - 1
                    && visibleItemCount > 0) {
                //加载更多
                Toast.makeText(getActivity(),"滑动到了底部",Toast.LENGTH_SHORT).show();
                pullContent(false,mContentType,null);
            }
        }
    }


}
