package com.mccraftaholics.warpportals.server.model.usage;

import com.mccraftaholics.warpportals.server.model.usage.AnalyticsPortalCreateEntity;
import org.springframework.data.repository.CrudRepository;

public interface PortalCreateReportRepository extends CrudRepository<AnalyticsPortalCreateEntity, Long> {
}
