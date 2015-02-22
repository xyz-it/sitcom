package com.xyzit.sitcom.view.fragment;

import android.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import com.xyzit.sitcom.MainActivity;

import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractFragment extends Fragment {
    protected ViewBinder createViewBinder() {
        BinderFactory binderFactory = getReusableBinderFactory();
        return binderFactory.createViewBinder(getActivity());
    }

    private BinderFactory getReusableBinderFactory() {
        BinderFactory binderFactory = ((MainActivity)getActivity()).getReusableBinderFactory();
        return binderFactory;
    }


    protected MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater) {
        BinderFactory binderFactory = getReusableBinderFactory();
        return binderFactory.createMenuBinder(menu, menuInflater, getActivity());
    }
}