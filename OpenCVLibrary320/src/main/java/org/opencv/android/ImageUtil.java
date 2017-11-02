package org.opencv.android;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by 孟晨 on 2017/8/20.
 */

public class ImageUtil {
    /**
     * ��תBitmap
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
        Matrix matrix = new Matrix();
        matrix.postRotate((float)rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }

    /**
     * 图片选旋转的方法
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);
            try {
                Bitmap tempBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                if (bitmap != tempBm) {
                    // bitmap回收
                    bitmap.recycle();
                    bitmap = tempBm;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}