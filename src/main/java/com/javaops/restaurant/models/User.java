package com.javaops.restaurant.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    @NonNull
    private String name;
    @Getter
    @Setter
    @NonNull
    private String email;
    @Getter
    @Setter
    @NonNull
    private String password;

    @Builder
    public User(final String name, final String email, final String password) {
        this(null, name, email, password);
    }
}
