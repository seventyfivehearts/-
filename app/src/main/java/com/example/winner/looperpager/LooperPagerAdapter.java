package com.example.winner.looperpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {
    private List<Integer> mPicture = null;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPostion =  position % mPicture.size();
        ImageView imageView = new ImageView(container.getContext());
//        imageView.setBackgroundColor(mPicture.get(position));
        //设置好颜色之后添加到容器中 container
        imageView.setImageResource(mPicture.get(realPostion));
        container.addView(imageView);
        return imageView;
    }

    //添加初始和销毁的方法
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //销毁视图
        container.removeView((View) object);
    }

    //返回颜色
    @Override
    public int getCount() {
        if (mPicture != null) {
             return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<Integer> mColors) {
        this.mPicture = mColors;
    }

    public int getRealPictureSize(){
        if (mPicture != null) {
            return mPicture.size();
        }
        return 0;
    }
}
