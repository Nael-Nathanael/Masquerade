package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom;

import android.os.Bundle;
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

public class PublicChatroomFragment extends Fragment {

    private PublicChatroomViewModel publicChatroomViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicChatroomViewModel =
                new ViewModelProvider(this).get(PublicChatroomViewModel.class);
        View root = inflater.inflate(R.layout.fragment_public_chatroom, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        publicChatroomViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}