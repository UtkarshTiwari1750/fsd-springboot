package payment_gateway.demo.Config;


import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve Angular static files
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        try {
                            // Normalize resourcePath: remove leading slash if present
                            if (resourcePath != null && resourcePath.startsWith("/")) {
                                resourcePath = resourcePath.substring(1);
                            }

                            Resource requestedResource = location.createRelative(resourcePath);

                            // If the requested resource exists, serve it
                            if (requestedResource.exists() && requestedResource.isReadable()) {
                                return requestedResource;
                            }

                            // For Angular routing - serve index.html for any non-API routes
                            if (resourcePath == null || !resourcePath.startsWith("api/")) {
                                return new ClassPathResource("static/index.html");
                            }

                            return null;
                        } catch (Exception ex) {
                            // Avoid bubbling up exceptions (which become 500s). If not an API path,
                            // fall back to index.html (SPA behavior); otherwise return null.
                            if (resourcePath == null || !resourcePath.startsWith("api/")) {
                                return new ClassPathResource("static/index.html");
                            }
                            return null;
                        }
                    }
                });
    }
}