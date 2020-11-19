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
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.list.dummy.DummyContent.DummyItem;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.viewmodel.PublicChatroomPagerNavigationViewModel;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyChatRoomListRecyclerViewAdapter extends RecyclerView.Adapter<MyChatRoomListRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    private final PublicChatroomPagerNavigationViewModel navigationModel;

    public MyChatRoomListRecyclerViewAdapter(Context context, List<DummyItem> items) {
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
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(v -> {
            Log.d("NaelTest", "One View Clicked on " + mValues.get(position).id);
            navigationModel.getCurrentPage().setValue(1);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public DummyItem mItem;

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