package com.unam.alex.pumaride.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.unam.alex.pumaride.fragments.listeners.OnFragmentInteractionListener;

/**
 * Created by alex on 2/11/16.
 */
abstract class ComunicationFragmentManager extends Fragment {
    public OnFragmentInteractionListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
