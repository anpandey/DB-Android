package edu.grinnell.appdev.grinnelldirectory.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindString;
import edu.grinnell.appdev.grinnelldirectory.DBScraperCaller;
import edu.grinnell.appdev.grinnelldirectory.activities.SearchResultsActivity;
import edu.grinnell.appdev.grinnelldirectory.interfaces.APICallerInterface;
import edu.grinnell.appdev.grinnelldirectory.interfaces.NetworkAPI;
import edu.grinnell.appdev.grinnelldirectory.models.Person;
import edu.grinnell.appdev.grinnelldirectory.models.SimpleResult;
import edu.grinnell.appdev.grinnelldirectory.models.User;
import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.grinnell.appdev.grinnelldirectory.R;
import java.util.ArrayList;
import java.util.List;

public class SimpleSearchFragment extends Fragment implements Serializable, APICallerInterface {

    private View view;

    @BindView(R.id.first_name_field) EditText mFirstNameEditText;
    @BindView(R.id.last_name_field) EditText mLastNameEditText;
    @BindView(R.id.search) Button mSearchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.simple_search_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Search when the search button is pressed
     *
     * @param view SimpleSearchActvity's view
     */
    @OnClick(R.id.search) void search(View view) {
        String firstName = mFirstNameEditText.getText().toString().trim();
        String lastName = mLastNameEditText.getText().toString().trim();

        NetworkAPI api = new DBScraperCaller(getContext(), this);

        List<String> query = new ArrayList();
        query.add(firstName);
        query.add(lastName);
        query.add("");
        query.add("");

        api.simpleSearch(query);
    }

    /**
     * Bundle people and move to SearchResults Activity if search successful
     *
     * @param people List of person models
     */
    @Override public void onSearchSuccess(List<Person> people) {
        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SimpleResult.SIMPLE_KEY, new SimpleResult(people));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override public void authenticateUserCallSuccess(boolean success, Person person) {
        // Intentionally left blank
        // The api should never call an authentication callback after a search is requested
    }

    /**
     * Show an error message if the server returns an error
     *
     * @param failMessage error description
     */
    @BindString(R.string.server_failure) String serverFailure;
    @Override public void onServerFailure(String failMessage) {
        showAlert(serverFailure, failMessage);
    }

    /**
     * Show an error message if the network has an error
     *
     * @param failMessage error description
     */
    @BindString(R.string.networking_error) String networkingError;
    @Override public void onNetworkingError(String failMessage) {
        showAlert(networkingError, failMessage);
    }

    private void showAlert(String label, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(label + ": " + message);
        builder.show();
    }
}