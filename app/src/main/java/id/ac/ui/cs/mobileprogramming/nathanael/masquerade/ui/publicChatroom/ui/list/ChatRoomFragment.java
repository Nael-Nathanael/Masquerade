package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import java.util.Objects;

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatRoomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_room_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference().child("chatrooms").child("public");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatRooms = new ArrayList<>();
                for (DataSnapshot childsnap : snapshot.getChildren()) {
                    ChatRoom chatRoom = childsnap.getValue(ChatRoom.class);
                    chatRooms.add(chatRoom);
                }

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new MyChatRoomListRecyclerViewAdapter(requireActivity(), chatRooms));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("NaelsTest", String.valueOf(error));
            }
        };

        reference.addValueEventListener(valueEventListener);

        FloatingActionButton create_fab = view.findViewById(R.id.create_room_fab);
        create_fab.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            CreatePublicChatroomDialog publicChatroomDialog = new CreatePublicChatroomDialog();
            publicChatroomDialog.show(fragmentManager, "dialog");
        });

        return view;
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