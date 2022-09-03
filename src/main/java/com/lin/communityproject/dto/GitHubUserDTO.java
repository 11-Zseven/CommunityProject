package com.lin.communityproject.dto;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
public class GitHubUserDTO {
    private String id;
    private String login;
    private String bio;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}
