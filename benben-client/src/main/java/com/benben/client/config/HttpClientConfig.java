package com.benben.client.config;

import com.benben.global.constants.BenbenConstants;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class HttpClientConfig {

    @Bean
    public CloseableHttpClient initHttpClient() {
        // SSL context for secure connections can be created either based on system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Use custom credentials provider if necessary.
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        // Create a registry of custom connection socket factories for supported protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.INSTANCE)
                .register(BenbenConstants.PROTOCAL_HTTPS, new SSLConnectionSocketFactory(sslcontext)).build();

        // Create a connection manager with custom configuration.
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);

        // Configure total max
        connManager.setMaxTotal(BenbenConstants.HTTP_MAX_TOTAL);

        // Per route limits for persistent connections
        connManager.setDefaultMaxPerRoute(BenbenConstants.HTTP_MAX_PER_ROUTE);

        // Request Config
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(BenbenConstants.HTTP_SOCKET_TIMEOUT)
                .setConnectTimeout(BenbenConstants.HTTP_CONNECT_TIMEOUT).build();

        // Create an HttpClient with the given custom dependencies and configuration.
        return HttpClients.custom().setConnectionManager(connManager).setConnectionManagerShared(true)
                .setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(credentialsProvider).build();
    }
}
