package com.example.proxyqueryplanexample.config;

import com.example.proxyqueryplanexample.listener.SlowQueryExplainListener;
import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//dataSourceUnproxied → 진짜 Hikari
//dataSource → Proxy로 감싼 것 (JPA나 기타 컴포넌트는 여길 씀)
@Configuration
public class DataSourceProxyConfig {

    @Bean
    @Primary
    @DependsOn("dataSourceUnproxied") // 원래 DataSource보다 나중에 초기화되도록
    public DataSource dataSource(DataSource dataSourceUnproxied) {
        // Proxy wrapping
        ProxyDataSource proxyDataSource = new ProxyDataSourceBuilder()
                .dataSource(dataSourceUnproxied)
                .listener(new SlowQueryExplainListener(0, dataSourceUnproxied))
                .name("DS-Proxy")
                .logQueryBySlf4j()
                .build();
        return proxyDataSource;
    }

    @Bean
    public DataSource dataSourceUnproxied(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password
    ) {
        HikariDataSource hikari = new HikariDataSource();
        hikari.setJdbcUrl(url);
        hikari.setUsername(username);
        hikari.setPassword(password);
        return hikari;
    }
}
