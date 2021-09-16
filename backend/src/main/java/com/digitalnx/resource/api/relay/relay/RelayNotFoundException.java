package com.digitalnx.resource.api.relay.relay;

public class RelayNotFoundException extends RuntimeException {
    public RelayNotFoundException(Integer id) {
        super("Could not find relay" + id);
    }
}
