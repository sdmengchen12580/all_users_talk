package org.faqrobot.text.ui.tabfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.faqrobot.text.R;
import org.faqrobot.text.adapter.MyBaseAdapter;
import org.faqrobot.text.ui.TabActivity;

import java.util.ArrayList;
import java.util.List;

// TODO: 2017/10/30 listview中子控件的点击事件 
// TODO: 2017/10/30 http://blog.csdn.net/zcf520android/article/details/51471018
public class RelQuesstionsFragment extends Fragment implements MyBaseAdapter.OnclickListener{


    public RelQuesstionsFragment() {
    }

    private ListView listView_rel_content;
    MyBaseAdapter myadapter;
    List<String> list_rel_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rel_quesstions, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        listView_rel_content = (ListView) view.findViewById(R.id.listview_rel);
        list_rel_content = new ArrayList<>();
        list_rel_content.add("给我看下大屏的使用手册");
        list_rel_content.add("公司成立了多久了？");
        list_rel_content.add("目前有多少员工？");
        list_rel_content.add("上一轮融到了多少资金？");
        list_rel_content.add("出差报销有规定的时间限制吗？");
        list_rel_content.add("日常给用如何报销？");
        myadapter = new MyBaseAdapter(getActivity(),list_rel_content);
        listView_rel_content.setAdapter(myadapter);
        /**fragment实现接口，并传入适配器中*/
        myadapter.set_click_listener(this);
    }


    /**
     * 适配器中点击事件的接口回调
     * 返回聊天界面-并播报
     */
    String string;
    @Override
    public void show_content(MyBaseAdapter adapter, View view, int position) {
        string = list_rel_content.get(position);
        Log.e("show_content: ","拿到的内容为"+ string);
        // TODO: 2017/10/30 跳转时候该怎么做
        ((TabActivity)getActivity()).viewPager.setCurrentItem(1,true);
        ((TabActivity)getActivity()).chatFragment.showTxtRight(string);
        ((TabActivity)getActivity()).chatFragment.speck_rel_quesstions(string);
    }

    @Override
    public void show_loge(MyBaseAdapter adapter, View view, int position) {
        Log.e("relquesstions中点击的数字为：",position+"" );
    }


    /**释放资源*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        list_rel_content.clear();
        list_rel_content = null;
        listView_rel_content = null;
        myadapter = null;
    }
}
