package com.accio.isegye.menu.entity;

import com.accio.isegye.customer.entity.Customer;
import com.accio.isegye.turtle.entity.TurtleLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    private int orderStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deliveredAt;

    //터틀 로그
    @OneToMany(mappedBy = "orderMenu", fetch = FetchType.LAZY)
    private final List<TurtleLog> turtleLogList = new ArrayList<>();

    //주문 상세
    @OneToMany(mappedBy = "orderMenu", fetch = FetchType.LAZY)
    private final List<OrderMenuDetail> orderMenuDetailList = new ArrayList<>();

    @OneToMany(mappedBy = "orderMenu", fetch = FetchType.LAZY)
    private final List<OrderMenuStatusLog> statusLogList = new ArrayList<>();

}
