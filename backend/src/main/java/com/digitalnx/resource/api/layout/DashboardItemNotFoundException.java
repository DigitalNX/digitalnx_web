package com.digitalnx.resource.api.layout;

public class DashboardItemNotFoundException extends RuntimeException {
    public DashboardItemNotFoundException(String itemName) { super("Couldn't find item " + itemName + "!"); }
    public DashboardItemNotFoundException(Integer id) { super("Couldn't find the item with id " + id + "!"); }
}
