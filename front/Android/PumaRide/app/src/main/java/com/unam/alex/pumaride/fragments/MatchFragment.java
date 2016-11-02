package com.unam.alex.pumaride.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unam.alex.pumaride.MessageActivity;
import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.adapters.MatchListViewAdapter;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.services.MessageService;
import com.unam.alex.pumaride.utils.NetworkUtils;
import com.unam.alex.pumaride.utils.Statics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MatchFragment extends Fragment {
    @BindView(R.id.fragment_match_list_rv)
    RecyclerView rvMatch;
    MatchListViewAdapter mAdapter;
    Realm realm = null;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MatchFragment newInstance(int columnCount) {
        MatchFragment fragment = new MatchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_list, container, false);
        ButterKnife.bind(this, view);
        // Initialize Realm
        Realm.init(getContext());
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        // Set the adapter
        //check internet conection
        if (view instanceof RecyclerView) {
            if(NetworkUtils.isNetworkAvailable(getContext())){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Statics.CHAT_SERVER_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                WebServices webServices = retrofit.create(WebServices.class);
                Call<List<Match>> call = webServices.listMatches("12");
                call.enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                        realm.beginTransaction();
                        realm.delete(Match.class);
                        for(Match match:response.body()) {
                            RealmObject realmMatch= realm.copyToRealmOrUpdate(match);
                        }
                        realm.commitTransaction();

                        loadRealmMatches();
                    }

                    @Override
                    public void onFailure(Call<List<Match>> call, Throwable t) {

                    }
                });
            }else{
                loadRealmMatches();
            }
        }
        return view;
    }
    public void loadRealmMatches(){
        RealmResults<Match> matches = realm.where(Match.class).findAll();
        mAdapter = new MatchListViewAdapter(matches,getContext());
        rvMatch.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMatch.setLayoutManager(layoutManager);
        rvMatch.setHasFixedSize(true);

        mAdapter.SetRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick, int id) {
                getActivity().startService(new Intent(getContext(), MessageService.class));
                Intent i  = new Intent(getContext(),MessageActivity.class);
                MessageActivity.id2 = id;
                startActivity(i);
            }
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
