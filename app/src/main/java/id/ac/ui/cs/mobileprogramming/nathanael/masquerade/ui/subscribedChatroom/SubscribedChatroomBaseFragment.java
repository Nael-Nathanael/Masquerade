package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom.helper.SubscribedChatroomNavigationViewModel;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom.ui.SubscribedChatroomFragment;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom.ui.list.SubscribedChatRoomListFragment;

public class SubscribedChatroomBaseFragment extends Fragment {

    SubscribedChatroomNavigationViewModel subscribedChatroomNavigationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_subscribed_chatroom, container, false);

        subscribedChatroomNavigationViewModel = new ViewModelProvider(requireActivity()).get(SubscribedChatroomNavigationViewModel.class);

        final Observer<String> subscribedSelectedObserver = selectedChatroomId -> {
            Log.d("NaelsTest", "SubscribedChatroomBaseFragment triggered by selected chatroom id");
            final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (selectedChatroomId == null) {
                ft.replace(R.id.subscribed_primary_frame, new SubscribedChatRoomListFragment(), "Subscribed Chatroom Tag");
            } else {
                ft.replace(R.id.subscribed_primary_frame, new SubscribedChatroomFragment(), "Subscribed Chatroom Tag");
                ft.addToBackStack(null);
            }
            ft.commit();
        };

        subscribedChatroomNavigationViewModel.getSelectedChatroomId().observe(getViewLifecycleOwner(), subscribedSelectedObserver);

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        subscribedChatroomNavigationViewModel.getSelectedChatroomId().setValue(null);
        subscribedChatroomNavigationViewModel.getSelectedChatroomName().setValue(null);
    }
}