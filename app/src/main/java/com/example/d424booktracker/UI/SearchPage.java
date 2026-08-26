package com.example.d424booktracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.CombineSearch;

import java.util.List;
import java.util.Objects;

public class SearchPage extends AppCompatActivity {

    private Repository repository;
    int currentUserID;
    String query;
    private ImageButton searchBtn;
    private EditText searchBar;
    private Book current;
    private Audiobook currentAudiobook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        SessionManager sessionManager = new SessionManager(SearchPage.this);

        RecyclerView recyclerView = findViewById(R.id.searchResults);
        repository = new Repository(getApplication());

        currentUserID = sessionManager.getCurrentId();


        final SearchAdapter searchAdapter = new SearchAdapter(this, (CombineSearch currentItem) -> {
            Intent intent = new Intent(SearchPage.this, BookDetails.class);
            intent.putExtra("id", currentItem.getBookID());
            intent.putExtra("name", currentItem.getTitle());
            intent.putExtra("author", currentItem.getAuthor());

            if (Objects.equals(currentItem.getBookType(), "book")) {
                java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                    current = repository.findBook(currentItem.getTitle(), currentItem.getBookID(), currentUserID);

                    runOnUiThread(() -> {
                        intent.putExtra("startdate", current.getStartDate());
                        intent.putExtra("enddate", current.getEndDate());

                        startActivity(intent);
                    });
                });
            } else {
                java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                    currentAudiobook = repository.findAudiobook(currentItem.getTitle(), currentItem.getBookID(), currentUserID);

                    runOnUiThread(() -> {
                        intent.putExtra("startdate", currentAudiobook.getStartDate());
                        intent.putExtra("enddate", currentAudiobook.getEndDate());
                        intent.putExtra("listeningspeed", currentAudiobook.getListeningSpeed());

                        startActivity(intent);
                    });
                });
            }
        });


        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        searchBtn = findViewById(R.id.searchButton);
        searchBar = findViewById(R.id.searchBar);

        searchBtn.setOnClickListener(v -> {
            String query = searchBar.getText().toString();

            List<CombineSearch> allBooks = repository.searchDatabase(query, currentUserID);

            runOnUiThread(() -> {
                searchAdapter.setResults(allBooks);
            });
        });

    }
}
