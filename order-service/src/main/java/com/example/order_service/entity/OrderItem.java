package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "order_item")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// один заказ может содержать несколько товаров. Без этой сущности не будет доп данных и нарушим нормализацию
// OrderItem - строки заказа, с конкретными данными
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;
}

/*
Заказ №1:
    Товар: Молоко, Кол-во: 2 шт.

    Товар: Хлеб, Кол-во: 1 шт.

    Товар: Яблоки, Кол-во: 5 шт.

    Заказ 1 - Order
    Товары - OrderItem
 */