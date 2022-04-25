package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import DAO.CategoryDao;
import DAO.ProductDao;
import entities.Category;
import entities.Product;
import entities.User;

/**
 * Servlet implementation class productsServlet
 */
@MultipartConfig()
@WebServlet({
	"/admin/products/index",
	"/admin/products/create",
	"/admin/products/store",
	"/admin/products/edit",
	"/admin/products/update",
	"/admin/products/delete",
	"/admin/products/restore",
})
public class adminProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ProductDao productDao;
    CategoryDao categoryDao;
    private static Logger logger=Logger.getLogger(adminProductsServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminProductsServlet() {
        super();
        productDao=new ProductDao();
        categoryDao=new CategoryDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri=request.getRequestURI();
		if(uri.contains("index")) {
			index(request, response);
		}else if(uri.contains("create")) {
			create(request, response);
		}else if(uri.contains("edit")) {
			edit(request, response);
		}else if(uri.contains("delete")) {
			delete(request, response);
		}else if(uri.contains("restore")) {
			restore(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri=request.getRequestURI();
		if(uri.contains("store")) {
			store(request, response);
		}else if(uri.contains("update")) {
			update(request, response);
		}
	}
	private void index(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Product> list=productDao.getAll();
		request.setAttribute("view", "/views/admin/products/index.jsp");
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void create(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Category> list=categoryDao.getAll();
		request.setAttribute("view", "/views/admin/products/create.jsp");
		request.setAttribute("listCategory", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Category> list=categoryDao.getAll();
		int id=Integer.parseInt(request.getParameter("id"));
		Product product=productDao.findById(id);
		request.setAttribute("view", "/views/admin/products/edit.jsp");
		request.setAttribute("product", product);
		request.setAttribute("listCategory", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void delete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		Product product=productDao.findById(id);
		try {
			product.setStatus((byte) 1);
			logger.info("Vô hiệu sản phẩm "+product.toString());
			productDao.update(product);
			request.getSession().setAttribute("message", "Đã ngừng kinh doanh "+product.getId()+" "+product.getName());
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Ngừng kinh doanh thất bại");
		}
	}
	private void store(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			int categoryId=Integer.parseInt(request.getParameter("categoryId"));
			Product product=new Product();
			BeanUtils.populate(product, request.getParameterMap());
			User user=(User) request.getSession().getAttribute("user");
			product.setUser(user);
			product.setStatus((byte) 0);
			try {
				product.setImage(upload(request, response,"img"));
			} catch (Exception e) {
			}
			Category category= categoryDao.findById(categoryId);
			product.setCategory(category);
			logger.info("Thêm sản phẩm "+product.toString());
			productDao.create(product);
			request.getSession().setAttribute("message", "Thêm thành công");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Thêm thất bại");
		}
	}
	private void update(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			int categoryId=Integer.parseInt(request.getParameter("categoryId"));
			int id=Integer.parseInt(request.getParameter("id"));
			Product product=productDao.findById(id);
			BeanUtils.populate(product, request.getParameterMap());
			try {
				product.setImage(upload(request, response,"imgEdit"));
			} catch (Exception e) {
			}
			Category category= categoryDao.findById(categoryId);
			product.setCategory(category);
			logger.info("Chỉnh sửa sản phẩm "+product.toString());
			productDao.update(product);
			request.getSession().setAttribute("message", "Sửa thành công");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Sửa thất bại");
		}
	}
	private void restore(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		Product product=productDao.findById(id);
		try {
			product.setStatus((byte) 0);
			logger.info("Khôi phục sản phẩm "+product.toString());
			productDao.update(product);
			request.getSession().setAttribute("message", "Đã khôi phục "+product.getId()+" "+product.getName());
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Khôi phục thất bại");
		}
	}
	public String upload(HttpServletRequest request,HttpServletResponse response,String parameter) throws IOException, ServletException {
		File dir = new File(request.getServletContext().getRealPath("/imgUpload"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Part img = request.getPart(parameter);
		File imgFile = new File(dir, img.getSubmittedFileName());
		img.write(imgFile.getAbsolutePath());
		return imgFile.getName();		
	}
}
