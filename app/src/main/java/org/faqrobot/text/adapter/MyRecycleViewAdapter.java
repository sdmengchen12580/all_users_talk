package org.faqrobot.text.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.faqrobot.text.R;
import org.faqrobot.text.constant.Config;
import org.faqrobot.text.custom.flowlayout.FlowLayout;
import org.faqrobot.text.custom.flowlayout.TagAdapter;
import org.faqrobot.text.custom.flowlayout.TagFlowLayout;
import org.faqrobot.text.custom.richtext.MoonHtmlRemoteImageGetter;
import org.faqrobot.text.custom.richtext.RichText;
import org.faqrobot.text.custom.richtext.listener.OnTextViewClickListener;
import org.faqrobot.text.entity.GetRobatResult;
import org.faqrobot.text.entity.GusList;
import org.faqrobot.text.entity.MyQuestion;
import org.faqrobot.text.entity.RobotAnswer;
import org.faqrobot.text.ui.webviewactivity.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class MyRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContent;
    public List<GetRobatResult> mList=new ArrayList<>();

    public MyRecycleViewAdapter(Context context, List<GetRobatResult> titls) {
        this.mContent = context;
        this.mList = titls;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        if (Config.FROMAUDIO == viewType) {       //收到的音频
            view = mLayoutInflater.inflate(R.layout.recycleview_audio_left, parent, false);
            return new ViewHolder_audio(view);
        } else if (Config.FROMIMAGE == viewType) {   //收到的图片
            view = mLayoutInflater.inflate(R.layout.recycleview_image_left, parent, false);
            return new ViewHolder_pic(view);
        } else if (Config.FROMTXT == viewType) {   //收到的文本
            view = mLayoutInflater.inflate(R.layout.recycleview_txt_left, parent, false);
            return new ViewHolder_Text(view);
        } else if (Config.FROMMORE == viewType) {   //收到的富文本
            view = mLayoutInflater.inflate(R.layout.recycleview_more_left, parent, false);
            return new ViewHolder_more(view);
        } else if (Config.FROMVIDEO == viewType) {   //收到的视频
            view = mLayoutInflater.inflate(R.layout.recycleview_vd_left, parent, false);
            return new ViewHolder_video(view);
        } else if (Config.SENDAUDIO == viewType) {   //发送音频
            view = mLayoutInflater.inflate(R.layout.recycleview_audio_right, parent, false);
            return new ViewHolder_audio(view);
        } else if (Config.SENDIMAGE == viewType) {   //发送图片
            view = mLayoutInflater.inflate(R.layout.recycleview_image_right, parent, false);
            return new ViewHolder_pic(view);
        } else if (Config.SENDVIDEO == viewType) {   //发送视频
            view = mLayoutInflater.inflate(R.layout.recycleview_vd_right, parent, false);
            return new ViewHolder_video(view);
        } else if (Config.SENDTXT == viewType) {   //发送文本
            view = mLayoutInflater.inflate(R.layout.recycleview_txt_right, parent, false);
            return new ViewHolder_Text(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        holder.setIsRecyclable(false);
        if (holder instanceof ViewHolder_Text) {
            //设置监听事件
            TextView mClock = ((ViewHolder_Text) holder).mClock;
            ImageView mPersion_pic = ((ViewHolder_Text) holder).mPersion_Pic;
            final ImageView isSpeaking = ((ViewHolder_Text) holder).isSpeaking;
            TextView mTxt = ((ViewHolder_Text) holder).mTxt;
            RichText text_show_webview = ((ViewHolder_Text) holder).text_show_webview;
            LinearLayout answer_01 = ((ViewHolder_Text) holder).answer_01;
            LinearLayout answer_02 = ((ViewHolder_Text) holder).answer_02;
            LinearLayout answer_03 = ((ViewHolder_Text) holder).answer_03;
            LinearLayout answer_04 = ((ViewHolder_Text) holder).answer_04;
            LinearLayout answer_05 = ((ViewHolder_Text) holder).answer_05;
            LinearLayout answer_06 = ((ViewHolder_Text) holder).answer_06;
            LinearLayout answer_07 = ((ViewHolder_Text) holder).answer_07;
            LinearLayout answer_08 = ((ViewHolder_Text) holder).answer_08;
            LinearLayout answer_09 = ((ViewHolder_Text) holder).answer_09;
            LinearLayout answer_10 = ((ViewHolder_Text) holder).answer_10;
            Button answer_01_button_01 = ((ViewHolder_Text) holder).answer_01_button_01;
            Button answer_01_button_02 = ((ViewHolder_Text) holder).answer_01_button_02;
            Button answer_01_button_03 = ((ViewHolder_Text) holder).answer_01_button_03;
            Button answer_01_button_04 = ((ViewHolder_Text) holder).answer_01_button_04;
            Button answer_01_button_05 = ((ViewHolder_Text) holder).answer_01_button_05;
            Button answer_01_button_06 = ((ViewHolder_Text) holder).answer_01_button_06;
            Button answer_01_button_07 = ((ViewHolder_Text) holder).answer_01_button_07;
            Button answer_01_button_08 = ((ViewHolder_Text) holder).answer_01_button_08;
            Button answer_01_button_09 = ((ViewHolder_Text) holder).answer_01_button_09;
            Button answer_01_button_10 = ((ViewHolder_Text) holder).answer_01_button_10;
            TextView answer_01_numbwe_01 = ((ViewHolder_Text) holder).answer_01_numbwe_01;
            TextView answer_01_numbwe_02 = ((ViewHolder_Text) holder).answer_01_numbwe_02;
            TextView answer_01_numbwe_03 = ((ViewHolder_Text) holder).answer_01_numbwe_03;
            TextView answer_01_numbwe_04 = ((ViewHolder_Text) holder).answer_01_numbwe_04;
            TextView answer_01_numbwe_05 = ((ViewHolder_Text) holder).answer_01_numbwe_05;
            TextView answer_01_numbwe_06 = ((ViewHolder_Text) holder).answer_01_numbwe_06;
            TextView answer_01_numbwe_07 = ((ViewHolder_Text) holder).answer_01_numbwe_07;
            TextView answer_01_numbwe_08 = ((ViewHolder_Text) holder).answer_01_numbwe_08;
            TextView answer_01_numbwe_09 = ((ViewHolder_Text) holder).answer_01_numbwe_09;
            TextView answer_01_numbwe_10 = ((ViewHolder_Text) holder).answer_01_numbwe_10;
            TagFlowLayout mFlowlayout = ((ViewHolder_Text) holder).id_flowlayout;
            List<LinearLayout> linearLayouts = new ArrayList<>();
            List<Button> buttons = new ArrayList<>();
            List<TextView> textViews = new ArrayList<>();
            textViews.add(answer_01_numbwe_01);
            textViews.add(answer_01_numbwe_02);
            textViews.add(answer_01_numbwe_03);
            textViews.add(answer_01_numbwe_04);
            textViews.add(answer_01_numbwe_05);
            textViews.add(answer_01_numbwe_06);
            textViews.add(answer_01_numbwe_07);
            textViews.add(answer_01_numbwe_08);
            textViews.add(answer_01_numbwe_09);
            textViews.add(answer_01_numbwe_10);
            linearLayouts.add(answer_01);
            linearLayouts.add(answer_02);
            linearLayouts.add(answer_03);
            linearLayouts.add(answer_04);
            linearLayouts.add(answer_05);
            linearLayouts.add(answer_06);
            linearLayouts.add(answer_07);
            linearLayouts.add(answer_08);
            linearLayouts.add(answer_09);
            linearLayouts.add(answer_10);
            buttons.add(answer_01_button_01);
            buttons.add(answer_01_button_02);
            buttons.add(answer_01_button_03);
            buttons.add(answer_01_button_04);
            buttons.add(answer_01_button_05);
            buttons.add(answer_01_button_06);
            buttons.add(answer_01_button_07);
            buttons.add(answer_01_button_08);
            buttons.add(answer_01_button_09);
            buttons.add(answer_01_button_10);
            if (null != mFlowlayout) {
                mFlowlayout.setVisibility(View.GONE);
            }
            for (LinearLayout mLinearLayout :
                    linearLayouts) {
                if (null != mLinearLayout) {
                    mLinearLayout.setVisibility(View.GONE);
                }
                if (null != text_show_webview) {
                    text_show_webview.setVisibility(View.GONE);
                }
                if (null != mTxt) {
                    mTxt.setVisibility(View.GONE);
                }
            }
            GetRobatResult getRobatResult = mList.get(position);
            RobotAnswer robotAnswer = getRobatResult.getRobotAnswer().get(0);
            String ansCon = robotAnswer.getAnsCon();
//            if (position==mList.size()-1){
//                mList.get(position).setPlaying(true);//设置一开始就播报
//            }
            if (mList.get(position).isPlaying()){
                isSpeaking.setImageResource(R.drawable.speaking);
            }else{
                isSpeaking.setImageResource(R.drawable.stopspeak);

            }
//            if (position==0){
//                isSpeaking.setImageResource(R.drawable.speaking);
//            }
//            if (position==mList.size()-1){
//                isSpeaking.setImageResource(R.drawable.speaking);
//            }
            isSpeaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecycleViewisSpakingClickListenter.setisSpeakingClick(isSpeaking,position,mList.get(position).isPlaying());
                }
            });
            if (ansCon.contains("</")) {
                //webview展示
                text_show_webview.setVisibility(View.VISIBLE);
                String s = ansCon.replaceAll("href=\"/", "href=\"http://v3.faqrobot.org/");
                setWebView(text_show_webview, s, holder);
            } else {
                //文字展示
                mTxt.setVisibility(View.VISIBLE);
                mTxt.setText(robotAnswer.getAnsCon());
            }
            final List<GusList> gusList = robotAnswer.getGusList();
            final List<MyQuestion> relateList = robotAnswer.getRelateList();
            final LayoutInflater from = LayoutInflater.from(mContent);
            if (null != relateList) {
                //相似文法
                int size = relateList.size();
                if (size > 10) {
                    mFlowlayout.setVisibility(View.VISIBLE);
                    mFlowlayout.setAdapter(new TagAdapter(relateList) {
                        @Override
                        public View getView(FlowLayout parent, int position, Object o) {
                            TextView inflate = (TextView) from.inflate(R.layout.recycleview_txt_flowitem, parent, false);
                            inflate.setText(relateList.get(position).getQuestion());
                            return inflate;
                        }
                    });
                    mFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            mOnRecycleViewTextClickListenter.setTextClick(view, holder.getAdapterPosition(), position, 0x2);
                            return true;
                        }
                    });
                } else {
                    for (int i = 0; i < size; i++) {
                        LinearLayout linearLayout = linearLayouts.get(i);
                        linearLayout.setVisibility(View.VISIBLE);
                        buttons.get(i).setText(relateList.get(i).getQuestion());
                        final int finalI = i;
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnRecycleViewTextClickListenter.setTextClick(v, holder.getAdapterPosition(), finalI, 0x2);
                            }
                        });
                    }
                }
            }
            if (null != gusList) {
                //导航
                int size = gusList.size();
                if (size > 10) {
                    mFlowlayout.setVisibility(View.VISIBLE);
                    mFlowlayout.setAdapter(new TagAdapter(relateList) {
                        @Override
                        public View getView(FlowLayout parent, int position, Object o) {
                            TextView inflate = (TextView) from.inflate(R.layout.recycleview_txt_flowitem, parent, false);
                            inflate.setText(gusList.get(position).getQuestion());
                            return inflate;
                        }
                    });
                    mFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            mOnRecycleViewTextClickListenter.setTextClick(view, holder.getAdapterPosition(), position, 0x2);
                            return true;
                        }
                    });
                } else {
                    for (int i = 0; i < size; i++) {
                        final int finalI = i;
                        buttons.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //点选的问题 回调
                                mOnRecycleViewTextClickListenter.setTextClick(v, holder.getAdapterPosition(), finalI, 0x1);
                            }
                        });
                        buttons.get(i).setText(gusList.get(i).getSeedQuestion().getQuestion());
                        linearLayouts.get(i).setVisibility(View.VISIBLE);
                    }
                }
            }

        } else if (holder instanceof ViewHolder_audio) {
            ImageView mPersion_pic = ((ViewHolder_audio) holder).mPersion_Pic;
            final CircleImageView clockIcon = ((ViewHolder_audio) holder).clockIcon;
            TextView mClock = ((ViewHolder_audio) holder).mClock;
            ImageButton start_voice = ((ViewHolder_audio) holder).start_voice;
            GetRobatResult getRobatResult = mList.get(position);
            final boolean playing = getRobatResult.isPlaying();
            if (playing) {
                //如果是正在播放
//                start_voice.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_pause));
                Animation animation = AnimationUtils.loadAnimation(mContent, R.anim.audio_rotate_animation);
                clockIcon.startAnimation(animation);
            } else {
                //如果停止播放
//                start_voice.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
            }
            //音频的开始点击事件
            start_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecycleViewAudioClickListenter.setAudioStart(v, holder.getAdapterPosition());
                }

//                private void enterAnimation() {
//                    Animation anim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                    anim.setFillAfter(true); // 设置保持动画最后的状态
//                    anim.setDuration(30000); // 设置动画时间
//                    anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
//                    clockIcon .startAnimation(anim);
//                }
//
//                private void exitAnimation() {
//                }

            });
        } else if (holder instanceof ViewHolder_video) {
            TextView mClock = ((ViewHolder_video) holder).mClock;
            ImageView mPersion_pic = ((ViewHolder_video) holder).mPersion_Pic;
            SurfaceView mVideo = ((ViewHolder_video) holder).mVideo;
            mVideo.setVisibility(View.VISIBLE);
            mVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecycleViewVideoClickListenter.setVideoClick(v, holder.getAdapterPosition());
                }
            });
        } else if (holder instanceof ViewHolder_more) {

            TextView mClock = ((ViewHolder_more) holder).mClock;
            ImageView mPersion_pic = ((ViewHolder_more) holder).mPersion_Pic;
            final ImageView isSpeaking = ((ViewHolder_more) holder).isSpeaking;
            RichText mWebView = ((ViewHolder_more) holder).mWebView;
            GetRobatResult getRobatResult = mList.get(position);
            String ansCon = getRobatResult.getRobotAnswer().get(0).getAnsCon();
            String s = ansCon.replaceAll("href=\"/", "href=\"http://v3.faqrobot.org/");
            //设置聊天时间
            setWebView(mWebView, s, holder);
//            if (position==mList.size()-1){
//                mList.get(position).setPlaying(true);//设置一开始就播报
//            }
            if (mList.get(position).isPlaying()){
                isSpeaking.setImageResource(R.drawable.speaking);
            }else{
                isSpeaking.setImageResource(R.drawable.stopspeak);
            }
//            if (position==0){
//                isSpeaking.setImageResource(R.drawable.speaking);
//            }
//            if (position==mList.size()-1){
//                isSpeaking.setImageResource(R.drawable.speaking);
//            }
            isSpeaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecycleViewisSpakingClickListenter.setisSpeakingClick(isSpeaking,position,mList.get(position).isPlaying());
                }
            });
        } else if (holder instanceof ViewHolder_pic) {
            final List<ImageView> imageLists = new ArrayList<>();
            final List<TextView> TextViewLists = new ArrayList<>();
            final List<RelativeLayout> RelativeLayoutLists = new ArrayList<>();
            TextView mClock = ((ViewHolder_pic) holder).mClock;
            ImageView mPersion_pic = ((ViewHolder_pic) holder).mPersion_Pic;
            imageLists.add(((ViewHolder_pic) holder).rich_01);
            imageLists.add(((ViewHolder_pic) holder).rich_02);
            imageLists.add(((ViewHolder_pic) holder).rich_03);
            imageLists.add(((ViewHolder_pic) holder).rich_04);
            imageLists.add(((ViewHolder_pic) holder).rich_05);
            imageLists.add(((ViewHolder_pic) holder).rich_06);
            imageLists.add(((ViewHolder_pic) holder).rich_07);
            imageLists.add(((ViewHolder_pic) holder).rich_08);
            imageLists.add(((ViewHolder_pic) holder).rich_09);
            imageLists.add(((ViewHolder_pic) holder).rich_10);
            TextViewLists.add(((ViewHolder_pic) holder).text_01);
            TextViewLists.add(((ViewHolder_pic) holder).text_02);
            TextViewLists.add(((ViewHolder_pic) holder).text_03);
            TextViewLists.add(((ViewHolder_pic) holder).text_04);
            TextViewLists.add(((ViewHolder_pic) holder).text_05);
            TextViewLists.add(((ViewHolder_pic) holder).text_06);
            TextViewLists.add(((ViewHolder_pic) holder).text_07);
            TextViewLists.add(((ViewHolder_pic) holder).text_08);
            TextViewLists.add(((ViewHolder_pic) holder).text_09);
            TextViewLists.add(((ViewHolder_pic) holder).text_10);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).one_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).two_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).three_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).four_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).five_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).six_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).seven_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).eight_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).nine_more);
            RelativeLayoutLists.add(((ViewHolder_pic) holder).ten_more);
            for (RelativeLayout mLinearLayout : RelativeLayoutLists
                    ) {
                if (null != mLinearLayout) {
                    mLinearLayout.setVisibility(View.GONE);
                }
            }
            final GetRobatResult getRobatResult = mList.get(position);
            //设置聊天时间

            //图片点击事件
            List<String> urlRichLink = getRobatResult.getUrlRichLink();
            final int size = urlRichLink.size();

            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    RelativeLayoutLists.get(i).setVisibility(View.VISIBLE);
                    final int finalI = i;
                    RelativeLayoutLists.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnRecycleViewRichMediaClickListenter.setRichMediaClick(v, holder.getAdapterPosition(), finalI);
                        }
                    });
                    Glide.with(mContent).load(getRobatResult.getUrlRichImage().get(i)).into(imageLists.get(i));
                    TextViewLists.get(i).setText(getRobatResult.getUrlRichText().get(i));
                }
            }
        }
    }

    private void setWebView(final RichText mWebView, String s, RecyclerView.ViewHolder holder) {
        final String webHtml = "<html><body>" +
                s +
                "</body></html>";
        mWebView.textViewClickListener(new OnTextViewClickListener() {
            @Override
            public void imageClicked(ArrayList<String> imageUrls, int position) {

            }

            @Override
            public void textLinkClicked(String url) {
                Intent intent = new Intent(mContent, WebviewActivity.class);
                intent.putExtra("webUrl", url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContent.startActivity(intent);
            }
        }).imageLoadAdapter(new MoonHtmlRemoteImageGetter.Adapter() {
            @Override
            public Drawable getDefaultDrawable() {
                return null;
            }

            @Override
            public Drawable getErrorDrawable() {
                return null;
            }
        });
        mWebView.text(webHtml);
//
//        mWebView.loadDataWithBaseURL("about:blank", webHtml, "text/html", "UTF-8", null);
//        mWebView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
//                return false;
//            }
//
//            @Override
//            public void onScaleChanged(WebView webView, float v, float v1) {
//                super.onScaleChanged(webView, v, v1);
//            }
//
////
////            @Override
////            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
////                if ("pdf" == s.substring(s.lastIndexOf(".") + 1)) {
////                    Uri path = Uri.parse(s);
////                    Intent intent = new Intent(Intent.ACTION_VIEW);
////                    intent.setDataAndType(path, "application/pdf");
////                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    try {
////                        mContent.startActivity(intent);
////                    } catch (ActivityNotFoundException e) {
////                        Log.e("AA", "错误");
////                    }
////
////                }
////
////
////                return super.shouldInterceptRequest(webView, s);
////            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        GetRobatResult msg = mList.get(position);
        int direct = msg.getDirect();
        String msgType;
        if (Config.Direct.SEND == direct) {
            msgType = msg.getRobotAnswer().get(0).getMsgType();
            if (msgType.equals(Config.Type.TXT)) {
                return Config.SENDTXT;
            } else if (msgType.equals(Config.Type.IMAGE)) {
                return Config.SENDIMAGE;
            } else if (msgType.equals(Config.Type.VOICE)) {
                return Config.SENDAUDIO;
            } else if (msgType.equals(Config.Type.VIDEO)) {
                return Config.SENDVIDEO;
            }
        } else if (Config.Direct.RECEIVE == direct) {
            msgType = msg.getRobotAnswer().get(0).getMsgType();
            if (msgType.equals(Config.Type.TXT)) {
                return Config.FROMTXT;
            } else if (msgType.equals(Config.Type.IMAGE)) {
                return Config.FROMIMAGE;
            } else if (msgType.equals(Config.Type.VOICE)) {
                return Config.FROMAUDIO;
            } else if (msgType.equals(Config.Type.VIDEO)) {
                return Config.FROMVIDEO;
            } else if (msgType.equals(Config.Type.RICHTEXT)) {
                return Config.FROMMORE;
            }
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    /*
    * 文本viwHolder
    * */
    private class ViewHolder_Text extends RecyclerView.ViewHolder {
        ImageView mPersion_Pic,isSpeaking;
        TextView mClock;
        TextView mTxt;
        RichText text_show_webview;
        LinearLayout answer_01, answer_02, answer_03, answer_04, answer_05, answer_06, answer_07, answer_08, answer_09, answer_10;
        Button answer_01_button_01, answer_01_button_02, answer_01_button_03, answer_01_button_04, answer_01_button_05,
                answer_01_button_06, answer_01_button_07, answer_01_button_08, answer_01_button_09, answer_01_button_10;
        TextView answer_01_numbwe_01, answer_01_numbwe_02, answer_01_numbwe_03, answer_01_numbwe_04, answer_01_numbwe_05,
                answer_01_numbwe_06, answer_01_numbwe_07, answer_01_numbwe_08, answer_01_numbwe_09, answer_01_numbwe_10;
        LinearLayout mLinearLayout;
        TagFlowLayout id_flowlayout;

        private ViewHolder_Text(View itemView) {
            super(itemView);
            mPersion_Pic = (ImageView) itemView.findViewById(R.id.PersonPic);
            mClock = (TextView) itemView.findViewById(R.id.txt_time);
            mTxt = (TextView) itemView.findViewById(R.id.text_show);
            text_show_webview = (RichText) itemView.findViewById(R.id.text_show_webview);
            answer_01 = (LinearLayout) itemView.findViewById(R.id.answer_01);
            answer_02 = (LinearLayout) itemView.findViewById(R.id.answer_02);
            answer_03 = (LinearLayout) itemView.findViewById(R.id.answer_03);
            answer_04 = (LinearLayout) itemView.findViewById(R.id.answer_04);
            answer_05 = (LinearLayout) itemView.findViewById(R.id.answer_05);
            answer_06 = (LinearLayout) itemView.findViewById(R.id.answer_06);
            answer_07 = (LinearLayout) itemView.findViewById(R.id.answer_07);
            answer_08 = (LinearLayout) itemView.findViewById(R.id.answer_08);
            answer_09 = (LinearLayout) itemView.findViewById(R.id.answer_09);
            answer_10 = (LinearLayout) itemView.findViewById(R.id.answer_10);
            answer_01_button_01 = (Button) itemView.findViewById(R.id.answer_01_button_01);
            answer_01_button_02 = (Button) itemView.findViewById(R.id.answer_01_button_02);
            answer_01_button_03 = (Button) itemView.findViewById(R.id.answer_01_button_03);
            answer_01_button_04 = (Button) itemView.findViewById(R.id.answer_01_button_04);
            answer_01_button_05 = (Button) itemView.findViewById(R.id.answer_01_button_05);
            answer_01_button_06 = (Button) itemView.findViewById(R.id.answer_01_button_06);
            answer_01_button_07 = (Button) itemView.findViewById(R.id.answer_01_button_07);
            answer_01_button_08 = (Button) itemView.findViewById(R.id.answer_01_button_08);
            answer_01_button_09 = (Button) itemView.findViewById(R.id.answer_01_button_09);
            answer_01_button_10 = (Button) itemView.findViewById(R.id.answer_01_button_10);
            answer_01_numbwe_01 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_01);
            answer_01_numbwe_02 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_02);
            answer_01_numbwe_03 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_03);
            answer_01_numbwe_04 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_04);
            answer_01_numbwe_05 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_05);
            answer_01_numbwe_06 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_06);
            answer_01_numbwe_07 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_07);
            answer_01_numbwe_08 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_08);
            answer_01_numbwe_09 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_09);
            answer_01_numbwe_10 = (TextView) itemView.findViewById(R.id.answer_01_numbwe_10);
            id_flowlayout = (TagFlowLayout) itemView.findViewById(R.id.id_flowlayout);
            isSpeaking = (ImageView) itemView.findViewById(R.id.iv_isSpeaking);
        }
    }

    /*
    * 视频viwHolder
    * */
    private class ViewHolder_video extends RecyclerView.ViewHolder {
        ImageView mPersion_Pic;
        TextView mClock;
        SurfaceView mVideo;

        private ViewHolder_video(View itemView) {
            super(itemView);
            mPersion_Pic = (ImageView) itemView.findViewById(R.id.PersonPic);
            mClock = (TextView) itemView.findViewById(R.id.txt_time);
            mVideo = (SurfaceView) itemView.findViewById(R.id.video);
        }
    }

    /*
    * 音频viwHolder
    * */
    private class ViewHolder_audio extends RecyclerView.ViewHolder {
        ImageView mPersion_Pic;
        TextView mClock;
        ImageButton start_voice;
        CircleImageView clockIcon;

        private ViewHolder_audio(View itemView) {
            super(itemView);
            mPersion_Pic = (ImageView) itemView.findViewById(R.id.PersonPic);
            mClock = (TextView) itemView.findViewById(R.id.txt_time);
            start_voice = (ImageButton) itemView.findViewById(R.id.start_voice);
            clockIcon = (CircleImageView) itemView.findViewById(R.id.clock);
        }
    }


    /*
    * 图片viwHolder
    * */
    private class ViewHolder_more extends RecyclerView.ViewHolder {
        ImageView mPersion_Pic,isSpeaking;
        TextView mClock;
        RichText mWebView;

        private ViewHolder_more(View itemView) {
            super(itemView);
            mPersion_Pic = (ImageView) itemView.findViewById(R.id.PersonPic);
            mClock = (TextView) itemView.findViewById(R.id.txt_time);
            mWebView = (RichText) itemView.findViewById(R.id.more_webview);
            isSpeaking = (ImageView) itemView.findViewById(R.id.iv_isSpeaking);
        }
    }

    /*ViewHolder_pic
    * 富文本viwHolder   ViewHolder_more
    * */
    private class ViewHolder_pic extends RecyclerView.ViewHolder {
        ImageView mPersion_Pic;
        TextView mClock;
        ImageView rich_01, rich_02, rich_03, rich_04, rich_05, rich_06, rich_07, rich_08, rich_09, rich_10;
        RelativeLayout one_more, two_more, three_more,
                four_more, five_more,
                six_more, seven_more,
                eight_more, nine_more,
                ten_more;
        TextView text_01, text_02, text_03, text_04, text_05, text_06, text_07, text_08, text_09, text_10;

        private ViewHolder_pic(View itemView) {
            super(itemView);
            mPersion_Pic = (ImageView) itemView.findViewById(R.id.PersonPic);
            mClock = (TextView) itemView.findViewById(R.id.txt_time);
            rich_01 = (ImageView) itemView.findViewById(R.id.rich_01);
            rich_02 = (ImageView) itemView.findViewById(R.id.rich_02);
            rich_03 = (ImageView) itemView.findViewById(R.id.rich_03);
            rich_04 = (ImageView) itemView.findViewById(R.id.rich_04);
            rich_05 = (ImageView) itemView.findViewById(R.id.rich_05);
            rich_06 = (ImageView) itemView.findViewById(R.id.rich_06);
            rich_07 = (ImageView) itemView.findViewById(R.id.rich_07);
            rich_08 = (ImageView) itemView.findViewById(R.id.rich_08);
            rich_09 = (ImageView) itemView.findViewById(R.id.rich_09);
            rich_10 = (ImageView) itemView.findViewById(R.id.rich_10);
            text_01 = (TextView) itemView.findViewById(R.id.text_01);
            text_02 = (TextView) itemView.findViewById(R.id.text_02);
            text_03 = (TextView) itemView.findViewById(R.id.text_03);
            text_04 = (TextView) itemView.findViewById(R.id.text_04);
            text_05 = (TextView) itemView.findViewById(R.id.text_05);
            text_06 = (TextView) itemView.findViewById(R.id.text_06);
            text_07 = (TextView) itemView.findViewById(R.id.text_07);
            text_08 = (TextView) itemView.findViewById(R.id.text_08);
            text_09 = (TextView) itemView.findViewById(R.id.text_09);
            text_10 = (TextView) itemView.findViewById(R.id.text_10);
            two_more = (RelativeLayout) itemView.findViewById(R.id.two_more);
            one_more = (RelativeLayout) itemView.findViewById(R.id.one_more);
            three_more = (RelativeLayout) itemView.findViewById(R.id.three_more);
            four_more = (RelativeLayout) itemView.findViewById(R.id.four_more);
            five_more = (RelativeLayout) itemView.findViewById(R.id.five_more);
            six_more = (RelativeLayout) itemView.findViewById(R.id.six_more);
            seven_more = (RelativeLayout) itemView.findViewById(R.id.seven_more);
            eight_more = (RelativeLayout) itemView.findViewById(R.id.eight_more);
            nine_more = (RelativeLayout) itemView.findViewById(R.id.nine_more);
            ten_more = (RelativeLayout) itemView.findViewById(R.id.ten_more);
        }
    }

    /**
     * 这是一个添加一条数据并刷新界面的方法
     *
     * @param msg
     */
    public void addData(GetRobatResult msg) {
        mList.add(mList.size(), msg);
        notifyItemInserted(mList.size());
        // TODO: 2017/10/31 数据做更新
        notifyDataSetChanged();
    }

    /**
     * 这是一个清楚数据
     *
     * @param msg
     */
    public void setClear(GetRobatResult msg) {
        mList.clear();
        notifyDataSetChanged();
    }


    public int getTotalCount() {
        return mList.size();
    }

    /*
   * 设置图片点击接口（可扩展）
   * */
    private OnRecycleViewImageClickListenter mOnRecycleViewImageClickListenter = null;


    public interface OnRecycleViewImageClickListenter {
        void setImageClick(View view, int position);
    }

    public void setOnRecycleViewImageClickListenter(OnRecycleViewImageClickListenter onRecycleViewImageClickListenter) {
        this.mOnRecycleViewImageClickListenter = onRecycleViewImageClickListenter;
    }

    /*
       * 设置视频点击接口（可扩展）
       * */
    private OnRecycleViewVideoClickListenter mOnRecycleViewVideoClickListenter = null;

    public interface OnRecycleViewVideoClickListenter {
        void setVideoClick(View view, int position);
    }

    public void setOnRecycleViewVideoClickListenter(OnRecycleViewVideoClickListenter mOnRecycleViewVideoClickListenter) {
        this.mOnRecycleViewVideoClickListenter = mOnRecycleViewVideoClickListenter;
    }

    /*
       * 设置文字点击接口（可扩展）
       * */
    private OnRecycleViewTextClickListenter mOnRecycleViewTextClickListenter = null;

    public interface OnRecycleViewTextClickListenter {
        void setTextClick(View view, int position, int index, int type);
    }

    public void setOnRecycleViewTextClickListenter(OnRecycleViewTextClickListenter mOnRecycleViewTextClickListenter) {
        this.mOnRecycleViewTextClickListenter = mOnRecycleViewTextClickListenter;
    }

    /*
    * 设置音频点击接口（可扩展）
    * */
    private OnRecycleViewAudioClickListenter mOnRecycleViewAudioClickListenter = null;

    public interface OnRecycleViewAudioClickListenter {
        void setAudioStart(View view, int position);

        void setAudioProgress(View view, int position, int progress);
    }

    public void setOnRecycleViewAudioClickListenter(OnRecycleViewAudioClickListenter mOnRecycleViewAudioClickListenter) {
        this.mOnRecycleViewAudioClickListenter = mOnRecycleViewAudioClickListenter;
    }

    /*
       * 设置富媒体点击接口（可扩展）
       * */
    private OnRecycleViewRichMediaClickListenter mOnRecycleViewRichMediaClickListenter = null;

    public interface OnRecycleViewRichMediaClickListenter {
        void setRichMediaClick(View view, int position, int index);


    }

    public void setOnRecycleViewRichMediaClickListenter(OnRecycleViewRichMediaClickListenter mOnRecycleViewRichMediaClickListenter) {
        this.mOnRecycleViewRichMediaClickListenter = mOnRecycleViewRichMediaClickListenter;
    }

    public void setWebViewDestory() {
        //关闭webview
//        if (null != mWebView) {
//            mWebView.destroyDrawingCache();
//            mWebView.removeAllViews();
//            mWebView.destroy();
//        }
//        if (null != text_show_webview) {
//            text_show_webview.destroyDrawingCache();
//            text_show_webview.removeAllViews();
//            text_show_webview.destroy();
//        }
    }

    private OnRecycleViewisSpeakingClickListenter mOnRecycleViewisSpakingClickListenter = null;

    public interface OnRecycleViewisSpeakingClickListenter {
        void setisSpeakingClick(ImageView imageview, int position, boolean isSpeaking);
    }
    public void setOnRecycleViewisSpeakingClickListenter(OnRecycleViewisSpeakingClickListenter mOnRecycleViewRichMediaClickListenter) {
        this.mOnRecycleViewisSpakingClickListenter = mOnRecycleViewRichMediaClickListenter;
    }
}
