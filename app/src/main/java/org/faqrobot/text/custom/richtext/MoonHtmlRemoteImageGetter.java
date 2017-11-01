package org.faqrobot.text.custom.richtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.net.URI;

/**
 * Created by org.faqrobot.armynewspaper.sdk.moon on 15/11/27.
 */
public class MoonHtmlRemoteImageGetter implements Html.ImageGetter {
    final TextView container;
    URI baseUri;
    final Adapter adapter;
    private boolean fullImage = false;
    private Context mConText;

    public static abstract class Adapter {
        /**
         * the replace Image
         *
         * @return
         */
        public abstract Drawable getDefaultDrawable();

        /**
         * the error Image
         *
         * @return
         */
        public abstract Drawable getErrorDrawable();

    }

    public MoonHtmlRemoteImageGetter fullImage(boolean b) {
        fullImage = b;
        return this;
    }


    public MoonHtmlRemoteImageGetter(TextView t, String baseUrl, Adapter adapter, Context context) {
        this.container = t;
        this.adapter = adapter;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
        this.mConText = context;
    }

    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        final Drawable defaultImage;
        if (null != adapter && (defaultImage = adapter.getDefaultDrawable()) != null) {
            urlDrawable.setDrawable(defaultImage, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
        }
        /**
         * use volley
         */
        Glide.with(mConText).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(bitmap);
                urlDrawable.setDrawable(drawable, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
                //container.invalidate();
                container.setText(container.getText());
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);

                final Drawable errorImage;
                if (null != adapter && (errorImage = adapter.getErrorDrawable()) != null) {
                    urlDrawable.setDrawable(errorImage, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
                }
            }
        });
        // TODO: 2017/2/22  更换图片加载
//        final ImageRequest imageRequest = new ImageRequest(
//                source,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        //response.getWidth()
//                        //Log.d("DRAWABLE","download size:" + response.getWidth()+ "," + response.getHeight());
//                        Drawable drawable = new BitmapDrawable(response);
//                        urlDrawable.setDrawable(drawable, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
//                        //container.invalidate();
//                        container.setText(container.getText());
//                    }
//                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                final Drawable errorImage;
//                if (null != adapter && (errorImage = adapter.getErrorDrawable())!=null){
//                    urlDrawable.setDrawable(errorImage, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
//                }
//            }
//        });
//        Volley.newRequestQueue(container.getContext()).add(imageRequest);

        return urlDrawable;
    }


    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        private void setDrawable(Drawable nDrawable, int maxWidth) {

            if (drawable != null) {
                drawable = null;
            }

            Log.d("DRAWABLE", "get size:" + nDrawable.getIntrinsicWidth() + "," + nDrawable.getIntrinsicHeight());
            //XLog.d("htmlremote: width:" + width);
            drawable = nDrawable;

            int width_dp = nDrawable.getIntrinsicWidth();
            int height_dp = nDrawable.getIntrinsicHeight();
            int width_px = MoonDensityUtil.dip2px(container.getContext(), width_dp);
            int height_px = MoonDensityUtil.dip2px(container.getContext(), height_dp);
            //too large,adapter screen,but the maxwidth can be zero.
            if (maxWidth > 0) {
                height_px = width_px > maxWidth ? height_px * (width_px / maxWidth) : height_px;
                width_px = width_px > maxWidth ? maxWidth : width_px;
            }
            if (fullImage) {
                height_px = (int) ((maxWidth * height_px) / (width_px * 1.0));
                width_px = maxWidth;
            }
            drawable.setBounds(0, 0, width_px, height_px);
            setBounds(0, 0, width_px, height_px);


        }

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }


    }

}
