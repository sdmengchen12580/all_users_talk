package org.faqrobot.text.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.faqrobot.text.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孟晨 on 2017/10/30.
 */

public class MyBaseAdapter extends BaseAdapter {

   public List<String> list_rel_content=new ArrayList<>();
   private LayoutInflater inflater;
   private Context context;

    public MyBaseAdapter(Context context,List<String> list){
        this.context = context;
        inflater = LayoutInflater.from(context);
        list_rel_content = list;
    }
    @Override
    public int getCount() {
        return list_rel_content.size();
    }

    @Override
    public Object getItem(int position) {
        return list_rel_content.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if(null == convertView){
            viewholder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_listview_single,null);
            viewholder.number = (TextView) convertView.findViewById(R.id.single_layout_number);
            viewholder.content = (TextView) convertView.findViewById(R.id.single_layout_content);
            /**写入点击事件*/
            viewholder.number.setOnClickListener(mOnClickListener);
            viewholder.content.setOnClickListener(mOnClickListener);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        /**作标记*/
        viewholder.number.setTag(position);
        viewholder.content.setTag(position);
        /**绑定资源*/
        viewholder.number.setText((position+1)+"");
        viewholder.content.setText(list_rel_content.get(position));
        return convertView;
    }

    public class ViewHolder{
        private TextView number= null;
        private TextView content= null;
    }


    /**外部传进来的点击事件的接口*/
    private OnclickListener click_listener=null;
    public void set_click_listener(OnclickListener click_listener) {
        this.click_listener = click_listener;
    }
    public interface  OnclickListener{
        /**显示单项的textview问题的内容*/
        void show_content(MyBaseAdapter adapter, View view, int position);
        /**log日志显示当前的数字*/
        void show_loge(MyBaseAdapter adapter, View view, int position);
    }



    private  View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (click_listener != null) {
                int position = (Integer) v.getTag();
                switch (v.getId()) {
                    case R.id.single_layout_number:
                        click_listener.show_loge(MyBaseAdapter.this,v,position);
                        break;
                    case R.id.single_layout_content:
                        click_listener.show_content(MyBaseAdapter.this,v,position);
                        break;
                }
            }
        }
    };
}
