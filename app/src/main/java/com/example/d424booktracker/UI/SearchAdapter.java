package com.example.d424booktracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424booktracker.R;
import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.CombineSearch;


import java.util.List;
import java.util.Objects;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<CombineSearch> results;
    private final LayoutInflater mInflater;
    private final Context context;
    private OnItemClickListener clickListener;

    public SearchAdapter(Context context, OnItemClickListener clickListener) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CombineSearch currentItem);
    }



    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.search_list_item, parent, false);
        return new SearchAdapter.SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        CombineSearch result = results.get(position);
        String title = result.getTitle();
        String author = result.getAuthor();
        holder.textTitle.setText(title);
        holder.textAuthor.setText(author);

    }

    @Override
    public int getItemCount() {
        if (results != null) {
            return results.size();
        } else return 0;
    }

    public void setResults(List<CombineSearch> newResult) {
        results = newResult;
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textAuthor;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textView);
            textAuthor = itemView.findViewById(R.id.textView2);

        textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null) {
                    clickListener.onItemClick(results.get(position));
                }

        };

        });
    }

}}
