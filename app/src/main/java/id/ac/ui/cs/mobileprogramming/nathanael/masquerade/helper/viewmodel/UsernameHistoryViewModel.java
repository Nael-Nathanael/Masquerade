package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.UsernameHistory;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository.UsernameHistoryRepository;

public class UsernameHistoryViewModel extends AndroidViewModel {

    private final UsernameHistoryRepository mRepository;

    private final LiveData<List<UsernameHistory>> mAllUsername;

    public UsernameHistoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UsernameHistoryRepository(application);
        mAllUsername = mRepository.getAllusernameHistories();
    }

    public LiveData<List<UsernameHistory>> getall() {
        return mAllUsername;
    }

    public void insert(UsernameHistory usernameHistory) {
        mRepository.insert(usernameHistory);
    }
}
