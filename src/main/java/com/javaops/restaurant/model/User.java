package com.javaops.restaurant.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @Getter
    @Setter
    @Builder.Default
    private String id = null;
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
    @Getter
    @Setter
    @NonNull
    @Builder.Default
    private boolean enabled = true;
}
