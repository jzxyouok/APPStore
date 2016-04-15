package com.ljb.game.torn.tool;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

public class ImageResource {

    private List<Bitmap> bitmapResource;
    private static ImageResource imageResource = new ImageResource();

    private static int mPers[];
    private static int mAfters[];

    private ImageResource() {
        bitmapResource = new ArrayList<Bitmap>();
        mPers = new int[]{R.drawable.pre1, R.drawable.pre2, R.drawable.pre3,
                R.drawable.pre4, R.drawable.pre5, R.drawable.pre6, R.drawable.pre7,
                R.drawable.pre8, R.drawable.pre9, R.drawable.pre10, R.drawable.pre11,
                R.drawable.pre12, R.drawable.pre13, R.drawable.pre14, R.drawable.pre15,
                R.drawable.pre16, R.drawable.pre17, R.drawable.pre18
        };
        mAfters = new int[]{R.drawable.after1, R.drawable.after2, R.drawable.after3,
                R.drawable.after4, R.drawable.after5, R.drawable.after6,
                R.drawable.after7, R.drawable.after8, R.drawable.after9,
                R.drawable.after10, R.drawable.after11, R.drawable.after12,
                R.drawable.after13, R.drawable.after14, R.drawable.after15,
                R.drawable.after16,R.drawable.after17, R.drawable.after18
        };

        loadingBitmap(XgoUIUtils.getScreenWidth() , 5);
    }

    public static ImageResource getImageResource() {
        return imageResource;
    }

    public Bitmap getAfterBitmap(Resources resources, int num) {//构造方法

        Bitmap bitmap = BitmapFactory.decodeResource(resources, mAfters[num]);

        return bitmap;
    }

    public Bitmap getPreBitmap(Resources resources, int num) {//构造方法

        Bitmap bitmap = BitmapFactory.decodeResource(resources, mPers[num]);//加载图片

        return bitmap;//返回BitMap类
    }

    public void loadingBitmap(int width, int num) {

        BitmapFactory.Options opts = new BitmapFactory.Options();//BitmapFactory.Options这个类
        //仅返回图片的 宽高  这样就不会占用太多的内存，也就不会那么频繁的发生OOM了。
        opts.inJustDecodeBounds = true;//该值设为true那么将不返回实际的bitmap对象，不给其分配内存空间但是可以得到一些解码边界信息即图片大小等信息。
        Bitmap temp = BitmapFactory.decodeResource(XgoUIUtils.getResources(), mPers[0], opts);//加载图片 缩放 mPers【】中第一位开始
        int radio = (int) Math.ceil(opts.outWidth / (width * 1.0 / num - 30));//向上取整 结果是7
        opts.inSampleSize = radio;//属性值inSampleSize表示缩略图大小为原始图片大小的几分之一
        if (null != temp) {
            temp.recycle();//回收
        }
        System.out.println(radio);

        opts.inJustDecodeBounds = false;//inJustDecodeBounds设为false，就可以根据已经得到的缩放比例得到自己想要的图片缩放图了。

        for (int i = 0; i < mPers.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(XgoUIUtils.getResources(), mPers[i], opts);//载入图片
            bitmapResource.add(bitmap);//循环添加到集合中
        }
    }

    public Bitmap getIconBitmap(int num) {
        return bitmapResource.get(num);
    }

    public int size() {
        return bitmapResource.size();
    }

    public int getProgress() {
        return (int) (100.0 * bitmapResource.size() / mPers.length);//得到文件当前大小
    }
}