package com.leadlet.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetail extends User{

    private Long appAccountId;
    private Long userId;


    public AppUserDetail(Long userId, Long appAccountId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.appAccountId = appAccountId;
        this.userId = userId;
    }

    public AppUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AppUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public Long getUserId() {
        return userId;
    }
}
