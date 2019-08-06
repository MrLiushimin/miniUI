package com.liushimin.miniUI.pojo;

import java.util.Date;

public class Employee {
    private String id;
    private String loginname;
    private String name;
    private int age;
    private Date birthday;
    private String dept_id;
    private String position;
    private int gender;
    private int married;
    private String salary;
    private String educational;
    private String country;
    private String city;
    private String remarks;
    private String school;
    private Date createtime;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMarried() {
        return married;
    }

    public void setMarried(int married) {
        this.married = married;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", loginname='" + loginname + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", dept_id='" + dept_id + '\'' +
                ", position='" + position + '\'' +
                ", gender=" + gender +
                ", married=" + married +
                ", salary='" + salary + '\'' +
                ", educational='" + educational + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", remarks='" + remarks + '\'' +
                ", school='" + school + '\'' +
                ", createtime=" + createtime +
                ", email='" + email + '\'' +
                '}';
    }
}
