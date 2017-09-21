package com.leadlet.security;

import com.leadlet.domain.AppAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetail extends User{

    private AppAccount appAccount;


    public AppUserDetail(AppAccount appAccount, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.appAccount = appAccount;
    }

    public AppUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AppUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }
}
