package com.hr.system.manage.web.controller.vo;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 17:38
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class AccountVo extends BaseVo{
    private String username;
    private String oldPwd;
    private String newOwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewOwd() {
        return newOwd;
    }

    public void setNewOwd(String newOwd) {
        this.newOwd = newOwd;
    }
}
