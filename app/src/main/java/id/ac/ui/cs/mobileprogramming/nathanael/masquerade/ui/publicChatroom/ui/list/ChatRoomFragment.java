package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.component.CreatePublicChatroomDialog;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.ChatRoom;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.helper.PublicChatroomPagerNavigationViewModel;

/**
 * A fragment representing a list of Items.
 */
public class ChatRoomFragment extends Fragment {

    private FirebaseDatabase database;
    private ArrayList<ChatRoom> chatRooms;
    private FragmentActivity targetActivity;
    private PublicChatroomPagerNavigationViewModel navigationModel;
    private DatabaseReference reference;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatRoomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_room_list, container, false);

        database = FirebaseDatabase.getInstance();
        targetActivity = requireActivity();
        navigationModel = new ViewModelProvider(requireActivity()).get(PublicChatroomPagerNavigationViewModel.class);
        reference = database.getReference().child("chatrooms").child("public");

        navigationModel.getCurrentPage().setValue(0);
        navigationModel.getSwipeActive().setValue(false);

        setupChatRoomList();
        setupCreateButton();

        return view;
    }

    private void setupChatRoomList() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatRooms = new ArrayList<>();
                for (DataSnapshot childsnap : snapshot.getChildren()) {
                    ChatRoom chatRoom = childsnap.getValue(ChatRoom.class);
                    chatRooms.add(chatRoom);
                }

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.note_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new MyChatRoomListRecyclerViewAdapter(targetActivity, chatRooms));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("NaelsTest", String.valueOf(error));
            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    private void setupCreateButton() {
        FloatingActionButton create_fab = view.findViewById(R.id.create_room_fab);
        create_fab.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            CreatePublicChatroomDialog publicChatroomDialog = new CreatePublicChatroomDialog();
            publicChatroomDialog.show(fragmentManager, "dialog");
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        new ViewModelProvider(requireActivity())
                .get(PublicChatroomPagerNavigationViewModel.class)
                .getSwipeActive()
                .setValue(false);
    }
}