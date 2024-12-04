package entity;

import java.util.List;

public class Account extends AbstractModelBean {
    private int id;
    private String username;
    private String password;
    private List<Role> roles;


}
