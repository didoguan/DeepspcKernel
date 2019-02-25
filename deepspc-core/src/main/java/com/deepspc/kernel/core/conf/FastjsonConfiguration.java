package com.deepspc.kernel.core.conf;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration("defaultFastjsonConfig")
@ConditionalOnClass(com.alibaba.fastjson.JSON.class)
@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
@ConditionalOnWebApplication
public class FastjsonConfiguration {

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(getSupportedMediaType());
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return converter;
    }

    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        initOtherValueFilters(fastJsonConfig);
        return fastJsonConfig;
    }

    /**
     * 支持的mediaType类型
     */
    public List<MediaType> getSupportedMediaType() {
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //增加解析spring boot actuator结果的解析
        mediaTypes.add(MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json"));
        return mediaTypes;
    }

    /**
     * 初始化value过滤器
     * <p>
     * 默认的valueFilter是把空的字段转化为空串
     */
    protected void initOtherValueFilters(FastJsonConfig fastJsonConfig) {
        ValueFilter valueFilter = (object, name, value) -> {
            if (null == value) {
                return "";
            } else {
                return value;
            }
        };
        fastJsonConfig.setSerializeFilters(valueFilter);
    }

}
