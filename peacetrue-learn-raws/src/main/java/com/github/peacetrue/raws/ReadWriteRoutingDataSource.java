package com.github.peacetrue.raws;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 读写分离路由数据源
 *
 * @author xiayx
 */
public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {

    public static final String
            TYPE_WRITE = "write",
            TYPE_READ = "read";

    protected String determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? TYPE_READ
                : TYPE_WRITE;
    }
}
