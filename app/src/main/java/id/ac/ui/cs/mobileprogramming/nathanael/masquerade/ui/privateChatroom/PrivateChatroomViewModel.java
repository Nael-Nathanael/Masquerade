package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrivateChatroomViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrivateChatroomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}