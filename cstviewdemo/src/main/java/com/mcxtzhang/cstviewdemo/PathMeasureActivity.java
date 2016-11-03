package com.mcxtzhang.cstviewdemo;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.cstviewdemo.widget.LoadingPathAnimView;
import com.mcxtzhang.cstviewdemo.widget.PathAnimView;
import com.mcxtzhang.cstviewdemo.widget.StoreHouseAnimView;

public class PathMeasureActivity extends AppCompatActivity {
    LoadingPathAnimView fillView1;
    LoadingPathAnimView fillView2;
    StoreHouseAnimView storeView1;
    StoreHouseAnimView storeView2;
    PathAnimView pathAnimView1;
    StoreHouseAnimView storeView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);
        fillView1 = (LoadingPathAnimView) findViewById(R.id.fillView1);
        fillView2 = (LoadingPathAnimView) findViewById(R.id.fillView2);
        storeView1 = (StoreHouseAnimView) findViewById(R.id.storeView1);
        storeView2 = (StoreHouseAnimView) findViewById(R.id.storeView2);

        //动态设置Path实例
        pathAnimView1 = (PathAnimView) findViewById(R.id.pathAnimView1);
        Path sPath = new Path();
        sPath.moveTo(0, 0);
        sPath.addCircle(40, 40, 30, Path.Direction.CW);
        pathAnimView1.setSourcePath(sPath);

        storeView3 = (StoreHouseAnimView) findViewById(R.id.storeView3);
        sPath = new Path();
        for (int i = 1; i < 20; i=i+2) {
            sPath.moveTo(5 * i, 5 * i);
            sPath.lineTo(50 * i, 5 * i);
            sPath.moveTo(55 * i, 5 * i);
            sPath.lineTo(100 * i, 5 * i);
        }
        storeView3.setSourcePath(sPath);
    }

    public void start(View view) {
        fillView1.startAnim();
        fillView2.setAnimTime(3000).setAnimInfinite(false).startAnim();
        storeView1.startAnim();
        storeView2.setAnimTime(1000).startAnim();
        pathAnimView1.startAnim();
        storeView3.setAnimTime(10000).startAnim();
    }

    public void stop(View view) {
        fillView1.stopAnim();
        fillView2.stopAnim();
        storeView1.stopAnim();
        storeView2.stopAnim();
        pathAnimView1.stopAnim();
        storeView3.stopAnim();
    }

    public void reset(View view) {
        fillView1.resetAnim();
        fillView2.resetAnim();
        storeView1.resetAnim();
        storeView2.resetAnim();
        pathAnimView1.resetAnim();
        storeView3.resetAnim();
    }
}
