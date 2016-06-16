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

    @Test
    public void balancer_ShouldBalanceEightVms_OnThreeServers_DecliningAssignmentOfOneBigVm(){
        Vm vm1 = new VmBuilder().withSize(1).build();
        Vm vm2 = new VmBuilder().withSize(3).build();
        Vm vm3 = new VmBuilder().withSize(2).build();
        Vm vm4 = new VmBuilder().withSize(4).build();
        Vm vm5 = new VmBuilder().withSize(6).build();
        Vm vm6 = new VmBuilder().withSize(5).build();
        Vm vm7 = new VmBuilder().withSize(100).build();
        Vm vm8 = new VmBuilder().withSize(7).build();
        Server server1 = new ServerBuilder().withCapacity(3).build();
        Server server2 = new ServerBuilder().withCapacity(7).build();
        Server server3 = new ServerBuilder().withCapacity(18).build();
        ServerLoadBalancer balancer = new ServerLoadBalancerBuilder()
                .withVms(new VmCollectionBuilder()
                        .withVm(vm1)
                        .withVm(vm2)
                        .withVm(vm3)
                        .withVm(vm4)
                        .withVm(vm5)
                        .withVm(vm6)
                        .withVm(vm7)
                        .withVm(vm8)
                        .build())
                .withServers(new ServerCollectionBuilder()
                        .withServer(server1)
                        .withServer(server2)
                        .withServer(server3)
                        .build())
                .build();
        balancer.balance();
        assertThat(server1.getVms().contains(vm1) && server1.getVms().contains(vm3) && server1.getVms().size() == 2,equalTo(true));
        assertThat(server2.getVms().contains(vm2) && server1.getVms().contains(vm4) && server1.getVms().size() == 2,equalTo(true));
        assertThat(server3.getVms().contains(vm5) && server1.getVms().contains(vm6)&& server1.getVms().contains(vm8) && server1.getVms().size() == 3,equalTo(true));
    }

    private Matcher<? super Server> hasCurrentLoadOf(double load) {
        return new CurrentLoadPercentageMatcher(load);
    }

}
