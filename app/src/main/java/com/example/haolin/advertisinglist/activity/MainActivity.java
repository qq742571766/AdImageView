package com.example.haolin.advertisinglist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.haolin.advertisinglist.R;
import com.example.haolin.advertisinglist.view.AdImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.example.haolin.advertisinglist.constant.Constants.CONSTANTS_NUMBER_ONE_HUNDRED;
import static com.example.haolin.advertisinglist.constant.Constants.CONSTANTS_NUMBER_SEVEN;
import static com.example.haolin.advertisinglist.constant.Constants.CONSTANTS_NUMBER_ZERO;

/**
 * @author haoli
 */
public class MainActivity extends AppCompatActivity {
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = findViewById(R.id.id_recyclerview);
        List<String> mockData = new ArrayList<>();
        for (int i = CONSTANTS_NUMBER_ZERO; i < CONSTANTS_NUMBER_ONE_HUNDRED; i++) {
            mockData.add(String.valueOf(i));
        }
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommonAdapter<String>(MainActivity.this, R.layout.item, mockData) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                if (position > CONSTANTS_NUMBER_ZERO && position % CONSTANTS_NUMBER_SEVEN == CONSTANTS_NUMBER_ZERO) {
                    holder.setVisible(R.id.id_tv_title, false);
                    holder.setVisible(R.id.id_tv_desc, false);
                    holder.setVisible(R.id.id_iv_ad, true);
                } else {
                    holder.setVisible(R.id.id_tv_title, true);
                    holder.setVisible(R.id.id_tv_desc, true);
                    holder.setVisible(R.id.id_iv_ad, false);
                }
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    AdImageView adImageView = view.findViewById(R.id.id_iv_ad);
                    if (adImageView.getVisibility() == View.VISIBLE) {
                        adImageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());
                    }
                }
            }
        });
    }
}