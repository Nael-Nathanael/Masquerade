package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.subscribedChatroom.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.SubscribedChatroomViewModel;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.SubscribedChatroomNavigationViewModel;

/**
 * A fragment representing a list of Items.
 */
public class SubscribedChatRoomListFragment extends Fragment {

    private List<SubscribedChatroom> chatRooms;
    private FragmentActivity targetActivity;
    private SubscribedChatroomViewModel subscribedChatroomViewModel;
    private SubscribedChatroomNavigationViewModel subscribedChatroomNavigationViewModel;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubscribedChatRoomListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.subscribed_chat_room_list, container, false);

        chatRooms = new ArrayList<>();
        targetActivity = requireActivity();
        subscribedChatroomViewModel = new ViewModelProvider(requireActivity()).get(SubscribedChatroomViewModel.class);
        subscribedChatroomNavigationViewModel = new ViewModelProvider(requireActivity()).get(SubscribedChatroomNavigationViewModel.class);

        setupChatRoomList();
        setupDeletionMethod();

        return view;
    }

    private void setupDeletionMethod() {
        subscribedChatroomNavigationViewModel
                .getMarkedToDelete()
                .observe(
                        getViewLifecycleOwner(),
                        idMarkedToDelete -> {
                            if (idMarkedToDelete != null) {
                                new MaterialAlertDialogBuilder(requireContext())
                                        .setTitle(getResources().getString(R.string.unsubscribe_dialog_title))
                                        .setMessage(getResources().getString(R.string.unsubscribe_dialog_message))
                                        .setNeutralButton(getResources().getString(R.string.unsubscribe_dialog_neutral), (dialog, which) -> {

                                        })
                                        .setNegativeButton(getResources().getString(R.string.unsubscribe_dialog_confirm), (dialog, which) -> {
                                            subscribedChatroomViewModel.delete(idMarkedToDelete);
                                            subscribedChatroomNavigationViewModel.getMarkedToDelete().setValue(null);
                                        })
                                        .show();
                            }
                        }
                );
    }

    private void setupChatRoomList() {

        RecyclerView recyclerView = view.findViewById(R.id.note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        SubscribedChatroomListRecyclerViewAdapter adapter = new SubscribedChatroomListRecyclerViewAdapter(targetActivity, chatRooms);
        recyclerView.setAdapter(adapter);

        subscribedChatroomViewModel.getAllSubscribedChatroom().observe(requireActivity(), subscribedChatroomList -> {
            adapter.setChatrooms(subscribedChatroomList);
            recyclerView.scrollToPosition(subscribedChatroomList.size() - 1);
        });
    }
}