package com.xco.spactshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    private String _id;
    private String user;
    private List<OrderItem> orderItems;
    private ShippingAddress shippingAddress;
    private String paymentMethod;
    private PaymentResult paymentResult;
    private Double itemsPrice = 0.0;
    private Double taxPrice = 0.0;
    private Double shippingPrice = 0.0;
    private Double totalPrice = 0.0;
    private Boolean isPaid = false;
    private Instant paidAt;
    private Boolean isDelivered = false;
    private Instant deliveredAt;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Data
    public static class OrderItem {

        @MongoId
        String _id;
        String name;
        Integer qty;
        String image;
        Double price;
        String product;

        public OrderItem() {
            this._id = ObjectId.get().toHexString();
        }
    }

    @Data
    public static class ShippingAddress {

        String address;
        String city;
        String postalCode;
        String country;
    }

}
