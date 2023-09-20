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
@Table(name = "te_gpt_product_placeholder")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class ProductPlaceholderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ph_id")
    private int phId;

    @ManyToOne
    @JoinColumn(name = "item_id", updatable = false, nullable = false)
    private ProductItemEntity ProductItemEntity;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "state")
    private int state;


}
