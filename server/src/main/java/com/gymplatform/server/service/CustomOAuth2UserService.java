package com.gymplatform.server.service;

import com.gymplatform.server.model.User;
import com.gymplatform.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String provider = userRequest.getClientRegistration().getRegistrationId(); // "Google" or "GitHub"

        String oauthId = attributes.get("sub") != null
                ? attributes.get("sub").toString() // Google uses "sub" field for ID
                : attributes.get("id").toString(); // GitHub uses "id"

        String name = attributes.get("name").toString();
        String email = attributes.get("email").toString();
        String picture = attributes.get("picture") != null
                ? attributes.get("picture").toString()
                : (attributes.get("avatar_url") != null ? attributes.get("avatar_url").toString() : null); // GitHub avatar

        // Check if user already exists
        Optional<User> existingUser = userRepository.findByOauthIdAndProvider(oauthId, provider);

        if (existingUser.isEmpty()) {
            // New user - save into MongoDB
            User newUser = User.builder()
                    .oauthId(oauthId)
                    .provider(provider)
                    .name(name)
                    .email(email)
                    .profilePicture(picture)
                    .build();

            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}