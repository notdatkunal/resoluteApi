package com.resolute.zero.models;


import com.resolute.zero.responses.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    public User(String userName, String password){

        this.userName = userName;
        this.password= password;
    }

    public UserModel getUserModel(){


        return UserModel.builder()
                .id(this.userId)
                .status(true)
                .username(this.userName)
                .role(this.role)
                .build();



    }

}
