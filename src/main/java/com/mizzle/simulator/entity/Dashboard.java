package com.mizzle.simulator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;

@DynamicUpdate
@Getter
@Entity
@Table(name = "dashboard")
public class Dashboard extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "content", nullable = false, length = 256)
    private String content;

    @Column(name = "path", nullable = false, length = 512)
    private String path;

    @Column(name = "raw", columnDefinition = "LONGTEXT", nullable = false)
    private String raw;

    public Dashboard(){}

    @Builder
    public Dashboard(String title, String content, String path, String raw){
        this.title = title;
        this.content = content;
        this.path = path;
        this.raw = raw;
    }
}
