package my.custom.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCustomFilter implements Filter {
    
    private static final Logger LOG = LoggerFactory.getLogger(MyCustomFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.info("Executing filter logic");
        chain.doFilter(request, response);
    }

}
