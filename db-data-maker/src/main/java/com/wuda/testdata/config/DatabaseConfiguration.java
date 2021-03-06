package com.wuda.testdata.config;

import com.wuda.testdata.cli.CliArgs;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据库相关配置.
 *
 * @author wuda
 */
@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource getDataSource() {
        PoolConfiguration poolProperties = new PoolProperties();
        poolProperties.setDriverClassName("oracle.jdbc.OracleDriver");
        return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
    }

    /**
     * 数据库连接的url,username,password等可以从命令行传入,
     * 使用命令行传入的数据库连接参数.
     *
     * @param cliArgs 命令行参数
     */
    public static void applyArgs(DataSource dataSource, CliArgs cliArgs) {
        org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
        tomcatDataSource.setUrl(cliArgs.getUrl());
        if (StringUtils.isNoneBlank(cliArgs.getUsername())) {
            tomcatDataSource.setUsername(cliArgs.getUsername());
        }
        if (StringUtils.isNoneBlank(cliArgs.getPassword())) {
            tomcatDataSource.setPassword(cliArgs.getPassword());
        }
        tomcatDataSource.setMaxActive(cliArgs.getMaxConnection());
    }
}
