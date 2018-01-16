package com.javaops.restaurant.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="votes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vote {
    @Id
    @Getter
    @Setter
    @Builder.Default
    private String id = null;
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
}
