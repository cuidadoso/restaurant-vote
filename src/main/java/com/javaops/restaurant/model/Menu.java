package com.javaops.restaurant.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection="menus")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu {
    @Id
    @Getter
    @Setter
    @Builder.Default
    private String id = null;
    @Getter
    @Setter
    @NonNull
    private String restaurantId;
    @Getter
    @Setter
    @NonNull
    private LocalDate date;
    @DBRef
    @Getter
    @Setter
    @NonNull
    private List<Dish> dishes;
}
