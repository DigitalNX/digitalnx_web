package com.digitalnx.resource.api.info;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Info {
    @Id
    @GeneratedValue
    Integer id;

    public Integer getId() { return id; }
}
