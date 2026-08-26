package com.example.d424booktracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookList extends AppCompatActivity {
    private Repository repository;
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SessionManager sessionManager = new SessionManager(BookList.this);

        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookList.this, BookDetails.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.toSearchPage);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookList.this, SearchPage.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        repository = new Repository(getApplication());

        currentUserID = sessionManager.getCurrentId();
//        displays user ID retrieved from session manager (for debug only)
//        System.out.println("Book List ID: " + currentUserID);

        List<Book> allBooks = repository.getmBooksForUser(currentUserID);
        List<Audiobook> allAudiobooks = repository.getmAudiobooksForUser(currentUserID);

        final BookAdapter bookAdapter = new BookAdapter(this);
        final AudiobookAdapter audiobookAdapter = new AudiobookAdapter(this);
        final ConcatAdapter concatAdapter = new ConcatAdapter(bookAdapter, audiobookAdapter);

        recyclerView.setAdapter(concatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter.setBooks(allBooks);
        audiobookAdapter.setAudiobooks(allAudiobooks);

//        //back button
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_book_list, menu);

        //hides back button (force use of logout button to return to login screen)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Book> allBooks = repository.getmBooksForUser(currentUserID);
        List<Audiobook> allAudiobooks = repository.getmAudiobooksForUser(currentUserID);

        RecyclerView recyclerview=findViewById(R.id.recyclerView);


        final BookAdapter bookAdapter = new BookAdapter(this);
        final AudiobookAdapter audiobookAdapter = new AudiobookAdapter(this);
        final ConcatAdapter concatAdapter = new ConcatAdapter(bookAdapter, audiobookAdapter);

        recyclerview.setAdapter(concatAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter.setBooks(allBooks);
        audiobookAdapter.setAudiobooks(allAudiobooks);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //fixes back button functionality
//        if(item.getItemId()==android.R.id.home){
//            getOnBackPressedDispatcher().onBackPressed();
//            return true;
//        }

        if(item.getItemId()==R.id.report){
            Intent intent = new Intent(BookList.this, ReportPage.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.logout){
            SessionManager sessionManager = new SessionManager(BookList.this);
            sessionManager.logout();
            Intent intent = new Intent(BookList.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            return true;
        }

        return true;
    }
}