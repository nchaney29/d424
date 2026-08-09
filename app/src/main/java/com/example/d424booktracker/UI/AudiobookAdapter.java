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

import java.util.List;

public class AudiobookAdapter extends RecyclerView.Adapter<AudiobookAdapter.AudiobookViewHolder> {

    private List<Audiobook> mAudiobooks;
    private final Context context;
    private final LayoutInflater mInflater;

    public AudiobookAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    public class AudiobookViewHolder extends RecyclerView.ViewHolder {
        private final TextView audiobookItemView;
        public AudiobookViewHolder(@NonNull View itemView) {
            super(itemView);
            audiobookItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getBindingAdapterPosition();
                    final Audiobook current= mAudiobooks.get(position);
                    Intent intent=new Intent(context, BookDetails.class);
                    intent.putExtra("id", current.getBookID());
                    intent.putExtra("name", current.getBookName());
                    intent.putExtra("author", current.getAuthor());
                    intent.putExtra("startdate", current.getStartDate());
                    intent.putExtra("enddate", current.getEndDate());
                    intent.putExtra("listeningspeed", current.getListeningSpeed());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AudiobookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.audiobook_list_item,parent,false);
        return new AudiobookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AudiobookViewHolder holder, int position) {
        if(mAudiobooks !=null){
            Audiobook current= mAudiobooks.get(position);
            String name=current.getBookName();
            holder.audiobookItemView.setText(name);
        }
        else{
            holder.audiobookItemView.setText("No book name");
        }

    }

    @Override
    public int getItemCount() {
        if (mAudiobooks != null) {
            return mAudiobooks.size();
        } else return 0;
    }

    public void setAudiobooks(List<Audiobook> audiobook){
        mAudiobooks = audiobook;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 2;
    }


}
