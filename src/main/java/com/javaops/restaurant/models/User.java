package com.javaops.restaurant.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
@NoArgsConstructor
public class User {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;

    public User(final String id, final String name, final String email, final String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(final String name, final String email, final String password) {
        this(null, name, email, password);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                                        .append("name", name)
                                        .append("email", email)
                                        .append("password", password)
                                        .toString();
    }
}
