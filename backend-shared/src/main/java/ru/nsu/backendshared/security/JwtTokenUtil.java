package ru.nsu.backendshared.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import ru.nsu.backendshared.model.UserAuthenticationToken;
import ru.nsu.backendshared.model.UserUuidAuthenticationToken;

import java.text.ParseException;
import java.util.Date;

public class JwtTokenUtil {

    //256 - bit (32 byte) shared secret
    private final String jwtSecret = "bezKDVOJiI2m49whDqIJnhK8ai6073Q5";
    private final Integer accessTokenDuration = 120 * 1000;
    private final Integer refreshTokenDuration = 30 * 60 * 1000;
    private final JWSSigner signer;
    private final JWSVerifier verifier;

    public JwtTokenUtil() throws Exception {
        this.signer = new MACSigner(jwtSecret);
        this.verifier = new MACVerifier(jwtSecret);
    }

    public String generateToken(String userId, boolean isAccess) throws JOSEException {
        var claimSet = new JWTClaimsSet
                .Builder()
                .subject(userId)
                .expirationTime(new Date(new Date().getTime() +
                        (isAccess
                                ? accessTokenDuration
                                : refreshTokenDuration)))
                .build();

        var signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimSet);
        signedJwt.sign(signer);
        return signedJwt.serialize();
    }

    public boolean verifyToken(String token) throws ParseException, JOSEException {
        var signedJwt = SignedJWT.parse(token);
        return signedJwt.verify(verifier)
                && (new Date().before(signedJwt.getJWTClaimsSet().getExpirationTime()));
    }

    public UserUuidAuthenticationToken parseToken(String token) throws Exception {
        var signedJwt = SignedJWT.parse(token);
        return new UserUuidAuthenticationToken(signedJwt.getJWTClaimsSet().getSubject());
    }
}