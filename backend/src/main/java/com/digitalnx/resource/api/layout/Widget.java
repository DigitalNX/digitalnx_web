package com.digitalnx.resource.api.layout;

import java.util.Arrays;

public class Widget {
    private String itemName;
    private Integer id;

    Widget(){}
    public Widget(String itemName, Integer id) {
        if(Arrays.stream(WidgetType.values()).anyMatch((w) -> w.name().equals(itemName))) {
            this.itemName = itemName;
            this.id = id;
        }
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
