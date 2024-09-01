package com.mccraftaholics.warpportals.server.model.usage;

import com.mccraftaholics.warpportals.server.model.usage.AnalyticsPortalUsageEntity;
import org.springframework.data.repository.CrudRepository;

public interface PortalUsageReportRepository extends CrudRepository<AnalyticsPortalUsageEntity, Long> {
}
