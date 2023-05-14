package com.divary.carservice.model;

import com.divary.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "brand")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Brand extends AuditEntity {

    private static final long serialVersionUID = 4972541060198676496L;
    @Id
    @GeneratedValue(generator = "car_brand", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "car_brand", strategy = "uuid")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDelete;
}
