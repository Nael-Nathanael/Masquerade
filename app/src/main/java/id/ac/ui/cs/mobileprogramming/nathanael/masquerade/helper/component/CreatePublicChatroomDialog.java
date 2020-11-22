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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();

        View view = inflater.inflate(R.layout.create_public_chatroom_layout, container, false);
        new_room_name = view.findViewById(R.id.new_public_chatroom_field);

        Button submitButton = view.findViewById(R.id.submit_new_public_room_button);
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

        return view;
    }


    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
