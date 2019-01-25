package com.leadlet.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class ElasticSearchConfig extends AbstractFactoryBean {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;
    @Value("${spring.data.elasticsearch.cluster-host}")
    private String clusterHost;
    @Value("${spring.data.elasticsearch.cluster-port}")
    private Integer clusterPort;
    @Value("${spring.data.elasticsearch.protocol}")
    private String protocol;
    private RestHighLevelClient restHighLevelClient;
    @Value("${spring.data.elasticsearch.username:''}")
    private String username;
    @Value("${spring.data.elasticsearch.password:''}")
    private String password;

    @Override
    public void destroy() {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            LOG.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public RestHighLevelClient createInstance() {
        return buildClient();
    }

    private RestHighLevelClient buildClient() {
        try {
            RestClientBuilder builder = RestClient.builder(new HttpHost(clusterHost, clusterPort, protocol))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
                        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                        credentialsProvider.setCredentials(AuthScope.ANY,
                            new UsernamePasswordCredentials(username, password));
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        return httpClientBuilder;
                    } else {
                        return httpClientBuilder;
                    }
                });

            restHighLevelClient = new RestHighLevelClient(builder);

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return restHighLevelClient;
    }

}
