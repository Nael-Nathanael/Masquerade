package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.NotesViewModel;

public class NotesFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private NotesViewModel notesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        notesViewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        Context context = view.getContext();
        sharedPreferences = requireActivity().getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyNotesRecyclerViewAdapter adapter = new MyNotesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        notesViewModel.getAllNotes().observe(requireActivity(), notes_from_db -> {
            adapter.setNotes(notes_from_db);
            recyclerView.scrollToPosition(notes_from_db.size() - 1);
        });

        EditText new_note_field = view.findViewById(R.id.new_note_field);
        Button submit_button = view.findViewById(R.id.send_note_button);
        submit_button.setOnClickListener((View.OnClickListener) v -> {
            Notes new_notes = new Notes(
                    new_note_field.getText().toString(),
                    sharedPreferences.getString("username", null)
            );

            notesViewModel.insert(new_notes);

            new_note_field.setText(null);
        });

        return view;
    }
}