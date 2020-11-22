package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.PrivateChatRoom;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.PrivateChatroomViewModel;

public class PrivateChatroomLandingFragment extends Fragment {

    Button login_private_button;
    EditText chatroom_password_field;
    PrivateChatroomViewModel privateChatroomViewModel;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_private_chatroom_landing, container, false);

        login_private_button = root.findViewById(R.id.login_private);
        chatroom_password_field = root.findViewById(R.id.chatroom_password_field);
        privateChatroomViewModel = new ViewModelProvider(requireActivity()).get(PrivateChatroomViewModel.class);

        database = FirebaseDatabase.getInstance();

        login_private_button.setOnClickListener(v -> {
            String password = chatroom_password_field.getText().toString();
            if (password.isEmpty()) {
                return;
            }

            reference = database.getReference().child("chatrooms").child("private");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean found = false;
                    for (DataSnapshot childsnap : snapshot.getChildren()) {
                        PrivateChatRoom chatRoom = childsnap.getValue(PrivateChatRoom.class);
                        if (chatRoom.password.equals(password)) {
                            privateChatroomViewModel.getSelectedChatroomId().setValue(chatRoom.id);
                            found = true;
                        }
                    }

                    if (!found) {
                        Toast.makeText(
                                getContext(),
                                "Private chat room not found",
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    chatroom_password_field.setText(null);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };

            reference.addListenerForSingleValueEvent(valueEventListener);
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}