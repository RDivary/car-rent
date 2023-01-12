package com.divary.carservice.model;

import com.divary.audit.AuditEntity;
import com.divary.carservice.enums.Engine;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends AuditEntity {

    private static final long serialVersionUID = 8765137319993553108L;

    @Id
    @GeneratedValue(generator = "car_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "car_uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(nullable = false)
    private int passenger;

    @Enumerated
    @Column(nullable = false)
    private Engine engine;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDelete;
}
