package com.mccraftaholics.warpportals.server.model.generic;

import com.mccraftaholics.warpportals.common.model.analytics.reports.AnalyticsReportServer;
import com.mccraftaholics.warpportals.server.Utils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "server_report")
public class AnalyticsServerReportEntity implements Serializable {
    private static final long serialVersionUID = -1798070786983154676L;

    public String serverName;
    public Date timestamp;
    public String warpPortalsVersion;
    public int maxPlayers;
    public String bukkitVersion;
    public int numWorlds;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serverReport")
    private Set<AnalyticsInstalledPluginsEntity> installedPlugins = new HashSet<AnalyticsInstalledPluginsEntity>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serverReport")
    private Set<AnalyticsActivePortalsEntity> activePortals = new HashSet<AnalyticsActivePortalsEntity>();

}

