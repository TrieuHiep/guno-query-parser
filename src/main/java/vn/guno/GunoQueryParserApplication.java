package vn.guno;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import vn.guno.global.ConfigInfo;

import java.util.Properties;

@SpringBootApplication
public class GunoQueryParserApplication {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("server.port", ConfigInfo.SERVICE_PORT);
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder()
                .sources(GunoQueryParserApplication.class)
                .properties(prop);
        applicationBuilder.run(args);
    }
}
