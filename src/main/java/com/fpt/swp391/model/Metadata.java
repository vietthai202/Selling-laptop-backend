package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metadata")
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // mã icon
    private String icon;
    // có thể dùng nhiều loại icon ví dụ fontawesome, material icon,...
    private String iconType;
    private String title;
    private String content;
    @ManyToOne()
    @JoinColumn(name = "laptop_id", nullable = false)
    private Laptop laptop;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private MetadataGroup metadataGroup;
}
