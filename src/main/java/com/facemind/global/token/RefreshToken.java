package com.facemind.global.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id @Column(name = "refresh_token_key")
    private String key;  // member id

    @Column(name = "refresh_token_value")
    private String value; //token

    public RefreshToken updateValue(String token){
        this.value = token;
        return this;
    }
}
