/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model;

import java.time.Period;
import java.util.Date;

/**
 *
 * @author Robson
 */
public class Dependent {
    
    private int id;
    private String name;
    private String cpf;
    private Date birthDate;
    private int idKinship;
    private int idDeficiency;
    private Beneficiary.Sex sex;
    private String phone;    
    private Period age;
    private int idUserCreate;
    private int idUserUpdate;
    private Date updateDate;
    private Date createDate;    
    
    public Dependent() {
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getIdKinship() {
        return idKinship;
    }

    public void setIdKinship(int idKinship) {
        this.idKinship = idKinship;
    }

    public int getIdDeficiency() {
        return idDeficiency;
    }

    public void setIdDeficiency(int idDeficiency) {
        this.idDeficiency = idDeficiency;
    }

    public Beneficiary.Sex getSex() {
        return sex;
    }

    public void setSex(Beneficiary.Sex sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Period getAge() {
        return age;
    }

    public void setAge(Period age) {
        this.age = age;
    }

    public int getIdUserCreate() {
        return idUserCreate;
    }

    public void setIdUserCreate(int idUserCreate) {
        this.idUserCreate = idUserCreate;
    }

    public int getIdUserUpdate() {
        return idUserUpdate;
    }

    public void setIdUserUpdate(int idUserUpdate) {
        this.idUserUpdate = idUserUpdate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }    
}
