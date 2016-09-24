package com.mcxtzhang.cstnorecyclelistview.nestrv;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.cstnorecyclelistview.R;
import com.mcxtzhang.cstnorecyclelistview.bean.NestBean;
import com.mcxtzhang.cstnorecyclelistview.bean.TestBean;
import com.mcxtzhang.zxtcommonlib.recyclerview.CommonAdapter;
import com.mcxtzhang.zxtcommonlib.recyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NestRecyclerViewActivity extends Activity {
    private static final String TAG = "zxt/NestRecyclerView";
    private RecyclerView mRv;
    private CommonAdapter<TestBean> mAdapter;
    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_recycler_view);

        initDatas();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter = new CommonAdapter<TestBean>(this, R.layout.item_nest_rv_1, mDatas) {
            @Override
            public void convert(ViewHolder holder, TestBean testBean) {
                holder.setText(R.id.tv, testBean.getName());
                Glide.with(mContext)
                        .load(testBean.getUrl())
                        .into((ImageView) holder.getView(R.id.iv));
                RecyclerView nestRv = holder.getView(R.id.nestRv);
                nestRv.setLayoutManager(new LinearLayoutManager(nestRv.getContext()));
                nestRv.setAdapter(new CommonAdapter<NestBean>(nestRv.getContext(), R.layout.item_nest_rv_2, testBean.getNest()) {
                    @Override
                    public void convert(ViewHolder holder, NestBean nestBean) {
                        Glide.with(mContext)
                                .load(nestBean.getUrl())
                                .into((ImageView) holder.getView(R.id.nestIv));

                    }
                });


            }
        });

    }


    private void initDatas() {
        int i = 0;
        mDatas = new ArrayList<>();
        ArrayList<NestBean> nestBeen = new ArrayList<>();
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));


        mDatas.add(new TestBean((i++) + "", "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg", nestBeen));

        nestBeen = new ArrayList<>();
        nestBeen.add(new NestBean("http://imgs.ebrun.com/resources/2016_03/2016_03_24/201603244791458784582125_origin.jpg"));
        nestBeen.add(new NestBean("http://www.wccdaily.com.cn/hxdsb/20151204/6f443028313f1888b7a9fb19549d6ef6.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://fudaoquan.com/wp-content/uploads/2016/04/wanghong.jpg", nestBeen));


        nestBeen = new ArrayList<>();
        nestBeen.add(new NestBean("http://img.mp.itc.cn/upload/20160427/316a154e56684a59b1e81df03a0860c4_th.png"));
        nestBeen.add(new NestBean("http://cdn.duitang.com/uploads/item/201509/17/20150917161810_exXGU.jpeg"));
        mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", nestBeen));


        mDatas.add(new TestBean((i++) + "", "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://www.kejik.com/image/1460343965520.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
        mDatas.add(new TestBean((i++) + "", "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));


    }

    public void add(View view) {
        mDatas.add(new TestBean("add", "http://finance.gucheng.com/UploadFiles_7830/201603/2016032110220685.jpg"));
        mAdapter.notifyDataSetChanged();
    }

    public void del(View view) {
        mDatas.remove(mDatas.size() - 1);
        mAdapter.notifyDataSetChanged();
    }

}
