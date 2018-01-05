package com.javaops.restaurant.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dishes")
@NoArgsConstructor
public class Dish {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Long price;

    public Dish(final String id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Dish(final String name, final Long price) {
        this(null, name, price);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                                        .append("name", name)
                                        .append("price", price)
                                        .toString();
    }
}
