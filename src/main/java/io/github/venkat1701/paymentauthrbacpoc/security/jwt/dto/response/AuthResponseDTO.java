package io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto.response;

public class AuthResponseDTO {
    private String jwt;
    private String message;

    public AuthResponseDTO(String jwt, String message) {
        this.jwt = jwt;
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
