package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Message;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;

/**
 * {@link RecyclerView.Adapter} that can display {@link Message}.
 */
public class MyNotesRecyclerViewAdapter extends RecyclerView.Adapter<MyNotesRecyclerViewAdapter.ViewHolder> {

    private List<Notes> mValues;

    public MyNotesRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_bubble, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.sender.setText(mValues.get(position).sender);
        holder.content.setText(mValues.get(position).content);
        holder.datetime.setText(mValues.get(position).datetime);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNotes(List<Notes> dbnotes) {
        mValues = dbnotes;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView sender;
        public final TextView content;
        public final TextView datetime;
        public Notes mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sender = (TextView) view.findViewById(R.id.sender);
            content = (TextView) view.findViewById(R.id.content);
            datetime = (TextView) view.findViewById(R.id.datetime);
        }
    }
}