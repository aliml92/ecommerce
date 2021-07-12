package com.xco.spactshop.dto;

import java.util.List;

public class OrderReq {

    private Double itemsPrice;
    private List<orderItem> orderItems;
    private String paymentMethod;
    private shippingAddress shippingAddress;
    private Double shippingPrice;
    private Double taxPrice;
    private Double totalPrice;

    public static class orderItem{
        Integer countInStock;
        String image;
        String name;
        Double price;
        String product;
        Integer qty;

        public orderItem() {
        }

        public Integer getCountInStock() {
            return countInStock;
        }

        public void setCountInStock(Integer countInStock) {
            this.countInStock = countInStock;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }
    }

    public static class shippingAddress {
        String address;
        String city;
        String country;
        String postalCode;

        public shippingAddress() {
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    public OrderReq() {
    }

    public Double getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(Double itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public List<orderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<orderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderReq.shippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(OrderReq.shippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shipppingPrice) {
        this.shippingPrice = shipppingPrice;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderReq{" +
                "itemsPrice=" + itemsPrice +
                ", orderItems=" + orderItems +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", shippingPrice=" + shippingPrice +
                ", taxPrice=" + taxPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
