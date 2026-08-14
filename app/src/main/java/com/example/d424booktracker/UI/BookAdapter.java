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
import com.example.d424booktracker.entities.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> mBooks;
    private final Context context;
    private final LayoutInflater mInflater;

    public BookAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookItemView;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getBindingAdapterPosition();
                    final Book current= mBooks.get(position);
                    Intent intent=new Intent(context, BookDetails.class);
                    intent.putExtra("id", current.getBookID());
                    intent.putExtra("name", current.getBookName());
                    intent.putExtra("author", current.getAuthor());
                    intent.putExtra("startdate", current.getStartDate());
                    intent.putExtra("enddate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.book_list_item,parent,false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if(mBooks !=null){
            Book current= mBooks.get(position);
            String name=current.getBookName();
            holder.bookItemView.setText(name);
        }
        else{
            holder.bookItemView.setText("No book name");
        }

    }

    @Override
    public int getItemCount() {
        if (mBooks != null) {
            return mBooks.size();
        } else return 0;
    }

    public void setBooks(List<Book> book){
        mBooks = book;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


}
