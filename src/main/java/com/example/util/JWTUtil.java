package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exp.UnAuthorizedException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {

    private static final String secretKey = "!maz234^gikey";
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-hour

    private static final int emailTokenLiveTime = tokenLiveTime * 24; // 1-day
    public static String encode(String id, String email) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("email", email);
        jwtBuilder.claim("id", id);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Youtube test portali");
        return jwtBuilder.compact();
    }
    public static JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            String email = (String) claims.get("email");
            String id = (String) claims.get("id");
            return new JwtDTO(id,email);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }


    public static String encodeEmailJwt(String profileId, String email) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("email", email);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.setIssuer("Youtube test portali");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeEmailJwt(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            String id = (String) claims.get("id");
            String email = (String) claims.get("email");
            return new JwtDTO(id, email);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }
}
