package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.chatroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Message;

/**
 * {@link RecyclerView.Adapter} that can display {@link Message}.
 */
public class MyChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<MyChatRoomRecyclerViewAdapter.ViewHolder> {

    private final List<Message> mValues;

    public MyChatRoomRecyclerViewAdapter(List<Message> items) {
        mValues = items;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView sender;
        public final TextView content;
        public final TextView datetime;
        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sender = (TextView) view.findViewById(R.id.sender);
            content = (TextView) view.findViewById(R.id.content);
            datetime = (TextView) view.findViewById(R.id.datetime);
        }
    }
}