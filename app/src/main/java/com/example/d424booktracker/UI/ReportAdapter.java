package com.example.d424booktracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424booktracker.R;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.CombineSearch;
import com.example.d424booktracker.entities.ReportItem;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private final LayoutInflater mInflater;
    private List<ReportItem> reportItemList;
    private Context context;

    public ReportAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.report_book_item, parent, false);
        return new ReportAdapter.ReportViewHolder(itemView);
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView reportTitle, reportAuthor, reportStart, reportEnd;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reportTitle = itemView.findViewById(R.id.book_title);
            reportAuthor = itemView.findViewById(R.id.book_author);
            reportStart = itemView.findViewById(R.id.book_start_date);
            reportEnd = itemView.findViewById(R.id.book_end_date);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if(reportItemList !=null){
            ReportItem current= reportItemList.get(position);

            String name=current.getTitle();
            holder.reportTitle.setText(name);

            String title=current.getAuthor();
            holder.reportAuthor.setText(title);

            String start=current.getStartDate();
            holder.reportStart.setText(start);

            String end=current.getEndDate();
            holder.reportEnd.setText(end);
        }
        else{
            holder.reportTitle.setText("No book name");
        }

    }

    @Override
    public int getItemCount() {
        if (reportItemList != null) {
            return reportItemList.size();
        } else return 0;
    }

    public void setReport(List<ReportItem> newReport) {
        reportItemList = newReport;
        notifyDataSetChanged();
    }

}
