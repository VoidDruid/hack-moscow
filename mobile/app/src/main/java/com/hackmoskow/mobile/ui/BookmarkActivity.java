    package com.hackmoskow.mobile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.domain.models.Event;
import com.hackmoskow.mobile.presenters.BookmarkController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkActivity extends Activity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private BookmarkController controller;
    private List<Event> events;
    private BookmarkAdapter bookmarkAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);

        controller = new BookmarkController(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookmarkAdapter = new BookmarkAdapter();
        recyclerView.setAdapter(bookmarkAdapter);

        controller.viewIsReady();
    }

    public void showEvents(List<Event> events) {
        bookmarkAdapter.setItems(events);
    }
}
