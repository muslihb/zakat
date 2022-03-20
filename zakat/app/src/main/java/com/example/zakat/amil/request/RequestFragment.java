package com.example.zakat.amil.request;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RequestFragment extends FragmentStateAdapter {
        public RequestFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position)
            {
                case 1 :
                    return new BerlangsungamilFragment();
                case 2:
                    return  new SelesaiamilFragment();
            }

            return new PendingamilFragment();
        }

        @Override
        public int getItemCount() {
            return 3;
        }
}
