package com.leadlet.service.dto;


import com.leadlet.domain.User;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * A DTO for the Deal entity.
 */
public class UserSearchIndexDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastname;
    private String login;
    private Long appAccountId;

    public UserSearchIndexDTO(){

    }

    public UserSearchIndexDTO(User user){

        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastname = user.getLastName();
        this.login = user.getLogin();
        this.appAccountId = user.getAppAccount().getId();

    }

    public Long getId() {
        return id;
    }

    public UserSearchIndexDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public UserSearchIndexDTO setAppAccountId(Long appAccountId) {
        this.appAccountId = appAccountId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public XContentBuilder getBuilder() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", getId());
            builder.field("first_name", getFirstName());
            builder.field("last_name", getLastname());
            builder.field("login", getLogin());
            builder.field("app_account_id", getAppAccountId());

        }
        builder.endObject();

        return builder;
    }
}


