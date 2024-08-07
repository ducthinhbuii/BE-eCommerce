package com.example.ecommerce.services.oauth2;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.services.CartService;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OidcUserRequest, OidcUser> delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String[] partName = name.split(" ", 2);

        Optional<Users> user = userRepository.findByUsername(email);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if(user.isEmpty()){
            Users u = new Users();
            u.setUsername(email);
            u.setFirstName(partName[0]);
            u.setLastName(partName[1]);
            LocalDateTime now = LocalDateTime.now(); 
            u.setCreateAt(now);
            u.setRole("USER");
            authorities.add(new SimpleGrantedAuthority("USER"));
            System.out.println("save user google database");
            Users savedUsers = userRepository.save(u);
            cartService.createCart(savedUsers);
        } else {
            String[] roles = getRoles(user.get());
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            }
        }

        System.out.println(oidcUser.getAuthorities());  
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

    }

    public String[] getRoles(Users user){
        if(user.getRole() == null){
            return new String[]{"USER"};
        } else {
            return user.getRole().split(",");
        }
    }
    
}
