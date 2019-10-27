package com.hackmoskow.mobile.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.domain.models.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private List<Event> eventList = new ArrayList<>();

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item_view, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, int position) {
        holder.bind(eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setItems(Collection<Event> tweets) {
        eventList.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearItems() {
        eventList.clear();
        notifyDataSetChanged();
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_info_tv_item)
        TextView categoryInfo;
        @BindView(R.id.distance_info_tv_item)
        TextView distanceInfo;
        @BindView(R.id.title_info_tv_item)
        TextView titleInfo;

        public BookmarkViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(Event event) {
            categoryInfo.setText(event.getDescription());
            distanceInfo.setText(event.getType());
            titleInfo.setText(event.getTitle());
        }
    }
}