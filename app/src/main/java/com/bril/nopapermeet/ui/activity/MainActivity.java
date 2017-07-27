package com.bril.nopapermeet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.Menu;
import com.bril.nopapermeet.presenter.MainPresenter;
import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainPresenter> implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.lv_menu)
    private ListView lvMenu;
    @ViewInject(R.id.tv_department_name)
    private TextView tvDepartmentName;
    private QuickAdapter<Menu> quickAdapter;
    private Fragment preFragment;
    private static Boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvDepartmentName.setText(account.departmentName + " " + account.nickName);
        quickAdapter = new QuickAdapter<Menu>(context, R.layout.itme_main_menu) {
            @Override
            protected void convert(BaseAdapterHelper helper, Menu item) {
                helper.setText(R.id.tv_main_name, item.name);
                helper.setImageResource(R.id.iv_main_icon, item.icon);
                if (item.isCheck) {
                    helper.setBackgroundRes(R.id.ll_item, R.color.menu_main_select);
                } else {
                    helper.setBackgroundRes(R.id.ll_item, R.color.menu_main);
                }
            }
        };
        lvMenu.setAdapter(quickAdapter);
        lvMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvMenu.setOnItemClickListener(this);
        getPresenter().openHomeMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 替换菜单
     */
    public void repalceMenu(List<Menu> list) {
        quickAdapter.replaceAll(list);
        lvMenu.performItemClick(lvMenu, 0, 0);
    }

    /**
     * 选中菜单
     */
    public void checkItme(String name) {
        List<Menu> menus = quickAdapter.getAll();
        for (int j = 0; j < menus.size(); j++) {
            Menu me = menus.get(j);
            if (me.name.equals(name)) {
                me.isCheck = true;
            } else {
                me.isCheck = false;
            }
        }
        quickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        getPresenter().openItemMenu(quickAdapter.getItem(i));
    }

    public void goLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goCallOut(MeetList meetList) {
        Intent intent = new Intent(context, CallOutActivity.class);
        intent.putExtra("meetInfo", meetList);
        startActivity(intent);
    }

    public void goApplySay(MeetList meetList) {
        Intent intent = new Intent(context, ApplySayActivity.class);
        intent.putExtra("meetInfo", meetList);
        startActivity(intent);
    }

    /**
     * 选中
     *
     * @param fragment
     * @param tag
     */
    public void selectFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment findFrag = fm.findFragmentByTag(tag);
        if (findFrag == null) {
            if (preFragment != null) {
                fm.beginTransaction().hide(preFragment).add(R.id.fl_container, fragment, tag).commit();
            } else {
                fm.beginTransaction().add(R.id.fl_container, fragment, tag).commit();
            }
            preFragment = fragment;
        } else {
            fm.beginTransaction().hide(preFragment).show(findFrag).commit();
            preFragment = findFrag;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Timer tExit = null;
            if (isExit == false) {
                isExit = true; // 准备退出
                ToastUtils.shortShow(context, "再按一次退出程序");
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 1500);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
