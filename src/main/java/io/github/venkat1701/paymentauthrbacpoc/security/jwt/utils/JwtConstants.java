package io.github.venkat1701.paymentauthrbacpoc.security.jwt.utils;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstants {

    @Value("${jwt.signing.key}")
    public static String SECRET_KEY = "CzqMVKK8TmHHFjhoZNfAmkxOSL2hXTJmUsxCmsPg4bHwUDN26Hxzv49hFZ+s1gkjWx6k+/XNTqlh2zpELC0opQ==";

    @Value("${jwt.header.string}")
    public static String JWT_HEADER = "Authorization";
}
