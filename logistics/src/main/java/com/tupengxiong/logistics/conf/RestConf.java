package com.tupengxiong.logistics.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class RestConf {

    @Value("${rest.readTimeout}")
    private int readTimeout;

    @Value("${rest.connectTimeout}")
    private int connectionTimeout;

    @Value("${proxy.enabled}")
    private boolean enabled;

    @Value("${proxy.host}")
    private String host;

    @Value("${proxy.port}")
    private int port;

    @Bean
    public SimpleClientHttpRequestFactory httpClientFactory() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(readTimeout);
        httpRequestFactory.setConnectTimeout(connectionTimeout);

        if (enabled) {
            SocketAddress address = new InetSocketAddress(host, port);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            httpRequestFactory.setProxy(proxy);
        }
        return httpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpClientFactory) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(httpClientFactory);
        return restTemplate;
    }
}