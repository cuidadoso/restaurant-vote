package com.javaops.restaurant.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="restaurants")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Restaurant {
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
    private String address;

    @Builder
    public Restaurant(final String name, final String address) {
        this(null, name, address);
    }
}
