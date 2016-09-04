package mcxtzhang.itemdecorationdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mcxtzhang.itemdecorationdemo.R;
import mcxtzhang.itemdecorationdemo.bean.BaseIndexPinyinBean;

/**
 * 介绍：索引右侧边栏
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/04.
 */

public class IndexBar extends View {
    private static final String TAG = "zxt/IndexBar";
    public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};//#在最后面（默认的数据源）
    private List<String> mIndexDatas;//索引数据源
    private boolean isNeedRealIndex;//是否需要根据实际的数据来生成索引数据源（例如 只有 A B C 三种tag，那么索引栏就 A B C 三项）

    private int mWidth, mHeight;//View的宽高
    private int mGapHeight;//每个index区域的高度

    private Paint mPaint;

    private int mPressedBackground;//手指按下时的背景色

    //以下边变量是外部set进来的
    private TextView mPressedShowTextView;//用于特写显示正在被触摸的index值
    private List<? extends BaseIndexPinyinBean> mSourceDatas;//Adapter的数据源
    private LinearLayoutManager mLayoutManager;

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());//默认的TextSize
        mPressedBackground = Color.BLACK;//
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.IndexBar_textSize:
                    textSize = typedArray.getDimensionPixelSize(attr, textSize);
                    break;
                case R.styleable.IndexBar_pressBackground:
                    mPressedBackground = typedArray.getColor(attr, mPressedBackground);
                default:
                    break;
            }
        }
        typedArray.recycle();

        if (!isNeedRealIndex) {//不需要真实的索引数据源
            mIndexDatas = Arrays.asList(INDEX_STRING);
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(Color.BLACK);

        //设置index触摸监听器
        setmOnIndexPressedListener(new onIndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                if (mPressedShowTextView != null) { //显示hintTexView
                    mPressedShowTextView.setVisibility(View.VISIBLE);
                    mPressedShowTextView.setText(text);
                }
                //滑动Rv
                if (mLayoutManager != null) {
                    int position = getPosByTag(text);
                    if (position != -1) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int l = getPaddingLeft();
        int t = getPaddingTop();


        Rect indexBounds = new Rect();
        String index;
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);

            mPaint.getTextBounds(index, 0, index.length(), indexBounds);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

            int baseY = (int) (1 / 2 * mGapHeight + 1 / 2 * (fontMetrics.descent - fontMetrics.ascent) - fontMetrics.bottom);

            int baseline = (int) ((mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2);

            Log.d(TAG, "onDraw() called with: canvas = [" + (fontMetrics.descent - fontMetrics.ascent) + "]" + ((fontMetrics.bottom - fontMetrics.top)) + ",boud:" + indexBounds.height());
            canvas.drawText(index, mWidth / 2 - indexBounds.width() / 2, t + mGapHeight * i /*+ (gapHeight / 2 + indexBounds.height() / 2)*/ + baseline, mPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(mPressedBackground);
            case MotionEvent.ACTION_MOVE:

                float y = event.getY();
                //通过计算判断落点在哪个区域：

                int pressI = (int) ((y - getPaddingTop()) / mGapHeight);
                //边界处理
                if (pressI < 0) {
                    pressI = 0;
                } else if (pressI >= mIndexDatas.size()) {
                    pressI = mIndexDatas.size() - 1;
                }

                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onIndexPressed(pressI, mIndexDatas.get(pressI));
                }
                //滑动Rv
                if (mLayoutManager != null) {
                    int position = getPosByTag(mIndexDatas.get(pressI));
                    if (position != -1) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                setBackgroundResource(android.R.color.transparent);
                if (mPressedShowTextView != null) {
                    mPressedShowTextView.setVisibility(View.GONE);
                }
                break;
        }


        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mGapHeight = (mHeight - getPaddingTop() - getPaddingBottom()) / mIndexDatas.size();
    }


    /**
     * 当前被按下的index的监听器
     */
    public interface onIndexPressedListener {
        void onIndexPressed(int index, String text);
    }

    private onIndexPressedListener mOnIndexPressedListener;

    public onIndexPressedListener getmOnIndexPressedListener() {
        return mOnIndexPressedListener;
    }

    public void setmOnIndexPressedListener(onIndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }

    /**
     * 显示当前被按下的index的TextView
     *
     * @return
     */

    public IndexBar setmPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }

    public IndexBar setmLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }

    /**
     * 一定要在设置数据源{@link #setmSourceDatas(List)}之前调用
     *
     * @param needRealIndex
     * @return
     */
    public IndexBar setNeedRealIndex(boolean needRealIndex) {
        isNeedRealIndex = needRealIndex;
        if (mIndexDatas != null) {
            mIndexDatas = new ArrayList<>();
        }
        return this;
    }

    public IndexBar setmSourceDatas(List<? extends BaseIndexPinyinBean> mSourceDatas) {
        this.mSourceDatas = mSourceDatas;
        initSourceDatas();//对数据源进行初始化
        return this;
    }


    /**
     * 组织数据源
     *
     * @return
     */
    private void initSourceDatas() {
        int size = mSourceDatas.size();
        for (int i = 0; i < size; i++) {
            BaseIndexPinyinBean indexPinyinBean = mSourceDatas.get(i);
            StringBuilder pySb = new StringBuilder();
            String target = indexPinyinBean.getTarget();//取出需要被拼音化的字段
            //取出首个char得到它的拼音
            for (int i1 = 0; i1 < target.length(); i1++) {
                //如果c为汉字，则返回大写拼音；如果c不是汉字，则返回String.valueOf(c)
                pySb.append(Pinyin.toPinyin(target.charAt(i1)));
            }
            indexPinyinBean.setPyCity(pySb.toString());//设置城市名拼音

            //以下代码设置城市拼音首字母
            String tagString = pySb.toString().substring(0, 1);
            if (tagString.matches("[A-Z]")) {//如果是A-Z字母开头
                indexPinyinBean.setTag(tagString);
                if (isNeedRealIndex) {//如果需要真实的索引数据源
                    if (!mIndexDatas.contains(tagString)) {//则判断是否已经将这个索引添加进去，若没有则添加
                        mIndexDatas.add(tagString);
                    }
                }
            } else {//特殊字母这里统一用#处理
                indexPinyinBean.setTag("#");
                if (isNeedRealIndex) {//如果需要真实的索引数据源
                    if (!mIndexDatas.contains("#")) {
                        mIndexDatas.add("#");
                    }
                }
            }
        }
        sortData();
    }

    /**
     * 对数据源排序
     */
    private void sortData() {
        //对右侧栏进行排序 将 # 丢在最后
        Collections.sort(mIndexDatas, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                if (lhs.equals("#")) {
                    return 1;
                } else if (rhs.equals("#")) {
                    return -1;
                } else {
                    return lhs.compareTo(rhs);
                }
            }
        });

        //对数据源进行排序
        Collections.sort(mSourceDatas, new Comparator<BaseIndexPinyinBean>() {
            @Override
            public int compare(BaseIndexPinyinBean lhs, BaseIndexPinyinBean rhs) {
                if (lhs.getTag().equals("#")) {
                    return 1;
                } else if (rhs.getTag().equals("#")) {
                    return -1;
                } else {
                    return lhs.getPyCity().compareTo(rhs.getPyCity());
                }
            }
        });
    }


    /**
     * 根据传入的pos返回tag
     *
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mSourceDatas.size(); i++) {
            if (tag.equals(mSourceDatas.get(i).getTag())) {
                return i;
            }
        }
        return -1;
    }
}
