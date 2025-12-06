package com.lunarsonic.geometryservice.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "points")
@Getter
@Setter
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coordinate_x", nullable = false)
    private float x;

    @Column(name = "coordinate_y", nullable = false)
    private float y;

    @Column(name = "radius", nullable = false)
    private float r;

    @Column(name = "is_hit", nullable = false)
    private boolean hit;

    @Column(name = "server_time", nullable = false)
    private String serverTime;

    @Column(name = "script_time", nullable = false)
    private long scriptTime;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private PointGroup pointGroup;
}
