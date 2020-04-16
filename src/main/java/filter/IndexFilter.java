package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter ")
public class IndexFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        rep.setHeader("Access-Control-Allow-Origin", "*");
        rep.setHeader("Access-Control-Allow-Credentials", "true");
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        chain.doFilter(req, rep);
    }

    /**
     * Access-Control-Allow-Origin
     * 该字段必填。它的值要么是请求时Origin字段的具体值，要么是一个*，表示接受任意域名的请求。
     * Access-Control-Allow-Methods
     * 该字段必填。它的值是逗号分隔的一个具体的字符串或者*，表明服务器支持的所有跨域请求的方法。注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求。
     * <p>
     * Access-Control-Expose-Headers
     * 该字段可选。CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。
     * <p>
     * Access-Control-Allow-Credentials
     * 该字段可选。它的值是一个布尔值，表示是否允许发送Cookie.默认情况下，不发生Cookie，即：false。对服务器有特殊要求的请求，比如请求方法是PUT或DELETE，或者Content-Type字段的类型是application/json，这个值只能设为true。如果服务器不要浏览器发送Cookie，删除该字段即可。
     * <p>
     * Access-Control-Max-Age
     * 该字段可选，用来指定本次预检请求的有效期，单位为秒。在有效期间，不用发出另一条预检请求。
     */

    @Override
    public void destroy() {

    }
}
