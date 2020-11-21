package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom.helper.PrivateChatroomViewModel;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom.ui.PrivateChatroomFragment;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom.ui.PrivateChatroomLandingFragment;

public class PrivateChatroomBaseFragment extends Fragment {

    PrivateChatroomViewModel privateChatroomViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_private_chatroom, container, false);

        privateChatroomViewModel = new ViewModelProvider(requireActivity()).get(PrivateChatroomViewModel.class);


        final Observer<String> privateObserver = selectedChatroomId -> {
            final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (selectedChatroomId == null) {
                ft.replace(R.id.private_primary_frame, new PrivateChatroomLandingFragment(), "Private Chatroom Tag");
            } else {
                ft.replace(R.id.private_primary_frame, new PrivateChatroomFragment(), "Private Chatroom Tag");
            }
            ft.addToBackStack(null);
            ft.commit();
        };

        privateChatroomViewModel.getSelectedChatroomId().observe(getViewLifecycleOwner(), privateObserver);

        return root;
    }
}