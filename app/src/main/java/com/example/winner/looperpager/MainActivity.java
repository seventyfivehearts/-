package com.example.winner.looperpager;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyViewPagerTouch.onViewPagerTouchListener, ViewPager.OnPageChangeListener {

    private MyViewPagerTouch mLooperPager;
    private LooperPagerAdapter mLooperPagerAdapter;
    private static List<Integer> sPicture = new ArrayList<>();
    private boolean mIsTouch = false;

    static {
        //NBA球星球球衣
        sPicture.add(R.mipmap.carter);
        sPicture.add(R.mipmap.cury);
        sPicture.add(R.mipmap.jordon);
        sPicture.add(R.mipmap.kobe);
        sPicture.add(R.mipmap.maddie);
        sPicture.add(R.mipmap.nash);
        sPicture.add(R.mipmap.owen);
        sPicture.add(R.mipmap.rose);
    }

    private Handler mHandler;
    private LinearLayout mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();

//        //准备数据  输出五种颜色
//        Random random = new Random();
//        for (int i = 0; i < 5; i++) {
//            mColors.add(Color.argb(random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255)));
//        }
//        mLooperPagerAdapter.setData(mColors);
//        //设置适配器之后才准备数据的
//        mLooperPagerAdapter.notifyDataSetChanged();

        mHandler = new Handler();
    }

    @Override
    public void onAttachedToWindow() {
        //当界面绑定到窗口时候
        mHandler.post(mRunnable);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mRunnable);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //判断是否触摸
            if (!mIsTouch) {
                //切换ViewPager的图片到下一个
                int currentItem = mLooperPager.getCurrentItem();
                mLooperPager.setCurrentItem(++currentItem, true);
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    private void initView() {
        //1.找到控件
        mLooperPager = this.findViewById(R.id.looper_pager);
        //2.设置适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        mLooperPagerAdapter.setData(sPicture);
        //把适配器设置到looper里面去
        mLooperPager.setAdapter(mLooperPagerAdapter);
        mLooperPager.addOnPageChangeListener(this);
        //初始时候设置1000，并且不做动画
        mLooperPager.setonViewPagerTouchListener(this);
        mPoints = this.findViewById(R.id.points);
        //根据图片的个数来加点
        InsertPoints();
        mLooperPager.setCurrentItem(mLooperPagerAdapter.getRealPictureSize() * 100, false);
    }

    private void InsertPoints() {
        for (int i = 0; i < sPicture.size(); i++) {
            View view = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
            view.setBackground(getResources().getDrawable(R.drawable.shape_ponit_press));
            layoutParams.leftMargin = 40;
            view.setLayoutParams(layoutParams);
            mPoints.addView(view);
        }
    }

    @Override
    public void onPagerTouch(boolean isTouch) {
        mIsTouch = isTouch;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //viewPager停下选中的位置
        int realPostion;
        if (mLooperPagerAdapter.getRealPictureSize() != 0) {
            realPostion = position % mLooperPagerAdapter.getRealPictureSize();
        } else {
            realPostion = 0;
        }
        selectPoint(realPostion);
    }

    private void selectPoint(int realPostion) {
        for (int i = 0; i < mPoints.getChildCount(); i++) {
            View childAt = mPoints.getChildAt(i);
            if (i != realPostion){
                //灰色
                childAt.setBackgroundResource(R.drawable.shape_point_nomal);
            }else {
                //红色
                childAt.setBackgroundResource(R.drawable.shape_ponit_press);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
