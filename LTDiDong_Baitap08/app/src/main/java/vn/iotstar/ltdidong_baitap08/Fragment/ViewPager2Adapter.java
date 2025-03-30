package vn.iotstar.ltdidong_baitap08.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.iotstar.ltdidong_baitap08.SearchRecyclerView.NewOrderFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NewOrderFragment();
//            case 1:
//                return new CancelFragment();
            case 2:
                return new NewOrderFragment();
//            case 3:
//                return new PickupFragment();
//            case 4:
//                return new DeliveryFragment();
//            case 5:
//                return new DanhGiaFragment();
            default:
                return new NewOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
