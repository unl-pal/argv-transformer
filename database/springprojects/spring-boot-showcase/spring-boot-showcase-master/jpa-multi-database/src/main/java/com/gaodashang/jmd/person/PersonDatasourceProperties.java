package com.gaodashang.jmd.person;

/**
 * comments.
 *
 * @author eva
 */
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource.person")
public class PersonDatasourceProperties {

    private String url;

    private String username;

    private String password;

}
