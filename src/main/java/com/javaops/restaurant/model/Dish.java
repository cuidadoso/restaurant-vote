package com.javaops.restaurant.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dishes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dish {
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
    private Long price;
}
