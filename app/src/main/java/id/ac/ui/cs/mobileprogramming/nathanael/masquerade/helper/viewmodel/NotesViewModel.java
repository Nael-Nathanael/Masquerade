package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository.NotesRepository;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository mRepository;

    private LiveData<List<Notes>> mAllNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NotesRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(Notes notes) {
        mRepository.insert(notes);
    }
}
