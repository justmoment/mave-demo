package filter;

import lombok.extern.slf4j.Slf4j;
import org.user.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//        HttpServletRequest request=(HttpServletRequest)servletRequest;
//        HttpServletResponse response=(HttpServletResponse)servletResponse;
//        SysUser user = (SysUser)servletRequest.getAttribute("user");
//        if(user==null){
//            String path="/login.jsp";
//            response.sendRedirect(path);
//            return ;
//
//        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
