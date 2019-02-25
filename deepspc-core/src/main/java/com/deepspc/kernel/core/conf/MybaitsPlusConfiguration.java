package com.deepspc.kernel.core.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.deepspc.kernel.core.peroperties.DruidProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"**.mapper"})
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
public class MybaitsPlusConfiguration {

    @Autowired
    private DruidProperties druidProperties;

    /**
     * 数据库配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

    /**
     * 数据库连接池
     */
    @Bean(initMethod = "init")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }
}
