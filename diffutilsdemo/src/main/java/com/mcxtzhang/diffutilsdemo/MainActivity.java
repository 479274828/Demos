package com.mcxtzhang.diffutilsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TestBean> mDatas;
    private RecyclerView mRv;
    private DiffAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this, mDatas);
        //mAdapter.setData(mDatas);
        mRv.setAdapter(mAdapter);
    }

    public void change(View view) {
        try {
            ArrayList<TestBean> newDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                newDatas.add(bean.clone());
            }
            newDatas.add(new TestBean("赵子龙", "帅", R.drawable.pic6));
            newDatas.get(0).setDesc("Android+");
            newDatas.get(0).setPic(R.drawable.pic7);
            TestBean testBean = newDatas.get(1);
            newDatas.remove(testBean);
            newDatas.add(testBean);

            //新宠
            //DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
            //diffResult.dispatchUpdatesTo(mAdapter);
            //别忘了将新数据给Adapter
            mDatas = newDatas;
            mAdapter.setDatas(mDatas);

            mAdapter.notifyDataSetChanged();//以前我们只能这样，现在我们有新宠了  ，实验二 验证notifyDataSetChanged getAdapterPosition为-1，也用的上

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("张旭童1", "Android", R.drawable.pic1));
        mDatas.add(new TestBean("张旭童2", "Java", R.drawable.pic2));
        mDatas.add(new TestBean("张旭童3", "背锅", R.drawable.pic3));
        mDatas.add(new TestBean("张旭童4", "手撕产品", R.drawable.pic4));
        mDatas.add(new TestBean("张旭童5", "手撕测试", R.drawable.pic5));
    }

}
