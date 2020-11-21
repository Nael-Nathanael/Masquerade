package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.helper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublicChatroomPagerNavigationViewModel extends ViewModel {

    private MutableLiveData<Integer> currentPage;
    private MutableLiveData<Boolean> swipeActive;
    private MutableLiveData<String> selectedChatroomId;
    private MutableLiveData<String> selectedChatroomName;

    public MutableLiveData<Integer> getCurrentPage() {
        if (currentPage == null) {
            currentPage = new MutableLiveData<>(0);
        }
        return currentPage;
    }

    public MutableLiveData<Boolean> getSwipeActive() {
        if (swipeActive == null) {
            swipeActive = new MutableLiveData<>(false);
        }
        return swipeActive;
    }

    public MutableLiveData<String> getSelectedChatroomId() {
        if (selectedChatroomId == null) {
            selectedChatroomId = new MutableLiveData<>(null);
        }
        return selectedChatroomId;
    }

    public MutableLiveData<String> getSelectedChatroomName() {
        if (selectedChatroomId == null) {
            selectedChatroomId = new MutableLiveData<>(null);
        }
        return selectedChatroomId;
    }
}
