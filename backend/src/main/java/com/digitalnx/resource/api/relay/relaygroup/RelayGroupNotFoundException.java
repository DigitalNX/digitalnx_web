package com.digitalnx.resource.api.relay.relaygroup;

public class RelayGroupNotFoundException extends RuntimeException {
    public RelayGroupNotFoundException(Integer id) {
        super("Could not find relay " + id);
    }
}
