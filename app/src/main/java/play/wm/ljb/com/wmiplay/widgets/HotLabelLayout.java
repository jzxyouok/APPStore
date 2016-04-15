package play.wm.ljb.com.wmiplay.widgets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ljb on 2015/11/13.
 */
public class HotLabelLayout extends ViewGroup {

    /**
     * 水平间距
     */
    private int horizontalSpace = 20;
    /**
     * 垂直间距
     */
    private int verticalSpace = 20;


    /**
     * 当前行已使用的空间
     */
    private int mUsedLineWidth = 0;
    /**
     * 当前行
     */
    private Line mCurrentLine;
    /**
     * 已使用过的行集合
     */
    private List<Line> mUsedLines;

    public HotLabelLayout(Context context) {
        super(context);
        mUsedLines = new ArrayList<>();
        mNeedLayout = true;
    }

    private boolean mNeedLayout;

    /**
     * 分配孩子的位子
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!mNeedLayout || changed) {
            mNeedLayout = false;
            //起始点
            int left = getPaddingLeft();
            int top = getPaddingTop();
            //由行自身去分配
            for (int i = 0; i < mUsedLines.size(); i++) {
                mUsedLines.get(i).onLayout(left, top);
                top += mUsedLines.get(i).getHeight() + verticalSpace;   //计算下一行坐标
            }
        }
    }

    /**
     * 注意：如果自身是容器
     * 在测量时需要先测量容器中孩子的大小 ， 再测量自身大小 ， 并在onLayout中为孩子分配大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取自身实际高、宽
        int mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int mHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        //自身的测量模式
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        restoreLine();// 还原所有数据

        //测量孩子大小
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            //孩子宽度、高度最大不能超过父容器
            int childViewWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, mWidthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
                    : mWidthMode);
            int childViewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, mHeightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
                    : mHeightMode);

            //开始测量
            childView.measure(childViewWidthMeasureSpec, childViewHeightMeasureSpec);

            //获取当前行
            if (mCurrentLine == null) {
                mCurrentLine = new Line();
            }
            //测量后的孩子宽、高
            int childViewMeasuredWidth = childView.getMeasuredWidth();
           // int childViewMeasuredHeight = childView.getMeasuredHeight();

            //加入孩子宽度
            mUsedLineWidth += childViewMeasuredWidth;

            if (mUsedLineWidth <= mWidth) {
                //未超出父容器宽度
                mCurrentLine.addChildView(childView);
                //加入水平间距
                mUsedLineWidth += horizontalSpace;
                //加入间距，超出宽度，换行
                if (mUsedLineWidth >= mWidth) {
                    newLine();
                }
            } else {
                if (mCurrentLine.getChildCount() == 0) {
                    //当前行没有孩子，且装不下当前孩子，强制装入孩子后，换行
                    mCurrentLine.addChildView(childView);
                    newLine();
                } else {
                    //当前行超出父容器宽度,换行,再新的行中添加孩子
                    newLine();
                    mCurrentLine.addChildView(childView);
                    mUsedLineWidth +=childViewMeasuredWidth+horizontalSpace;
                }
            }
        }

        //记录最后一行
        if (mCurrentLine != null  && !mUsedLines.contains(mCurrentLine)) {
            mUsedLines.add(mCurrentLine);
        }

        //计算计入孩子后的父容器高度
        int totalHeight = 0;
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);

        for (int i = 0; i < mUsedLines.size(); i++) {
            totalHeight += mUsedLines.get(i).getHeight();
        }
        totalHeight += verticalSpace * (mUsedLines.size() - 1);
        totalHeight += getPaddingTop() + getPaddingBottom();

        //测量自身尺寸,宽度为原先父容器宽度，高度为总和高度，如果不够一屏高度，取原先父容器高度
        setMeasuredDimension(totalWidth,resolveSize(totalHeight, heightMeasureSpec));
    }

    /**
     * 由于会多次onMeasure() ， 防止错乱 ，重置数据
     */
    private void restoreLine() {
        mUsedLines.clear();
        mCurrentLine = new Line();
        mUsedLineWidth = 0;
    }

    private void newLine() {
        //记录已使用的行
        if (mCurrentLine != null) {
            mUsedLines.add(mCurrentLine);
        }
        //创建新的一行，初始化使用宽度
        mCurrentLine = new Line();
        mUsedLineWidth = 0;
    }

    /**
     * 代表行的对象
     */
    class Line {
        /**
         *最大孩子高度
         */
        private int mChildMaxHeight = 0;
        /**
         * 孩子总宽度
         * */
        private int mChildCountWidth = 0;

        /**
         * 当前行收纳孩子的容器
         */
        private List<View> childern;


        public Line() {
            childern = new ArrayList<>();
        }

        /**
         * 在当前行添加一个孩子
         */
        public void addChildView(View childView) {
            childern.add(childView);
            mChildCountWidth += childView.getMeasuredWidth();
        }

        public int getChildCount() {
            return childern.size();
        }

        public int getHeight() {
            for (int i = 0; i < childern.size(); i++) {
                int height = childern.get(i).getMeasuredHeight();
                if (height > mChildMaxHeight) {
                    mChildMaxHeight = height;
                }
            }
            return mChildMaxHeight;
        }

        /**
         * 分配孩子坐标
         */
        public void onLayout(int l, int t) {
            int left=l;
            int top=t;
            int count =getChildCount();
            // 总宽度
            int layoutWidth=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
            // 剩余宽度,是除了View和间隙的剩余空间
            int surplusWidth=layoutWidth- mChildCountWidth -horizontalSpace*(count-1);
            if(surplusWidth>=0){
                //采用float类型数据计算后四舍五入能减少int类型计算带来的误差
                int  splitSpacing=(int) (surplusWidth/count*1.0+0.5);
                for(int i=0;i<count;i++){
                    View view=childern.get(i);
                    int childWidth=view.getMeasuredWidth();
                    int childHeight=view.getMeasuredHeight();
                    // 计算出每个View的顶点 ,是由最高的View和该view高度差值除以2
                    int topOffset=(int) ((mChildMaxHeight-childHeight)/2.0+0.5);
                    if(topOffset<0){
                        topOffset=0;
                    }
                    // 把剩余空间平均到每个View上
                    childWidth=childWidth+splitSpacing;
                    // 修改View的宽度
                    view.getLayoutParams().width=childWidth;
                    if(splitSpacing>0){
                        // view宽度改变需要重新测量
                        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                        view.measure(widthMeasureSpec, heightMeasureSpec);
                    }
                    //布局View
                    view.layout(left, top+topOffset, left+childWidth, top+childHeight+topOffset);
                    left+=childWidth+horizontalSpace;//为下一个View的left赋值
                }
            }else{
                if(count==1){
                    View view=childern.get(0);
                    view.layout(left, top, view.getMeasuredWidth()+left, top+view.getMeasuredHeight());
                }
            }

        }
    }
}
