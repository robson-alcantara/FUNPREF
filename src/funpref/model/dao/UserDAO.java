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
public class UserDAO {
    
    private String login;
    private long cpf;
    private String name;
    private String office;
    private String password;
    private int id;
    private int loginScreenResult;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoginScreenResult() {
        return loginScreenResult;
    }

    public void setLoginScreenResult(int loginScreenResult) {
        this.loginScreenResult = loginScreenResult;
    }
}