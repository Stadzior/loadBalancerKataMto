package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentVmCountMatcher extends TypeSafeMatcher<Server> {

    private int expectedCount;
    public CurrentVmCountMatcher(int count) {
        expectedCount = count;
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return expectedCount == server.getVmCount();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("server that contains " + expectedCount + " vms.");
    }

    @Override
    protected void describeMismatchSafely(Server server, Description description) {
        description.appendText("was server that contains ").appendText(String.valueOf(server.getVmCount())).appendText(" vms.");
    }

}
