package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;

public class ServerLoadBalancerTest {
    Server server;

	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancigServer_noVm_ServerStaysEmpty(){
		server = new ServerBuilder.withCapacity(1).build();
		ServerLoadBalancer balancer = new ServerLoadBalancerBuilder.withVms(VmCollectionBuilder.Empty()).withServers(new ArrayList<Server>()).build();
		balancer.balance();

		assertThat(server, hasCurrentLoadOf(0.0d));
	}

    private Matcher<? super Server> hasCurrentLoadOf(double load) {
        return new CurrentLoadPercentageMatcher(load);
    }

}
