package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublicChatroomPagerNavigationViewModel extends ViewModel {

    private MutableLiveData<Integer> currentPage;
    private MutableLiveData<Boolean> swipeActive;

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
}
