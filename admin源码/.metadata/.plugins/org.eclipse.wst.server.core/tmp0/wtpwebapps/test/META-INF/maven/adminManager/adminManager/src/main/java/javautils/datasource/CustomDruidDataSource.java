package javautils.datasource;

import java.util.ArrayList;
import com.alibaba.druid.filter.Filter;
import java.util.List;
import com.alibaba.druid.pool.DruidDataSource;

public class CustomDruidDataSource extends DruidDataSource
{
    public List<Filter> getProxyFilters() {
        final List<Filter> proxyFilters = new ArrayList<Filter>();
        proxyFilters.add((Filter)ConfigDruidDataSource.configLog4jFilter());
        proxyFilters.add((Filter)ConfigDruidDataSource.configStatFilter());
        return proxyFilters;
    }
}
