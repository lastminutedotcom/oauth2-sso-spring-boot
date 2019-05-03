package com.lastminute.fderatedsso.javaoauth2server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * Created by mrflick72 on 03/05/17.
 */

@Configuration
@EnableAuthorizationServer
public class SecurityOAuth2AutorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public SecurityOAuth2AutorizationServerConfig(UserDetailsService userDetailsService,
                                                  AuthenticationManager authenticationManager,
                                                  PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.approvalStoreDisabled()
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .passwordEncoder(passwordEncoder)
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("client-secret"))
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .authorities("ROLE_USER", "ROLE_EMPLOYEE")
                .scopes("read", "write", "trust", "openid")
                .resourceIds("oauth2-resource")
                .autoApprove(true)
                .redirectUris( "http://localhost:8080/bootifull-21-site/login/oauth2/code/client",
                        "http://localhost:8081/bootifull-15-site/login")
                .accessTokenValiditySeconds(60 * 100 * 5)
                .refreshTokenValiditySeconds(60 * 60 * 8);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "secret".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("keypair");
        return new RsaJwtAccessTokenConverter("resource", keyPair);
    }

}