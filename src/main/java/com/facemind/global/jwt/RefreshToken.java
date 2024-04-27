package com.facemind.global.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id @Column(name = "email")
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    public RefreshToken updateValue(String token){
        this.refreshToken = token;
        return this;
    }
}
