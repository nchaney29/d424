package com.example.d424booktracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookDetails extends AppCompatActivity {
    String name;
    String author;
    String startDate;
    String endDate;
    String myFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    int bookID;
    Book currentBook;
    EditText editName;
    EditText editAuthor;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editName = findViewById(R.id.titletext);
        editAuthor = findViewById(R.id.authortext);
        editStartDate = findViewById(R.id.startdatetext);
        editEndDate = findViewById(R.id.enddatetext);

        name = getIntent().getStringExtra("name");
        author = getIntent().getStringExtra("author");
        startDate = getIntent().getStringExtra("startdate");
        endDate = getIntent().getStringExtra("enddate");
        bookID = getIntent().getIntExtra("id", -1);


        editName.setText(name);
        editAuthor.setText(author);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        repository = new Repository(getApplication());

        // excursion action button
//        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BookDetails.this, ExcursionDetails.class);
//                Intent intent = new Intent(BookDetails.this);
//                intent.putExtra("id", bookID);
//                intent.putExtra("startdate", startDate);
//                intent.putExtra("enddate", endDate);
//                startActivity(intent);
//            }
//        });


        //for excursion list
//        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
//
//        repository = new Repository(getApplication());
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Excursion> filteredExcursions = new ArrayList<>();
//        for (Excursion e : repository.getmAllExcursions()) {
//            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
//        }
////        excursionAdapter.setExcursions(repository.getmAllExcursions());
//        excursionAdapter.setExcursions(filteredExcursions);

    }

    @Override
    protected void onResume() {
        super.onResume();


//for excursion list
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Excursion> filteredExcursions = new ArrayList<>();
//        for (Excursion e : repository.getmAllExcursions()) {
//            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
//        }
////        excursionAdapter.setExcursions(repository.getmAllExcursions());
//        excursionAdapter.setExcursions(filteredExcursions);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        //fixes back button
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        //shares book details
        if (item.getItemId() == R.id.bookshare) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Book: " + editName.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Author: " + editAuthor.getText().toString() + "\nDate Started: " + editStartDate.getText().toString() + "\nDate Finished: " + editEndDate.getText().toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        //saves and updates books
        if (item.getItemId() == R.id.booksave) {
            Book book;

//checks dates are valid AND start date is before end date
            try {
                Date bookStart = sdf.parse(editStartDate.getText().toString());
                Date bookEnd = sdf.parse(editEndDate.getText().toString());
                if (bookEnd.before(bookStart)) {
                    Toast.makeText(BookDetails.this, "ERROR: start date must be before end date!", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (ParseException e) {
                Toast.makeText(BookDetails.this, "ERROR: date is invalid, please check format (mm/dd/yyyy)", Toast.LENGTH_LONG).show();
                return false;
            }


            if (bookID == -1) {
                if (repository.getmAllBooks().isEmpty()) bookID = 1;
                else
                    bookID = repository.getmAllBooks().get(repository.getmAllBooks().size() - 1).getBookID() + 1;
                book = new Book(bookID, editName.getText().toString(), editEndDate.getText().toString(), editStartDate.getText().toString(), editAuthor.getText().toString());
                repository.insert(book);
                this.finish();
            } else {
                book = new Book(bookID, editName.getText().toString(), editEndDate.getText().toString(), editStartDate.getText().toString(), editAuthor.getText().toString());
                repository.update(book);
                this.finish();
            }
        }
        //deletes vacations
        if (item.getItemId() == R.id.bookdelete) {
            for (Book book : repository.getmAllBooks()) {
                if (book.getBookID() == bookID)
                    currentBook = book;
            }

//            numExcursion = 0;
//            for (Excursion excursion : repository.getmAllExcursions()) {
//                if (excursion.getVacationID() == vacationID)
//                    ++numExcursion;
//            }

//            if (currentBook != null) {
//                if (numExcursion == 0) {
//                    repository.delete(currentBook);
//                    Toast.makeText(BookDetails.this, currentBook.getBookName() + " was deleted", Toast.LENGTH_LONG).show();
//                    BookDetails.this.finish();
//                } else {
//                    Toast.makeText(BookDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
//
//                }
//            }

            repository.delete(currentBook);
            Toast.makeText(BookDetails.this, currentBook.getBookName() + " was deleted", Toast.LENGTH_LONG).show();
            BookDetails.this.finish();
        }

//sets start date notification
//        if (item.getItemId() == R.id.vacationnotify) {
//            String dateFromScreen = editStartDate.getText().toString();
//
//            Date myDate = null;
//            try {
//                myDate = sdf.parse(dateFromScreen);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try {
//                Long trigger = myDate.getTime();
//                Intent intent = new Intent(BookDetails.this, MyReceiver.class);
//                intent.putExtra("key", editName.getText().toString() + " is starting");
//                PendingIntent sender = PendingIntent.getBroadcast(BookDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
//            } catch (Exception e) {
//
//            }
//        }
//
//        //sets end date notification
//        if (item.getItemId() == R.id.vacationendnotify) {
//            String dateFromScreen = editEndDate.getText().toString();
//
//            Date myDate = null;
//            try {
//                myDate = sdf.parse(dateFromScreen);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try {
//                Long trigger = myDate.getTime();
//                Intent intent = new Intent(BookDetails.this, MyReceiver.class);
//                intent.putExtra("key", editName.getText().toString() + " is ending");
//                PendingIntent sender = PendingIntent.getBroadcast(BookDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
//            } catch (Exception e) {
//
//            }
//        }

        return true;
    }
}