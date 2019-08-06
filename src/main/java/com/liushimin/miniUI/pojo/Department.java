package com.liushimin.miniUI.pojo;

import com.liushimin.miniUI.service.BaseService;

public class Department {
    private String id;
    private String name;
    private String manager;
    private String manager_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", manager_name='" + manager_name + '\'' +
                '}';
    }
}
