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
@Table(name = "t_user", uniqueConstraints = {@UniqueConstraint(name = "unique_tuser_provider_uid", columnNames = {"provider_type", "email"})})
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long userId;

    @Column(name = "provider_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(length = 80)
    @Size(min = 3, max = 80)
    private String uid;

    @JsonIgnore
    private String upw;

    @Column(length = 20)
    @Size(min = 2, max = 20)
    private String unm;


    @JsonIgnore
    @Column(name = "role_type", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;


    @Size(min = 10, max = 60)
    @Column(length = 60)
    private String email;

    @ColumnDefault("0")
    private Long delYn;


}
