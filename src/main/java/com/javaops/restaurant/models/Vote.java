package com.javaops.restaurant.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="votes")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vote {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    @NonNull
    private String userId;
    @Getter
    @Setter
    @NonNull
    private String restaurantId;
    @Getter
    @Setter
    @NonNull
    private LocalDateTime time;

    @Builder
    public Vote(final String userId, final String restaurantId, final LocalDateTime time) {
        this(null, userId, restaurantId, time);
    }
}
