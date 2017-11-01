package org.faqrobot.text.ui.tabfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.faqrobot.text.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {


    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        img_dianzan = null;
        textview_click_value = null;
    }

    /**application获取name*/
    private String user_name;
    private ImageView img_dianzan;
    private TextView textview_click_value;
    private int number_value = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        clickView();
        return view;
    }

    private void clickView() {
        img_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_value += 1;
                textview_click_value.setText(number_value+"");
            }
        });
    }

    private void initView(View view) {
        img_dianzan = (ImageView) view.findViewById(R.id.img_dianzan);
        textview_click_value = (TextView) view.findViewById(R.id.number_value);
    }

}
