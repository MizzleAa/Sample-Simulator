package com.mizzle.simulator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;

@DynamicUpdate
@Getter
@Entity
@Table(name = "file_names")
public class FileNames extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dashboard")
    private Dashboard dashboard;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "name", nullable = false)
    private String name;
    
    public FileNames(){}

    @Builder
    public FileNames(Dashboard dashboard, String name, String path){
        this.dashboard = dashboard;
        this.name = name;
        this.path = path;
    }

}
