package com.tidev.database.entity;

import com.tidev.database.entity.fields.Status;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "createdAt")
@ToString(exclude = {"user", "orderProducts"})
@Builder
@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = {"createdAt"}))
public class Order implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime endAt;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void addOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts.addAll(orderProducts);
        orderProducts.forEach(it -> it.setOrder(this));
    }

    public void increasePrice(BigDecimal productTotalPrice) {
        price = price.add(productTotalPrice);
    }

    public Order updatePrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderProduct orderProduct : orderProducts) {
            sum = sum.add(orderProduct.getTotalPrice());
        }
        price = sum;
        return this;
    }
}
