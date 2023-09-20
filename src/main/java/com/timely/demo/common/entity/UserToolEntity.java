package com.timely.demo.common.entity;

import com.timely.demo.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "te_gpt_user_tool")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class UserToolEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private long resultId;

    @ManyToOne
    @JoinColumn(name = "tool_id", updatable = false, nullable = false)
    private MytoolEntity mytoolEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity userEntity;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(name = "cts")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime cts;

    @Column(name = "state")
    private int state;
}
