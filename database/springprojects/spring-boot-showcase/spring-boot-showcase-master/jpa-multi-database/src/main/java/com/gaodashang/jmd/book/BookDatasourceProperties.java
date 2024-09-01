package com.gaodashang.jmd.book;

/**
 * comments.
 *
 * @author eva
 */
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource.book")
public class BookDatasourceProperties {

    private String url;

    private String username;

    private String password;

}
