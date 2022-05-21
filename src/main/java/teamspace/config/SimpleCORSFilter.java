package teamspace.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

    public SimpleCORSFilter() {
        logger.error("XXXXXXX-SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String ipAddress = "" ;


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        logger.error(String.format("%s request || to %s  ,headers %s", request.getMethod(), request.getRequestURL().toString(), request.getHeaderNames().toString()));
        logger.error("XXXXXXX-SimpleCORSFilter init" + request.getRequestURL().toString());
        if(request.getMethod().equals("GET") && request.getRequestURL().toString().contains("auth/api/v1/widget/detail") ) {


            logger.error("HHHHHHHHH-SimpleCORSFilter init" + request.getRequestURL().toString()+ "?integrity=" +request.getParameter("integrity"));
        }else{

        }

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD, PATCH, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        ipAddress =  request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }




        String finalIpAddress = ipAddress;
        new Thread(() -> {
            if( !finalIpAddress.equals("0:0:0:0:0:0:0:1") ||
                    !finalIpAddress.startsWith("172.31."  /*ignoring internal Requests*/)){


            }
        }).start();
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
    }
