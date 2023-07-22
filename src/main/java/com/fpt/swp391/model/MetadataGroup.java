package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metadata_group")
public class MetadataGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String name;
    @OneToMany(mappedBy = "metadataGroup")
    private Set<Metadata> metadatas;
}
