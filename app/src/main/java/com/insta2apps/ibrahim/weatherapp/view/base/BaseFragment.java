package com.insta2apps.ibrahim.weatherapp.view.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.insta2apps.ibrahim.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ibrahim AbdelGawad on 1/28/2018.
 */

public abstract class BaseFragment<P extends BasePresenter> extends android.support.v4.app.Fragment implements BasePresenter.View, OnBackPressedInterface {
    protected Context mContext;
    protected View rootView;
    protected ViewStub mainViewStub;
    protected ViewStub loadingViewStub;

    @BindView(R.id.main_content)
    View contentView;

    @BindView(R.id.main_loading)
    View loadingView;


    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_base, container, false);
        mainViewStub = (ViewStub) rootView.findViewById(R.id.main_stub);
        loadingViewStub = (ViewStub) rootView.findViewById(R.id.loading_stub);

        mainViewStub.setLayoutResource(getLayoutRes());
        mainViewStub.inflate();

        loadingViewStub.setLayoutResource(getLoadingLayout());
        loadingViewStub.inflate();
        ButterKnife.bind(this, rootView);
        loadingView.setVisibility(View.GONE);

        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        return rootView;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    @Override
    public boolean onBackPressed() {
        int childCount = getChildFragmentManager().getBackStackEntryCount();
        if (childCount > 0) {
            getChildFragmentManager().popBackStackImmediate();
            return true;
        }
        return false;
    }


    protected int getLoadingLayout() {
        return R.layout.layout_main_loading;
    }


    protected abstract int getLayoutRes();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    public void setPresenter(P mPresenter) {
        this.mPresenter = mPresenter;
    }


    /**
     * Creates a new presenter instance, if needed. Will reuse the previous presenter instance if
     */
    public P getPresenter() {
        return mPresenter;
    }


    @Override
    public void showLoading() {
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }


    public void setBackView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}
