package com.mccraftaholics.warpportals.server.model.generic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "server_report_portals")
public class AnalyticsActivePortalsEntity {
    private static final long serialVersionUID = -1278070786983154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String portalId;

    @ManyToOne()
    @JoinColumn(name = "server_report_id")
    private AnalyticsServerReportEntity serverReport;
}
