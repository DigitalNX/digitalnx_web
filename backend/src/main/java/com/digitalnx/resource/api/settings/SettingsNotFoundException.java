package com.digitalnx.resource.api.settings;

public class SettingsNotFoundException extends RuntimeException{
    public SettingsNotFoundException() { super("Settings not found."); }
}
