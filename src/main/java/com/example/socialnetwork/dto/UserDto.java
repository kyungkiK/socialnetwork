package com.example.socialnetwork.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", updatable = false, unique = true, nullable = false)
    private Long id;

    private String userId;

    private String password;

    private Authority authority;

    @Builder
    public UserDto(String userId, String password, Authority authority) {
        this.userId = userId;
        this.password = password;
        this.authority = authority;
    }
}
