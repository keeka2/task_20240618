package com.example.olc.common.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
    private static final String READ_WRITE_DATA_SOURCE_KEY = "read-write";
    private static final String READ_ONLY_DATA_SOURCE_KEY = "read-only";

    public RoutingDataSource(
            final DataSource readWriteDataSource, final DataSource readOnlyDataSource) {
        super();
        final Map<Object, Object> dataSourceMap =
                Map.ofEntries(
                        Map.entry(RoutingDataSource.READ_WRITE_DATA_SOURCE_KEY, readWriteDataSource),
                        Map.entry(RoutingDataSource.READ_ONLY_DATA_SOURCE_KEY, readOnlyDataSource));
        super.setTargetDataSources(dataSourceMap);
        super.setDefaultTargetDataSource(readWriteDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? RoutingDataSource.READ_ONLY_DATA_SOURCE_KEY
                : RoutingDataSource.READ_WRITE_DATA_SOURCE_KEY;
    }
}
