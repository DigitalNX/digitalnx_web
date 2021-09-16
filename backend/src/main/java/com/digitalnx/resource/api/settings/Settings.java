package com.digitalnx.resource.api.settings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Settings {
    @Id
    @GeneratedValue
    private Integer id;
    private String version;
    private SecurityMode securityMode;
    private UILanguage uiLanguage;
    Settings() {}

    public Settings(String version, SecurityMode securityMode, UILanguage uiLanguage) {
        this.version = version;
        this.securityMode = securityMode;
        this.uiLanguage = uiLanguage;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public SecurityMode getSecurityMode() {
        return securityMode;
    }
    public void setSecurityMode(SecurityMode securityMode) {
        this.securityMode = securityMode;
    }
    public UILanguage getUiLanguage() {
        return uiLanguage;
    }
    public void setUiLanguage(UILanguage uiLanguage) {
        this.uiLanguage = uiLanguage;
    }
}
