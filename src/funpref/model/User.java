/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model;

/**
 *
 * @author Robson Santana
 */
public class User {
    
    private int id;
    private int idPermition;
    private String login;
    private String cpf;
    private String name;
    private String office;
    private String password;    
    private boolean active;
    private int loginScreenResult; // TODO: remover

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPermition() {
        return idPermition;
    }

    public void setIdPermition(int idPermition) {
        this.idPermition = idPermition;
    }    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }    

    // TODO: remover
    public int getLoginScreenResult() {
        return loginScreenResult;
    }

    public void setLoginScreenResult(int loginScreenResult) {
        this.loginScreenResult = loginScreenResult;
    }    
}
