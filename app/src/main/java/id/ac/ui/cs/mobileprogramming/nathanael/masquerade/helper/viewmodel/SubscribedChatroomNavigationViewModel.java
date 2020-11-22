package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubscribedChatroomNavigationViewModel extends ViewModel {

    private MutableLiveData<String> selectedChatroomId;
    private MutableLiveData<String> selectedChatroomName;
    private MutableLiveData<String> markedToDeleteId;

    public MutableLiveData<String> getSelectedChatroomId() {
        if (selectedChatroomId == null) {
            selectedChatroomId = new MutableLiveData<>(null);
        }
        return selectedChatroomId;
    }

    public MutableLiveData<String> getMarkedToDelete() {
        if (markedToDeleteId == null) {
            markedToDeleteId = new MutableLiveData<>(null);
        }
        return markedToDeleteId;
    }

    public MutableLiveData<String> getSelectedChatroomName() {
        if (selectedChatroomName == null) {
            selectedChatroomName = new MutableLiveData<>(null);
        }
        return selectedChatroomName;
    }
}
