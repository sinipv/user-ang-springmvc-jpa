package org.home.user.vo;

import java.util.List;

import org.home.user.model.User;



public class UserListVO {

    private int pagesCount;
    private long totalUsers;

    private String actionMessage;
    private String searchMessage;

    private List<User> Users;

    public UserListVO() {
    }

    public UserListVO(int pages, long totalUsers, List<User> Users) {
        this.pagesCount = pages;
        this.Users = Users;
        this.totalUsers = totalUsers;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> Users) {
        this.Users = Users;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }


}
