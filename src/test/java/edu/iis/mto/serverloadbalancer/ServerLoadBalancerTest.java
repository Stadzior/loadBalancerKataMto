package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import edu.iis.mto.serverloadbalancer.Collections.ServerCollection;
import edu.iis.mto.serverloadbalancer.Collections.ServerCollectionBuilder;
import edu.iis.mto.serverloadbalancer.Collections.VmCollectionBuilder;
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
	public void balancingServer_noVm_ServerStaysEmpty(){
		server = new ServerBuilder().withCapacity(1).build();
		ServerLoadBalancer balancer = new ServerLoadBalancerBuilder().withVms(VmCollectionBuilder.Empty()).withServers(new ServerCollection()).build();
		balancer.balance();

		assertThat(server, hasCurrentLoadOf(0.0d));
	}

    @Test
    public void balancingServer_OneVm_AllCapacityTaken(){
        server = new ServerBuilder().withCapacity(1).build();
        Vm vm = new VmBuilder().withSize(1).build();
        ServerLoadBalancer balancer = new ServerLoadBalancerBuilder()
                .withVms(new VmCollectionBuilder()
                        .withVm(vm)
                        .build())
                .withServers(new ServerCollectionBuilder()
                        .withServer(server)
                        .build())
                .build();
        balancer.balance();
        assertThat(server, hasCurrentLoadOf(100.0d));
    }

    @Test
    public void balancingServer_OneVm_SomeOfCapacityTaken(){
        server = new ServerBuilder().withCapacity(2).build();
        Vm vm = new VmBuilder().withSize(1).build();
        ServerLoadBalancer balancer = new ServerLoadBalancerBuilder()
                .withVms(new VmCollectionBuilder()
                        .withVm(vm)
                        .build())
                .withServers(new ServerCollectionBuilder()
                        .withServer(server)
                        .build())
                .build();
        balancer.balance();
        assertThat(server, hasCurrentLoadOf(50.0d));
    }

    @Test
    public void balancingServer_ThreeVms_OneServer_EnoughCapacity(){
        server = new ServerBuilder().withCapacity(5).build();
        Vm vm1 = new VmBuilder().withSize(1).build();
        Vm vm2 = new VmBuilder().withSize(2).build();
        Vm vm3 = new VmBuilder().withSize(1).build();
        ServerLoadBalancer balancer = new ServerLoadBalancerBuilder()
                .withVms(new VmCollectionBuilder()
                        .withVm(vm1)
                        .withVm(vm2)
                        .withVm(vm3)
                        .build())
                .withServers(new ServerCollectionBuilder()
                        .withServer(server)
                        .build())
                .build();
        balancer.balance();
        assertThat(server.getVms().size(),equalTo(3));
    }

    private Matcher<? super Server> hasCurrentLoadOf(double load) {
        return new CurrentLoadPercentageMatcher(load);
    }

}
