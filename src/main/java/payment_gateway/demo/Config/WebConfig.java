package payment_gateway.demo.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve everything from classpath:/static/ (works with WAR context path)
        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
                @Override
                protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    try {
                        if (resourcePath == null) {
                            return null;
                        }
                        // normalize leading slash which can break createRelative in some containers
                        String normalized = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
                        Resource requested = location.createRelative(normalized);
                        if (requested.exists() && requested.isReadable()) {
                            return requested;
                        }
                        // fallback: do not throw — return index.html for SPA routes or null to let 404 happen
                        Resource index = new ClassPathResource("static/index.html");
                        if (index.exists() && index.isReadable()) {
                            // serve index for routes (uncomment if you want SPA fallback)
                            // return index;
                            return null; // prefer 404 for missing static files unless SPA fallback is desired
                        }
                        return null;
                    } catch (Exception ex) {
                        // never rethrow: log and return null so servlet container returns 404, not 500
                        log.error("Failed to resolve static resource path [{}] under [{}]: {}", resourcePath, location, ex.toString());
                        return null;
                    }
                }
            });
    }
}