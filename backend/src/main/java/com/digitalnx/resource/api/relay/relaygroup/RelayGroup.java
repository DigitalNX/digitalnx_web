package com.digitalnx.resource.api.relay.relaygroup;

import com.digitalnx.resource.api.layout.DashboardItem;
import com.digitalnx.resource.api.relay.relay.Relay;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RelayGroup {
    @Id @GeneratedValue Integer id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="relayGroup", cascade = CascadeType.REMOVE)
    private Set<Relay> relays;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<DashboardItem> dashboardItems;

    public Set<Relay> getRelays() {
        return relays;
    }

    public void setRelays(Set<Relay> relays) {
        this.relays = relays;
    }

    RelayGroup() {};
    public Integer getId() { return id; }
    public RelayGroup(String name) {
        setName(name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
}

