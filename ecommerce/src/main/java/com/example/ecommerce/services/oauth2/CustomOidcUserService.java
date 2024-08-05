package com.example.ecommerce.services.oauth2;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OidcUserRequest, OidcUser> delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);

        // Lấy thông tin người dùng từ Google
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        System.out.println(email);
        
        return new DefaultOidcUser(null, oidcUser.getIdToken(), oidcUser.getUserInfo());

    }
    
}
