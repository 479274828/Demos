package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.outpututils.PathAnimHelper;

/**
 * 介绍：一个路径动画的View
 * 利用源Path绘制“底”
 * 利用动画Path 绘制 填充动画
 * <p>
 * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画,
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class PathAnimView extends View {
    protected Path mSourcePath;//需要做动画的源Path
    protected Path mAnimPath;//用于绘制动画的Path
    protected Paint mPaint;
    protected PathAnimHelper mPathAnimHelper;//Path动画工具类

    public PathAnimView(Context context) {
        this(context, null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * GET SET FUNC
     **/
    public Path getSourcePath() {
        return mSourcePath;
    }

    /**
     * 这个方法可能会经常用到，用于设置源Path
     * @param sourcePath
     * @return
     */
    public PathAnimView setSourcePath(Path sourcePath) {
        mSourcePath = sourcePath;
        initAnimHelper();
        return this;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public PathAnimView setPaint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public PathAnimHelper getPathAnimHelper() {
        return mPathAnimHelper;
    }

    public PathAnimView setPathAnimHelper(PathAnimHelper pathAnimHelper) {
        mPathAnimHelper = pathAnimHelper;
        initAnimHelper();
        return this;
    }

    /**
     * INIT FUNC
     **/
    protected void init() {
        //Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //动画路径只要初始化即可
        mAnimPath = new Path();

        //初始化动画帮助类
        initAnimHelper();

    }

    /**
     * //初始化动画帮助类
     */
    private void initAnimHelper() {
        mPathAnimHelper = new PathAnimHelper(this, mSourcePath, mAnimPath);
        //mPathAnimHelper = new PathAnimHelper(this, mSourcePath, mAnimPath, 1500, true);
    }

    /**
     * draw FUNC
     **/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //平移
        canvas.translate(20, 20);
        setBackgroundColor(Color.BLACK);
        mPaint.setColor(Color.GRAY);
        canvas.drawPath(mSourcePath, mPaint);


        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mAnimPath, mPaint);
    }

    /**
     * 设置动画 循环
     */
    public PathAnimView setAnimInfinite(boolean infinite) {
        mPathAnimHelper.setInfinite(infinite);
        return this;
    }

    /**
     * 设置动画 总时长
     */
    public PathAnimView setAnimTime(long animTime) {
        mPathAnimHelper.setAnimTime(animTime);
        return this;
    }

    /**
     * 执行循环动画
     */
    public void startAnim() {
        mPathAnimHelper.startAnim();
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        mPathAnimHelper.stopAnim();
    }

    /**
     * 重置
     */
    public void resetAnim() {
        stopAnim();
        mAnimPath.reset();
        mAnimPath.lineTo(0, 0);
        invalidate();
    }
}
