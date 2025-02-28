package com.example.Shop.Service;

import com.example.Shop.model.User;
import com.example.Shop.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.security.Key;
import java.security.Signature;
import java.util.Date;

@Service
public class JwtService {

    private final String secretKey = "TLNgJTmFRb9ZOf8eFL3AzxidK7QrEye19stOp2hUHG7E8ryVX6";
    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String generateJwtToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 60*60*1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Key getSigningKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String validateJwtToken(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("username",String.class);
    }
}
