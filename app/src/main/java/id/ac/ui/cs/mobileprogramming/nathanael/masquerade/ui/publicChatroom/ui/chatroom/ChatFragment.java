package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.ui.chatroom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Message;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.helper.PublicChatroomPagerNavigationViewModel;

/**
 * A fragment representing a list of Items.
 */
public class ChatFragment extends Fragment {

    private FirebaseDatabase database;
    private ArrayList<Message> messages;
    private PublicChatroomPagerNavigationViewModel navigationModel;
    private String selectedChatroomId;
    private EditText newMsgField;
    private SharedPreferences sharedPreferences;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_bubble_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        database = FirebaseDatabase.getInstance();
        navigationModel = new ViewModelProvider(requireActivity()).get(PublicChatroomPagerNavigationViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        final Observer<String> chatRoomObserver = selectedChatroomId -> {
            this.selectedChatroomId = selectedChatroomId;
            DatabaseReference reference = database
                    .getReference()
                    .child("chatrooms")
                    .child("public")
                    .child(selectedChatroomId)
                    .child("messages");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("NaelsTest", String.valueOf(snapshot));
                    messages = new ArrayList<>();
                    for (DataSnapshot childsnap : snapshot.getChildren()) {
                        Message single_message = childsnap.getValue(Message.class);
                        messages.add(single_message);
                    }

                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new MyChatRoomRecyclerViewAdapter(messages));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("NaelsTest", String.valueOf(error));
                }
            };

            reference.addValueEventListener(valueEventListener);
        };

        navigationModel.getSelectedChatroomId().observe(getViewLifecycleOwner(), chatRoomObserver);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sendButton = view.findViewById(R.id.send_button);
        newMsgField = view.findViewById(R.id.new_text_field);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message newmsg = new Message();

                String newId = database
                        .getReference()
                        .child("chatrooms")
                        .child("public")
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
                        .child("public")
                        .child(selectedChatroomId)
                        .child("messages")
                        .child(newId)
                        .setValue(
                                newmsg
                        );
            }
        });
    }
}