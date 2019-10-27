package com.hackmoskow.mobile.presenters;

import com.hackmoskow.mobile.domain.executor.Executor;
import com.hackmoskow.mobile.domain.executor.ThreadExecutor;
import com.hackmoskow.mobile.domain.models.Event;
import com.hackmoskow.mobile.domain.repository.BookmarkRepository;
import com.hackmoskow.mobile.domain.threading.MainThread;
import com.hackmoskow.mobile.domain.threading.MainThreadImpl;
import com.hackmoskow.mobile.ui.BookmarkActivity;

import java.util.List;

public class BookmarkController {
    private MainThread mainThread;
    private Executor executor;
    private BookmarkRepository bookmarkRepository;
    private BookmarkActivity view;

    public BookmarkController(BookmarkActivity view) {
        this.view = view;
        this.mainThread = MainThreadImpl.getInstance();
        this.executor = ThreadExecutor.getInstance();
        this.bookmarkRepository = new BookmarkRepository("bookmark", view);
    }

    public void viewIsReady() {
        executor.execute(() -> {
            List<Event> events = bookmarkRepository.getEvents();
            mainThread.post(() -> view.showEvents(events));
        });
    }

    public void removeEvent(Event event) {
        executor.execute(() -> bookmarkRepository.remove(event));
    }
}
