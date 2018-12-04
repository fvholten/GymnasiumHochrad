package de.hochrad.hochradapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.loader.NachrichtenDesTagesLadenTask;
import de.hochrad.hochradapp.loader.NachrichtenDesTagesLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

import java.util.List;
import java.util.Objects;

public class NewsOfDayFragment extends Fragment implements WochennummerLadenTaskCallBack, NachrichtenDesTagesLadenTaskCallBack {
    private NachrichtenDesTagesLadenTask nachrichtenDesTagesLadenTask;
    private View view;
    private WochennummerLadenTask wochennummerLadenTask;

    class NewsOfDayAdapter extends Adapter<NewsOfDayAdapter.ViewHolder> {
        private List<String> newsOfWeek;

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView newsOfDayTextView;

            ViewHolder(View itemView, TextView newsOfDayTextView) {
                super(itemView);
                this.newsOfDayTextView = newsOfDayTextView;
            }

            TextView getNewsOfDayTextView() {
                return this.newsOfDayTextView;
            }
        }

        NewsOfDayAdapter(List<String> newsOfWeek) {
            this.newsOfWeek = newsOfWeek;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_of_day, parent, false);
            return new ViewHolder(view, (TextView) view.findViewById(R.id.news_of_day_text_view));
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getNewsOfDayTextView().setText(this.newsOfWeek.get(position));
        }

        public int getItemCount() {
            return this.newsOfWeek.size();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.wochennummerLadenTask != null) {
            this.wochennummerLadenTask.cancel(true);
        }
        if (this.nachrichtenDesTagesLadenTask != null) {
            this.nachrichtenDesTagesLadenTask.cancel(true);
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_of_day, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(R.string.news_of_day);
        this.view = view;

        this.wochennummerLadenTask = new WochennummerLadenTask(false, 0, 0, this);
        this.wochennummerLadenTask.execute();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.wochennummerLadenTask != null) {
            this.wochennummerLadenTask.cancel(true);
        }
        if (this.nachrichtenDesTagesLadenTask != null) {
            this.nachrichtenDesTagesLadenTask.cancel(true);
        }
    }

    @Override
    public void newsOfDay(List<String> newsOfWeek) {
        if (newsOfWeek == null) {
            startActivity();
            return;
        }
        RecyclerView newsOfDayRecyclerView = this.view.findViewById(R.id.news_of_day_content);
        newsOfDayRecyclerView.setLayoutManager(new LinearLayoutManager(this.view.getContext()));
        newsOfDayRecyclerView.setAdapter(new NewsOfDayAdapter(newsOfWeek));
    }

    private void startActivity() {
    }

    @Override
    public void loadWeekNumber(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            startActivity();
            return;
        }
        this.nachrichtenDesTagesLadenTask = new NachrichtenDesTagesLadenTask(wochennummer, this);
        this.nachrichtenDesTagesLadenTask.execute();
    }
}
