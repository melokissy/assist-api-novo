///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package Autenticacao;

import io.jsonwebtoken.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import model.User;

//@Provider
//@Priority(Priorities.AUTHENTICATION)
//public class MCRSessionFilter implements ContainerRequestFilter, ContainerResponseFilter {
public class JWTTokenUtils implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final String secret = "assistsupport";

//Sample method to construct a JWT
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getEmail());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, this.secret).compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = getUserEmailFromToken(token);

//        ResetToken resetToken = this.resetTokenRepository.findByToken(token);
//        if (resetToken != null) {
//            return false;
//        }

        return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    public String getUserEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

}
