package hooyn.routing_datasource.config;

import hooyn.routing_datasource.routingconfig.DatabaseContextHolder;
import hooyn.routing_datasource.routingconfig.DatabaseEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String company = request.getHeader("company");
        if (DatabaseEnum.COMPANY01.toString().equalsIgnoreCase(company)) {
            DatabaseContextHolder.setDatabaseContext(DatabaseEnum.COMPANY01);
        } else {
            DatabaseContextHolder.setDatabaseContext(DatabaseEnum.COMPANY02);
        }
        return true;
    }
}