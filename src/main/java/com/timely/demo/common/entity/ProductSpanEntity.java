package com.timely.demo.common.entity;

import com.timely.demo.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "te_gpt_product_span")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class ProductSpanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "span_id")
    private int spanId;

    @ManyToOne
    @JoinColumn(name = "item_id", updatable = false, nullable = false)
    private ProductItemEntity ProductItemEntity;

    @Column(name = "value")
    private String value;

    @Column(name = "state")
    private int state;


}
