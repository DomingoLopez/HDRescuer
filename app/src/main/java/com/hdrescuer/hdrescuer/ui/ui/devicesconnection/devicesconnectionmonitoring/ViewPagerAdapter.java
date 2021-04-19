package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase ViewPagerAdapter para los fragments de monitorización
 * @author Domingo Lopez
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    /**
     * Método que "crea" un fragmento en función de la posición del ViewPager que se haya seleccionado
     * @param position
     * @return Fragment
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TabE4BandMonitoring();
            case 1:
                return new TabWatchMonitoring();
            case 2:
                return new TabOximeterMonitoring();
        }
        return new TabE4BandMonitoring();
    }

    /**
     * Método que devuelve el total de fragmentos del ViewPager
     * @return int
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}
