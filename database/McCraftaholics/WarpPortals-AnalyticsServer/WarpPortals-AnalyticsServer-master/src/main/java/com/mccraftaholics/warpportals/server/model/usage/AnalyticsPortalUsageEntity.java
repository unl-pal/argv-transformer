package com.mccraftaholics.warpportals.server.model.usage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "portal_use")
public class AnalyticsPortalUsageEntity implements Serializable {
    private static final long serialVersionUID = -1798070566993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String portalId;
    private Long hour;
    private Integer numberOfUses;
}

