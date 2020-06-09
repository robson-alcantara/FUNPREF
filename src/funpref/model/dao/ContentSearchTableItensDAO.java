/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model.dao;

/**
 *
 * @author secretaria
 */
public class ContentSearchTableItensDAO {
    private int id;
    private int enrollment;
    private String name;

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int id) {
        this.enrollment = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
