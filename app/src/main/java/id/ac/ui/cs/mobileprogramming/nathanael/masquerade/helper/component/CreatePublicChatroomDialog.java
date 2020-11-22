package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.component;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.ChatRoom;

public class CreatePublicChatroomDialog extends DialogFragment {

    private FirebaseDatabase database;
    private EditText new_room_name;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_public_chatroom_layout, container, false);

        database = FirebaseDatabase.getInstance();
        new_room_name = view.findViewById(R.id.new_public_chatroom_field);
        submitButton = view.findViewById(R.id.submit_new_public_room_button);

        setupSubmitButton();
        return view;
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> {
            String newId = database
                    .getReference()
                    .child("chatrooms")
                    .child("public")
                    .push()
                    .getKey();

            ChatRoom chatRoom = new ChatRoom();

            chatRoom.id = newId;
            chatRoom.name = new_room_name.getText().toString();

            assert newId != null;
            database
                    .getReference()
                    .child("chatrooms")
                    .child("public")
                    .child(newId)
                    .setValue(
                            chatRoom
                    );

            CreatePublicChatroomDialog.this.dismiss();
        });
    }


    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
