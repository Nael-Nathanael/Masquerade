package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublicChatroomViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PublicChatroomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}