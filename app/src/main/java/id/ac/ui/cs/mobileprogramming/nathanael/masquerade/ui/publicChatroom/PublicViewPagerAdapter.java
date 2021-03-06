package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.chatroom.PublicChatFragment;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.list.ChatRoomListFragment;

public class PublicViewPagerAdapter extends FragmentStateAdapter {

    private final Fragment[] mFragments = new Fragment[]{
            new ChatRoomListFragment(),
            new PublicChatFragment(),
    };

    public PublicViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments[position];
    }

    @Override
    public int getItemCount() {
        return mFragments.length;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}