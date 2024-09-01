package com.mccraftaholics.warpportals.server.model.usage;

import com.google.common.base.Joiner;
import com.mccraftaholics.warpportals.common.model.SimplePortal;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "portal_create")
public class AnalyticsPortalCreateEntity implements Serializable {
    private static final long serialVersionUID = -1798070566993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long hour;
    private String portalId;
    private String name;
    private String message;
    private String material;
    private String blocks;
}

