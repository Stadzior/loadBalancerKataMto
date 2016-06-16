package edu.iis.mto.serverloadbalancer;

public class ThereIsNoCapableServerForThisVmException extends Exception {

    private String message;
    ThereIsNoCapableServerForThisVmException(Vm vm){
        message = "There is no capable server for this virtual machine \n" +
                "virtual machine size: " + vm.getSize();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
