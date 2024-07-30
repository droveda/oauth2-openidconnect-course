package com.droveda.examples;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.X509CertUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class MyJWSExample {

    public void createJWS() throws Exception {

        var certificate = getCertificate();

        //create claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("scopre", "my-scope");
        claims.put("iss", "my-issuer");
        claims.put("aud", "my-audience");
        claims.put("sub", "my-subject");
        claims.put("exp", LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        );
        claims.put("iat", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        claims.put("jti", "aabbccdd");
        claims.put("accessId", "123456");

        //creating my JWS
        JWSObject jws = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.PS256)
                        .keyID(certificate.getSerialNumber().toString())
                        .build(),
                new Payload(claims)
        );

        //creating the signer
        RSASSASigner signer = new RSASSASigner(getPrivateKey());
        signer.getJCAContext().setProvider(BouncyCastleProviderSingleton.getInstance());

        //signing the token
        jws.sign(signer);

        System.out.println(jws.serialize());

        JWKSet jwkSet = new JWKSet(JWK.parse(certificate));

        System.out.println(jwkSet.toPublicJWKSet().toJSONObject());
    }

    private X509Certificate getCertificate() throws IOException {
        return X509CertUtils.parse(readFile("my-file.pem"));
    }

    private RSAPrivateKey getPrivateKey() throws IOException, JOSEException {
        return RSAKey.parseFromPEMEncodedObjects(readFile("my-file.pem"))
                .toRSAKey()
                .toRSAPrivateKey();
    }

    private String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

}
