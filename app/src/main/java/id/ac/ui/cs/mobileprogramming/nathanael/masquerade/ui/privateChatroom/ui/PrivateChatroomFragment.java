package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.adapter.ChatroomRecyclerViewAdapter;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Message;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom.helper.PrivateChatroomViewModel;

/**
 * A fragment representing a list of Items.
 */
public class PrivateChatroomFragment extends Fragment {

    private FirebaseDatabase database;
    private ArrayList<Message> messages;
    private String selectedChatroomId;
    private EditText newMsgField;
    private SharedPreferences sharedPreferences;
    private ChatroomRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private PrivateChatroomViewModel privateChatroomViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PrivateChatroomFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        sharedPreferences = requireActivity().getSharedPreferences("masq-auth", Context.MODE_PRIVATE);
        messages = new ArrayList<>();
        adapter = new ChatroomRecyclerViewAdapter(messages);
    }

    @Override
    public void onPause() {
        super.onPause();
        messages = new ArrayList<>();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_bubble_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        privateChatroomViewModel = new ViewModelProvider(requireActivity()).get(PrivateChatroomViewModel.class);

        recyclerView = view.findViewById(R.id.note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.subscribe_button).setVisibility(View.GONE);

        final Observer<String> chatRoomObserver = selectedChatroomId -> {
            this.selectedChatroomId = selectedChatroomId;

            if (selectedChatroomId == null) {
                view.findViewById(R.id.note_list).setVisibility(View.GONE);
                view.findViewById(R.id.chat_layout).setVisibility(View.GONE);
                view.findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                return;
            } else {
                view.findViewById(R.id.note_list).setVisibility(View.VISIBLE);
                view.findViewById(R.id.chat_layout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.no_content).setVisibility(View.GONE);
            }

            DatabaseReference reference = database
                    .getReference()
                    .child("chatrooms")
                    .child("private")
                    .child(selectedChatroomId)
                    .child("messages");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messages = new ArrayList<>();
                    for (DataSnapshot childsnap : snapshot.getChildren()) {
                        Message single_message = childsnap.getValue(Message.class);
                        messages.add(single_message);
                    }

                    recyclerView = view.findViewById(R.id.note_list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    adapter = new ChatroomRecyclerViewAdapter(messages);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("NaelsTest", String.valueOf(error));
                }
            };

            reference.addListenerForSingleValueEvent(valueEventListener);

            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Message new_message = snapshot.getValue(Message.class);
                    messages.add(new_message);
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.scrollToPosition(messages.size() - 1);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            reference.addChildEventListener(childEventListener);
        };

        privateChatroomViewModel.getSelectedChatroomId().observe(getViewLifecycleOwner(), chatRoomObserver);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton sendButton = view.findViewById(R.id.send_note_button);
        newMsgField = view.findViewById(R.id.new_note_field);

        sendButton.setOnClickListener(v -> {
            if (newMsgField.getText().toString().isEmpty()) {
                return;
            }

            Message newmsg = new Message();

            String newId = database
                    .getReference()
                    .child("chatrooms")
                    .child("private")
                    .child(selectedChatroomId)
                    .child("messages")
                    .push()
                    .getKey();

            newmsg.id = newId;
            newmsg.datetime = String.valueOf(android.text.format.DateFormat.format("MM/dd/yyyy hh:mm", new java.util.Date()));
            newmsg.content = newMsgField.getText().toString();
            newmsg.sender = sharedPreferences.getString("username", null);

            assert newId != null;
            database
                    .getReference()
                    .child("chatrooms")
                    .child("private")
                    .child(selectedChatroomId)
                    .child("messages")
                    .child(newId)
                    .setValue(
                            newmsg
                    );

            newMsgField.setText(null);
        });
    }
}