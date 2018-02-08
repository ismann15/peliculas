package model;

public class Administrador {

    private String adminUser;
    private String adminPass;

    public Administrador() {
    }

    public Administrador(String adminUser, String adminPass) {
        this.adminUser = adminUser;
        this.adminPass = adminPass;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }
}
