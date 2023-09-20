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
@Table(name = "te_gpt_mytool")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class MytoolEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //sequence
    @Column(name = "tool_id")
    private Long toolId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false) //fk
    private UserEntity userEntity;

    @Column(name = "title")
    private String title;

    @Column(name = "placeholder")
    private String placeholder;

    //span 1~5 null 가능
    @Column(name = "span1", nullable = true)
    private String span1;

    @Column(name = "span2", nullable = true)
    private String span2;

    @Column(name = "span3", nullable = true)
    private String span3;

    @Column(name = "span4", nullable = true)
    private String span4;

    @Column(name = "span5", nullable = true)
    private String span5;

    @Column(name = "cts")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime cts;

    @Column(name = "state")
    private int state;




}
