package com.javaops.restaurant.models;

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
    private String name;
    @Getter
    @Setter
    private String address;

    @Builder
    public Restaurant(final String name, final String address) {
        this(null, name, address);
    }
}
