package com.example.d424booktracker.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424booktracker.R;
import com.example.d424booktracker.database.Repository;
import com.example.d424booktracker.entities.ReportItem;

import java.util.List;

public class ReportPage extends AppCompatActivity {
    private Repository repository;
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generate_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.report_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SessionManager sessionManager = new SessionManager(ReportPage.this);

        RecyclerView recyclerView=findViewById(R.id.report_view);

        repository = new Repository(getApplication());

        currentUserID = sessionManager.getCurrentId();

        List<ReportItem> allBooks = repository.generateReport(currentUserID);

        final ReportAdapter reportAdapter = new ReportAdapter(this);

        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportAdapter.setReport(allBooks);

        //fix back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
}
