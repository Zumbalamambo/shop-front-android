package com.zack.shop.mvp.ui.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.mvp.http.entity.product.RecommendBean;
import com.zack.shop.mvp.ui.activity.product.ProductDetailsActivity;
import com.zack.shop.mvp.ui.widget.SlideDetailsLayout;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.GlideImageLoader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/6 下午4:21
 * @Package com.zack.shop.mvp.ui.fragments
 **/
public class ProductInfoFragment extends BaseSupportFragment implements View.OnClickListener, SlideDetailsLayout.OnSlideDetailsListener {


    @BindView(R.id.sv_switch)
    SlideDetailsLayout slideDetailsLayout;
    @BindView(R.id.sv_goods_info)
    ScrollView scrollView;
    @BindView(R.id.vp_item_goods_img)
    Banner bannerTitle;
    @BindView(R.id.vp_recommend)
    Banner bannerRecommend;
    @BindView(R.id.tv_goods_title)
    TextView productTitle;
    @BindView(R.id.tv_new_price)
    TextView newPrice;
    @BindView(R.id.tv_old_price)
    TextView oldPrice;
    @BindView(R.id.tv_current_goods)
    TextView currentProductType;
    @BindView(R.id.tv_comment_count)
    TextView commentCount;
    @BindView(R.id.tv_good_comment)
    TextView goodCommentCount;
    @BindView(R.id.ll_empty_comment)
    LinearLayout llEmptyComment;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.ll_pull_up)
    LinearLayout llPullUp;
    @BindView(R.id.ll_current_goods)
    LinearLayout llCurrentProduct;
    @BindView(R.id.fab_up_slide)
    FloatingActionButton fabUpSlide;
    private RecommendBean.RecommendProductsBean productBean;


    public ProductInfoFragment() {
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_info, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getData();
        initBanner();
        initText();
        initListenter();
        initFab();
    }

    private void getData() {
        productBean = ((RecommendBean.RecommendProductsBean) _mActivity.getIntent().getSerializableExtra(AppConstant.ActivityIntent.Bean));
    }

    private void initFab() {
        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fabUpSlide.hide();
    }

    private void initListenter() {
        fabUpSlide.setOnClickListener(this);
        llCurrentProduct.setOnClickListener(this);
        llEmptyComment.setOnClickListener(this);
        llPullUp.setOnClickListener(this);
        slideDetailsLayout.setOnSlideDetailsListener(this);
    }

    private void initText() {
        productTitle.setText(productBean.getName());
        newPrice.setText(String.format("%s", productBean.getPrice()));
        oldPrice.setText(String.format("%s", productBean.getPrice()));
        currentProductType.setText("普通类型，1件");
        commentCount.setText("0");
        goodCommentCount.setText("0");
    }

    private void initBanner() {
        if (!TextUtils.isEmpty(productBean.getSubImages())) {
            String[] split = productBean.getSubImages().split(",");
            List<String> subImages = Arrays.asList(split);
            {
                bannerTitle.setImageLoader(new GlideImageLoader());
//                //设置自动轮播，默认为true
//                bannerTitle.isAutoPlay(true);
//                //设置轮播时间
//                bannerTitle.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                bannerTitle.setIndicatorGravity(BannerConfig.CENTER);
                bannerTitle.setImages(subImages);
//                bannerTitle.start();
            }
            {
                bannerRecommend.setImageLoader(new GlideImageLoader());
//                //设置自动轮播，默认为true
//                bannerRecommend.isAutoPlay(true);
//                //设置轮播时间
//                bannerRecommend.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                bannerRecommend.setIndicatorGravity(BannerConfig.CENTER);
                bannerRecommend.setImages(subImages);
//                bannerRecommend.start();
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pull_up:
                //上拉查看图文详情
                slideDetailsLayout.smoothOpen(true);
                break;

            case R.id.fab_up_slide:
                //点击滑动到顶部
                scrollView.smoothScrollTo(0, 0);
                slideDetailsLayout.smoothClose(true);
                break;

        }
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        ProductDetailsActivity mActivity = (ProductDetailsActivity) _mActivity;
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fabUpSlide.show();
            mActivity.showTitle(true);
        } else {
            //当前为商品详情页
            fabUpSlide.hide();
            mActivity.showTitle(false);
        }
    }

}
