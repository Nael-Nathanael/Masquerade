package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.helper.PublicViewPagerAdapter;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.viewmodel.PublicChatroomPagerNavigationViewModel;

public class PublicChatroomFragment extends Fragment {

    private ViewPager2 mViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_public_chatroom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = view.findViewById(R.id.public_pager);
        mViewPager.setAdapter(new PublicViewPagerAdapter(getActivity()));

        PublicChatroomPagerNavigationViewModel navigationModel = new ViewModelProvider(requireActivity()).get(PublicChatroomPagerNavigationViewModel.class);

        final Observer<Integer> navigationObserver = targetPage -> {
            mViewPager.setCurrentItem(targetPage);
            mViewPager.setUserInputEnabled(targetPage != 0);
        };

        final Observer<Boolean> swipeObserver = aBoolean -> mViewPager.setUserInputEnabled(aBoolean);

        navigationModel.getCurrentPage().observe(getViewLifecycleOwner(), navigationObserver);
        navigationModel.getSwipeActive().observe(getViewLifecycleOwner(), swipeObserver);
    }
}