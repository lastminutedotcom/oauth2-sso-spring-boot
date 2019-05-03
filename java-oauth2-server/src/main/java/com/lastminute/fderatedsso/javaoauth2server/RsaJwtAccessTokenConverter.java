package com.lastminute.fderatedsso.javaoauth2server;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class RsaJwtAccessTokenConverter extends JwtAccessTokenConverter {

    /**
     * Field name for token id.
     */
    public static final String JKW_ID = "kid";

    /**
     * Field name for access token id.
     */

    private final String jwk;
    private final KeyPair keyPair;

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private JsonParser objectMapper = JsonParserFactory.create();

    private String verifierKey = new RandomValueStringGenerator().generate();

    private Signer signer;
    private SignatureVerifier verifier;

    public RsaJwtAccessTokenConverter(String jwk, KeyPair keyPair) {
        this.jwk = jwk;
        this.keyPair = keyPair;
        applyKeyPair();
    }

    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = objectMapper.formatMap(tokenConverter.convertAccessToken(accessToken, authentication));
        } catch (Exception e) {
            throw new IllegalStateException("Cannot convert access token to JSON", e);
        }
        return JwtHelper.encode(content, signer, jwtHeaders()).getEncoded();
    }

    Map jwtHeaders() {
        Map headers = new HashMap();
        headers.put(JKW_ID, jwk);
        return headers;
    }

    void applyKeyPair() {
        PrivateKey privateKey = keyPair.getPrivate();
        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA ");
        this.signer = new RsaSigner((RSAPrivateKey) privateKey);
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        this.verifier = new RsaVerifier(publicKey);
        this.verifierKey = "-----BEGIN PUBLIC KEY-----\n" + new String(Base64.encode(publicKey.getEncoded()))
                + "\n-----END PUBLIC KEY-----";
    }


    public void setKeyPair(KeyPair keyPair) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the JWT signing key. It can be either a simple MAC key or an RSA key. RSA keys
     * should be in OpenSSH format, as produced by <tt>ssh-keygen</tt>.
     *
     * @param key the key to be used for signing JWTs.
     */
    public void setSigningKey(String key) {
        throw new UnsupportedOperationException();
    }

}