package com.javaops.restaurant.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="restaurants")
@NoArgsConstructor
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

    public Restaurant(final String id, final String name, final String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Restaurant(final String name, final String address) {
        this(null, name, address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                                        .append("name", name)
                                        .append("address", address)
                                        .toString();
    }
}
