package NikKha03.TaskService.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class OriginFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(OriginFilter.class);

    @Value("#{'${app-env.allowed-origins}'.split(',')}")
    private List<String> ALLOWED_ORIGIN;

    @Value("#{'${app-env.gateway-secret}'}")
    private String GATEWAY_SECRET;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String origin = httpRequest.getScheme() + "://" + httpRequest.getHeader("Host");
        String gatewaySecret = httpRequest.getHeader("gateway-secret");

//        if (ALLOWED_ORIGIN.contains(origin) && GATEWAY_SECRET.equals(gatewaySecret)) {
//            chain.doFilter(request, response); // Пропускаем запрос
//        } else {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.sendError(403);
//            logger.warn("Запрос с неопознанного источника. Origin: " + origin + ", has gateway-secret: " + !Objects.isNull(gatewaySecret));
//        }

        // На время разработки
        chain.doFilter(request, response); // Пропускаем запрос

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
