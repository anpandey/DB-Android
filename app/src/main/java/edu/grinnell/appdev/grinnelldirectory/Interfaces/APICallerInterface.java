package edu.grinnell.appdev.grinnelldirectory.Interfaces;

import java.util.List;

import edu.grinnell.appdev.grinnelldirectory.Model.Person;
import edu.grinnell.appdev.grinnelldirectory.User;

/**
 * Created by nicholasroberson on 2/15/17.
 */

public interface APICallerInterface {

    List<Person> onSearchSuccess(List<Person> people);

    boolean authenticateUserCallSuccess(List<Person> people);

    String onServerFailure(String fail_message);

    boolean onNetworkingError(String fail_message);
}
