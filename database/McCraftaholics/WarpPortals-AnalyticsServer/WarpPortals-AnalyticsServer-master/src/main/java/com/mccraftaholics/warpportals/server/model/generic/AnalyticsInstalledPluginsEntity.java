package com.mccraftaholics.warpportals.server.model.generic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "server_report_plugins")
public class AnalyticsInstalledPluginsEntity implements Serializable {
    private static final long serialVersionUID = -1798070566983154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    private String version;

    @ManyToOne()
    @JoinColumn(name = "server_report_id")
    private AnalyticsServerReportEntity serverReport;
}
