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
@Table(name = "te_gpt_product")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long pid;

    @Column(name = "value")
    private String value;

    @Column(name = "label")
    private String label;

    @Column(name = "state")
    private int state;

}
