package com.bril.nopapermeet.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bril.nopapermeet.R;

/**
 * Created by MrP on 2017/4/4.
 */

public class LoginAnimFragment extends Fragment {

    private ImageView img;
    private TextView tv1, tv2;
    private LinearLayout ll;
    private int[] images = {R.drawable.login_animat_1, R.drawable.login_animat_2, R.drawable.login_animat_3};
    private String[] strArray1 = {"一键触摸，快速签到，自动记录", "支持文件按权限分发，支持手写批注、签字。", "支持集中表决，支持系统通知群发，支持对单个或多个终端进行单独通知。"};
    private String[] strArray2 = {"支持多会议预置，会议信息一键切换", "会议内容自动存档，涉密资料阅后即焚。", "更多功能等待你发现！"};
    private int position = 0;
    private boolean isAnimta;
    private Handler handler;
    private int[] colors = {Color.parseColor("#9ad69c"), Color.parseColor("#ebc291"), Color.parseColor("#8399f9")};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 改变Activity背景色
        getActivity().getWindow().getDecorView().setBackgroundColor(colors[position]);

        // 初始化控件
        View root = inflater.inflate(R.layout.fragment_login_anim, container, false);
        ll = (LinearLayout) root.findViewById(R.id.ll_anim);
        img = (ImageView) root.findViewById(R.id.img_login_anim);
        tv1 = (TextView) root.findViewById(R.id.tv_login_anim_1);
        tv2 = (TextView) root.findViewById(R.id.tv_login_anim_2);

        initData();

        goInAnimation();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        isAnimta = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAnimta) {
                    handler.removeCallbacks(this);
                    return;
                }
                if (++position == 3) {
                    position = 0;
                }
                goOutAnimation();
                handler.postDelayed(this, 10000);
            }
        }, 10000);
    }

    @Override
    public void onStop() {
        super.onStop();
        isAnimta = false;
    }

    private void initData() {
        img.setImageResource(images[position]);
        tv1.setText(strArray1[position]);
        tv2.setText(strArray2[position]);
    }

    public void goInAnimation() {
        initData();
        Animation imgAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_anim);
        img.startAnimation(imgAnim);

        Animation tv1Anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_anim);
        tv1Anim.setStartOffset(300);
        tv1.startAnimation(tv1Anim);

        Animation tv2Anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_anim);
        tv2Anim.setStartOffset(600);
        tv2.startAnimation(tv2Anim);
    }

    public void goOutAnimation() {
        Animation imgAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_anim);
        img.startAnimation(imgAnim);

        Animation tv1Anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_anim);
        tv1Anim.setStartOffset(300);
        tv1.startAnimation(tv1Anim);

        Animation tv2Anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_anim);
        tv2Anim.setStartOffset(600);
        tv2Anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getActivity().getWindow().getDecorView().setBackgroundColor(colors[position]);
                goInAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv2.startAnimation(tv2Anim);
    }

}
