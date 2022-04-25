package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDao;
import entities.Product;
import entities.User;

/**
 * Servlet Filter implementation class RoleProductFilter
 */
@WebFilter(
		urlPatterns = {"/admin/products/edit","/admin/products/update"},
		filterName = "roleProductFilter"
		)
public class RoleProductFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RoleProductFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		HttpServletResponse httpServletResponse=(HttpServletResponse) response;
		User u=(User) httpServletRequest.getSession().getAttribute("user");
		String idString=request.getParameter("id");
		if(idString==null) {
			httpServletResponse.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/home");
			return;
		}
		int id=Integer.parseInt(idString);
		Product product=new ProductDao().findById(id);
		if(u.getId()!=product.getUser().getId()) {
			httpServletRequest.getSession().setAttribute("error", "Bạn không có quyền sửa sản phẩm này");
			httpServletResponse.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/index");
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
