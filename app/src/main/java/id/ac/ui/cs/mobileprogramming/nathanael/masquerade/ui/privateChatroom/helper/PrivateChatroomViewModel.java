package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.privateChatroom.helper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrivateChatroomViewModel extends ViewModel {

    private MutableLiveData<String> selectedChatroomId;

    public MutableLiveData<String> getSelectedChatroomId() {
        if (selectedChatroomId == null) {
            selectedChatroomId = new MutableLiveData<>(null);
        }
        return selectedChatroomId;
    }
}
