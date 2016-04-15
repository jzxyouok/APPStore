package com.ljb.game.torn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ljb.game.torn.tool.ImageResource;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;

/**
 * Created by Ljb on 2015/11/17.
 */
public class ClsActivity extends BaseActivity {

    private GridView mGridview;
    private ImageResource mImageRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls);
        mGridview = (GridView) findViewById(R.id.gridview);

        mImageRes = ImageResource.getImageResource();
        mGridview.setAdapter(new GradViewAdapter());
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClsActivity.this , ShowGirlActivity.class);
                intent.putExtra("num" , position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageRes = null;
    }

    class GradViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImageRes.size();
        }

        @Override
        public Bitmap getItem(int position) {
            return mImageRes.getIconBitmap(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            final ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(ClsActivity.this, R.layout.item_cls, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.imageView.setImageBitmap(getItem(position));
            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
