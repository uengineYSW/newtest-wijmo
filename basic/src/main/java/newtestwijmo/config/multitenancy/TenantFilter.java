package newtestwijmo.config.multitenancy;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import newtestwijmo.security.AuthenticationService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
class TenantFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        String tenant = AuthenticationService.getTenant(
            (HttpServletRequest) request
        );
        TenantContext.setCurrentTenant(tenant);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
