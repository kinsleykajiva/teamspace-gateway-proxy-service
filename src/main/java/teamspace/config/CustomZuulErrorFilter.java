package teamspace.config;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;


public class CustomZuulErrorFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
