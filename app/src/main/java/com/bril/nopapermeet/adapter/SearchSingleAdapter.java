package com.bril.nopapermeet.adapter;

import android.content.Context;

import com.bril.nopapermeet.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SearchSingleAdapter extends QuickAdapter<String>{


    public SearchSingleAdapter(Context context, ArrayList<String> arrayList) {
        super(context, R.layout.item_search_single_fragment,arrayList);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, String item) {
        helper.setText(R.id.item_search_single_fragment_tv,item);
    }
}
