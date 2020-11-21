package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom.ui.list;

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
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.helper.PublicChatroomPagerNavigationViewModel;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom.helper.SubscribedChatroomNavigationViewModel;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SubscribedChatroom}.
 */
public class SubscribedChatroomListRecyclerViewAdapter extends RecyclerView.Adapter<SubscribedChatroomListRecyclerViewAdapter.ViewHolder> {

    private final SubscribedChatroomNavigationViewModel navigationModel;
    private List<SubscribedChatroom> mValues;

    public SubscribedChatroomListRecyclerViewAdapter(Context context, List<SubscribedChatroom> items) {
        this.mValues = items;

        navigationModel = new ViewModelProvider((ViewModelStoreOwner) context).get(SubscribedChatroomNavigationViewModel.class);
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
            Log.d("NaelTest", "Item clicked");
            navigationModel.getSelectedChatroomId().setValue(mValues.get(position).id);
            navigationModel.getSelectedChatroomName().setValue(mValues.get(position).name);
        });

        holder.mView.setOnLongClickListener(v -> {
            Log.d("NaelTest", "Item long clicked");
            navigationModel.getMarkedToDelete().setValue(mValues.get(position).id);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setChatrooms(List<SubscribedChatroom> chatrooms) {
        mValues = chatrooms;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public SubscribedChatroom mItem;

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