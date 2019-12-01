package com.example.fuellog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogListAdapter extends RecyclerView.Adapter<LogListAdapter.LogViewHolder> {

    private final LayoutInflater mInflater;
    private List<Log> mLogs;
    private static ClickListener clickListener;

    LogListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new LogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        if (mLogs != null) {
            Log current = mLogs.get(position);

            holder.logItemView.setText(current.getLog());
            holder.log2.setText(current.getLog2());
            holder.log3.setText(current.getLog3());


        } else {
            // Covers the case of data not being ready yet.
            holder.logItemView.setText("No Log");
        }
    }

    void setLogs(List<Log> logs){
        mLogs = logs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mLogs != null)
            return mLogs.size();
        else return 0;
    }
    public Log getLogAtPosition(int position) {
        return mLogs.get(position);
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        private final TextView logItemView;
        private final TextView log2;
        private final TextView log3;

        private LogViewHolder(View itemView) {
            super(itemView);
            logItemView = itemView.findViewById(R.id.textView);
            log2 = itemView.findViewById(R.id.textView2);
            log3 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        LogListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}

