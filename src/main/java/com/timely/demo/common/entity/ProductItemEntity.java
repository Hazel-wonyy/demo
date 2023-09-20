package com.timely.demo.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timely.demo.common.config.jpa.BaseEntity;
import com.timely.demo.common.config.security.model.ProviderType;
import com.timely.demo.common.config.security.model.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "te_gpt_product_item")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class ProductItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "pid", updatable = false, nullable = false)
    private ProductEntity productEntity;

    @Column(name = "type")
    private int type;

    @Column(name = "title")
    private String title;

    @Column(name = "state")
    private int state;

}
