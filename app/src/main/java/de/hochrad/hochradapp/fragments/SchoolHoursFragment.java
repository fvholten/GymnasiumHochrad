package de.hochrad.hochradapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.hochrad.hochradapp.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SchoolHoursFragment extends Fragment {

    class SchoolHoursAdapter extends Adapter<SchoolHoursAdapter.ViewHolder> {
        private List<String> infoTextList;
        private List<String> timeTextList;

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView schoolHoursInfoTextView;
            private TextView schoolHoursTimeTextView;

            ViewHolder(View itemView, TextView schoolHoursInfoTextView, TextView schoolHoursTimeTextView) {
                super(itemView);
                this.schoolHoursInfoTextView = schoolHoursInfoTextView;
                this.schoolHoursTimeTextView = schoolHoursTimeTextView;
            }

            TextView getSchoolHoursInfoTextView() {
                return this.schoolHoursInfoTextView;
            }

            TextView getSchoolHoursTimeTextView() {
                return this.schoolHoursTimeTextView;
            }
        }

        SchoolHoursAdapter(List<String> infoTextList, List<String> timeTextList) {
            this.infoTextList = infoTextList;
            this.timeTextList = timeTextList;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_school_hours, parent, false);
            return new ViewHolder(view,
                    (TextView) view.findViewById(R.id.school_hours_info_text_view),
                    (TextView) view.findViewById(R.id.school_hours_time_text_view));
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getSchoolHoursInfoTextView().setText(this.infoTextList.get(position));
            holder.getSchoolHoursTimeTextView().setText(this.timeTextList.get(position));
        }

        public int getItemCount() {
            return this.infoTextList.size();
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stundenzeiten, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(R.string.school_hours);
        RecyclerView schoolHoursRecyclerView = view.findViewById(R.id.school_hours_recycler_view);
        schoolHoursRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        schoolHoursRecyclerView.setAdapter(new SchoolHoursAdapter(Arrays.asList(getResources().getStringArray(R.array.school_hours_info)), Arrays.asList(getResources().getStringArray(R.array.school_hours_times))));
    }
}
