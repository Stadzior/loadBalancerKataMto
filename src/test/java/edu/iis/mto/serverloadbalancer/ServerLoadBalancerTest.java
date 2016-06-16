package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import edu.iis.mto.serverloadbalancer.Collections.ServerCollection;
import edu.iis.mto.serverloadbalancer.Collections.ServerCollectionBuilder;
import edu.iis.mto.serverloadbalancer.Collections.VmCollection;
import edu.iis.mto.serverloadbalancer.Collections.VmCollectionBuilder;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;

public class ServerLoadBalancerTest {

	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingServer_noVm_ServerStaysEmpty(){
		Server server = new ServerBuilder().withCapacity(1).build();
		ServerLoadBalancer balancer = new ServerLoadBalancerBuilder().withVms(VmCollectionBuilder.Empty()).withServers(new ServerCollection()).build();
		balancer.balance();

		assertThat(server, hasCurrentLoadOf(0.0d));
	}

    @Test
    public void balancingServer_OneVm_AllCapacityTaken(){
        Server server = new ServerBuilder().withCapacity(1).build();
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
        Server server = new ServerBuilder().withCapacity(2).build();
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
    public void balancingServer_ThreeVms_EnoughCapacity(){
        Server server = new ServerBuilder().withCapacity(5).build();
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

    @Test
    public void balancer_ShouldAssignVm_ToLessLoadedServer(){
        Vm vm1 = new VmBuilder().withSize(1).build();
        Vm vm2 = new VmBuilder().withSize(2).build();
        Server server1 = new ServerBuilder().withCapacity(5).build();
        Server server2 = new ServerBuilder().withCapacity(5).build();
                ServerLoadBalancer balancer = new ServerLoadBalancerBuilder()
                .withVms(new VmCollectionBuilder()
                        .withVm(vm1)
                        .withVm(vm2)
                        .build())
                .withServers(new ServerCollectionBuilder()
                        .withServer(server1)
                        .withServer(server2)
                        .build())
                .build();
        balancer.balance();
        assertThat(server2.getVms(),contains(vm2));
    }

    @Test
    public void balancer_ShouldNotAssignVm_IfThereIsNoCapableServer(){
        Vm vm1 = new VmBuilder().withSize(1).build();
        Vm vm2 = new VmBuilder().withSize(4).build();
        Vm vm3 = new VmBuilder().withSize(1).build();
        Server server = new ServerBuilder().withCapacity(5).build();
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
        assertThat(server.getVms().contains(vm3),equalTo(false));
    }

    private Matcher<? super Server> hasCurrentLoadOf(double load) {
        return new CurrentLoadPercentageMatcher(load);
    }

}
