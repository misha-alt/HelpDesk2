package misha.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

public class DispatcerServlet extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]
                {SpringConfig.class, HibernateConfig.class};
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
        //registerCustomFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null,true,"/*");
        /*new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");*/
    }


}










/*@WebFilter(urlPatterns = { "*.html" })
    public class CharacterSetFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }
    }*/