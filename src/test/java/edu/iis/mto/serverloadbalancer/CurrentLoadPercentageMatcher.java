package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Kamil on 2016-06-16.
 */
public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {
    double expectedLoad;
    public CurrentLoadPercentageMatcher(double load) {
        expectedLoad = load;
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return Math.abs(expectedLoad - server.getLoad()) < 0.1d;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a server with load percentage of ").appendValue(expectedLoad);
    }
}
