package com.example.d424booktracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookList extends AppCompatActivity {
    private Repository repository;

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
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookList.this, BookDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        repository = new Repository(getApplication());
        List<Book> allBooks = repository.getmAllBooks();
        final BookAdapter bookAdapter =new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter.setBooks(allBooks);

        //fix back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Book> allBooks = repository.getmAllBooks();
        RecyclerView recyclerview=findViewById(R.id.recyclerView);
        final BookAdapter bookAdapter =new BookAdapter(this);
        recyclerview.setAdapter(bookAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter.setBooks(allBooks);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //adds sample data for testing
        /*if(item.getItemId()==R.id.sample){
            repository=new Repository(getApplication());
            //Toast.makeText(VacationList.this, "put in sample data", Toast.LENGTH_LONG).show();
            Vacations vacations=new Vacations(0, "Vacation", "07/01/2027","07/12/2027", "Hotel");
            repository.insert(vacations);
            Excursion excursion=new Excursion(0, "Excursioning", "07/02/2027", 1);
            repository.insert(excursion);
            return true;
        }*/
        if(item.getItemId()==android.R.id.home){
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        return true;
    }
}