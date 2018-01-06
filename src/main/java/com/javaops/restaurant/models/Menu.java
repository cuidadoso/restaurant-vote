package com.javaops.restaurant.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="menus")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    @NonNull
    private String restaurantId;
    @Getter
    @Setter
    @NonNull
    private LocalDate date;

    @Builder
    public Menu(final String restaurantId, final LocalDate date) {
        this(null, restaurantId, date);
    }
}
