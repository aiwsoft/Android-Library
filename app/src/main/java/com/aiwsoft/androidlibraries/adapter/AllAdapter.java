package com.aiwsoft.androidlibraries.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiwsoft.androidlibraries.AllActivity;
import com.aiwsoft.androidlibraries.MainFragment;
import com.aiwsoft.androidlibraries.R;
import com.aiwsoft.androidlibraries.application.MyApp;

import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.DataHolder> {
    private List<String> listdata;
    private List<String> displayedList;
    private LayoutInflater inflater;
    private Fragment c;

    public AllAdapter(List<String> listdata, Fragment c) {
        this.inflater = LayoutInflater.from(c.getActivity());
        this.listdata = listdata;
        displayedList = listdata;
        this.c = c;
    }


    @Override
    public AllAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_all, parent, false);
        return new AllAdapter.DataHolder(view);
    }

    @Override
    public void onBindViewHolder(AllAdapter.DataHolder holder, int position) {
        String item = displayedList.get(position);
        holder.title.setText(item.split("@@")[0]);
        holder.description.setText(item.split("@@")[2]);

        Layout l = holder.description.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
//                    Log.d(TAG, "Text is ellipsized");
                    holder.txt_read_more.setVisibility(View.VISIBLE);
                } else {
                    holder.txt_read_more.setVisibility(View.INVISIBLE);
                }

        }
    }

    @Override
    public int getItemCount() {
        return displayedList.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView txt_read_more;

        public DataHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            txt_read_more = itemView.findViewById(R.id.txt_read_more);
            itemView.setOnClickListener(this);
            txt_read_more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_read_more) {
                MyApp.popMessage("", listdata.get(getLayoutPosition()).split("@@")[2], c.getActivity());
                return;
            }
            ((MainFragment) c).openWebView(displayedList.get(getLayoutPosition()));
        }
    }

    public void updateList(List<String> list) {
        displayedList = list;
        notifyDataSetChanged();

    }
}


