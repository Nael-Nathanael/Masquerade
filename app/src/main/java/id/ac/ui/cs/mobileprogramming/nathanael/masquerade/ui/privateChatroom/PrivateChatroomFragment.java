package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;

public class PrivateChatroomFragment extends Fragment {

    private PrivateChatroomViewModel privateChatroomViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("NaelsTest", "on private room");
        privateChatroomViewModel =
                new ViewModelProvider(this).get(PrivateChatroomViewModel.class);
        View root = inflater.inflate(R.layout.fragment_private_chatroom, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        privateChatroomViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}