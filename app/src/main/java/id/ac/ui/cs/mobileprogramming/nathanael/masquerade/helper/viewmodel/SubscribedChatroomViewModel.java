package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository.SubscribedChatroomRepository;

public class SubscribedChatroomViewModel extends AndroidViewModel {

    private final SubscribedChatroomRepository mRepository;

    private final LiveData<List<SubscribedChatroom>> mAllSubscribedChatroom;

    public SubscribedChatroomViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SubscribedChatroomRepository(application);
        mAllSubscribedChatroom = mRepository.getmAllChatrooom();
    }

    public LiveData<List<SubscribedChatroom>> getAllSubscribedChatroom() {
        return mAllSubscribedChatroom;
    }

    public void insert(SubscribedChatroom subscribedChatroom) {
        mRepository.insert(subscribedChatroom);
    }

    public void delete(String selectedChatroomId) {
        mRepository.delete(selectedChatroomId);
    }
}
