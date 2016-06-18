package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Kamil on 2016-06-18.
 */
public class LoadMatcher extends TypeSafeMatcher<Server> {
    private double expectedLoad;
    public LoadMatcher(double load) {
        expectedLoad = load;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("A server with load percentage of " + expectedLoad);
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return expectedLoad == server.getLoad() || Math.abs(expectedLoad-server.getLoad())<0.01d;
    }
}
