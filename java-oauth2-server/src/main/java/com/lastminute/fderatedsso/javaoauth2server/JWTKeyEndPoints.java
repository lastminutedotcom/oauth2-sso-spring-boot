package com.lastminute.fderatedsso.javaoauth2server;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import net.minidev.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import static java.util.Arrays.asList;

@FrameworkEndpoint
public class JWTKeyEndPoints {

    @GetMapping("/sign-key/public")
    public ResponseEntity key() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "secret".toCharArray());
        KeyPair keypair = keyStoreKeyFactory.getKeyPair("keypair");

        JWK jwk = new RSAKey.Builder((RSAPublicKey) keypair.getPublic())
                .privateKey((RSAPrivateKey) keypair.getPrivate())
                .keyUse(KeyUse.SIGNATURE)
                .keyID("resource")
                .build();


        return ResponseEntity.ok(new Jwts(asList(jwk.toJSONObject())));
    }
}

class Jwts {
    public List<JSONObject> keys;

    public Jwts(List<JSONObject> keys){
        this.keys = keys;
    }

}