package com.mccraftaholics.warpportals.server;

import com.mccraftaholics.warpportals.common.model.SimplePortal;
import com.mccraftaholics.warpportals.common.model.analytics.objects.AnalyticsUsage;
import com.mccraftaholics.warpportals.common.model.analytics.reports.AnalyticsReportServer;
import com.mccraftaholics.warpportals.common.model.analytics.reports.AnalyticsReportUsage;
import com.mccraftaholics.warpportals.server.model.generic.AnalyticsServerReportEntity;
import com.mccraftaholics.warpportals.server.model.generic.GenericServerReportRepository;
import com.mccraftaholics.warpportals.server.model.usage.AnalyticsPortalCreateEntity;
import com.mccraftaholics.warpportals.server.model.usage.AnalyticsPortalUsageEntity;
import com.mccraftaholics.warpportals.server.model.usage.PortalCreateReportRepository;
import com.mccraftaholics.warpportals.server.model.usage.PortalUsageReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    GenericServerReportRepository genericReportRepo;
    @Autowired
    PortalCreateReportRepository portalCreateRepo;
    @Autowired
    PortalUsageReportRepository portalUsageRepo;
}