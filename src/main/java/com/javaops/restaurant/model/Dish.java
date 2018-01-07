package com.javaops.restaurant.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dishes")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dish {
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
    private Long price;

    @Builder
    public Dish(final String name, final Long price) {
        this(null, name, price);
    }
}
