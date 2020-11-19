package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.helper.PublicChatroomPagerNavigationViewModel;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.ChatRoom;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ChatRoom}.
 */
public class MyChatRoomListRecyclerViewAdapter extends RecyclerView.Adapter<MyChatRoomListRecyclerViewAdapter.ViewHolder> {

    private final List<ChatRoom> mValues;

    private final PublicChatroomPagerNavigationViewModel navigationModel;

    public MyChatRoomListRecyclerViewAdapter(Context context, List<ChatRoom> items) {
        this.mValues = items;

        navigationModel = new ViewModelProvider((ViewModelStoreOwner) context).get(PublicChatroomPagerNavigationViewModel.class);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_room_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(v -> {
            navigationModel.getCurrentPage().setValue(1);
            navigationModel.getSelectedChatroomId().setValue(mValues.get(position).id);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public ChatRoom mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}