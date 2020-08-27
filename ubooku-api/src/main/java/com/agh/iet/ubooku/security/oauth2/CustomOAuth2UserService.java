package com.agh.iet.ubooku.security.oauth2;

import com.agh.iet.ubooku.exception.auth.OAuth2AuthenticationProcessingException;
import com.agh.iet.ubooku.model.auth.AuthProvider;
import com.agh.iet.ubooku.model.auth.Role;
import com.agh.iet.ubooku.model.auth.RoleName;
import com.agh.iet.ubooku.model.auth.User;
import com.agh.iet.ubooku.repository.UserRepository;
import com.agh.iet.ubooku.security.UserPrincipal;
import com.agh.iet.ubooku.security.oauth2.user.OAuth2UserInfo;
import com.agh.iet.ubooku.security.oauth2.user.OAuth2UserInfoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
           user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateUser(user, oAuth2UserInfo);

        } else {

            user = registerUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo auth2UserInfo) {
        User user = User.builder()
                .roles(new HashSet<>(Collections.singleton(new Role(RoleName.ROLE_USER))))
                .email(auth2UserInfo.getEmail())
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(auth2UserInfo.getId())
                .firstName(auth2UserInfo.getFirstName())
                .lastName(auth2UserInfo.getLastName())
                .imageUrl(auth2UserInfo.getImageUrl())
                .build();

        return userRepository.save(user);
    }

    private User updateUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
