package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import DAO.CategoryDao;
import DAO.ProductDao;
import DAO.UserDao;
import Utils.EncryptUtil;
import Utils.cookieUtils;
import entities.Category;
import entities.Product;
import entities.User;

/**
 * Servlet implementation class homeServlet
 */
@WebServlet({ "/home", "/login", "/registry", "/forgetPassword", "/giayDep", "/phuKien", "/quanAo", "/loginIndex",
		"/registryIndex", "/forgetPasswordIndex", "/loadmoreQuanAo", "/loadmorePhuKien", "/loadmoreGiayDep" })
public class guestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private ProductDao productDao;
	private CategoryDao categoryDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public guestServlet() {
		super();
		userDao = new UserDao();
		productDao = new ProductDao();
		categoryDao = new CategoryDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("home")) {
			home(request, response);
		} else if (uri.contains("quanAo")) {
			request.setAttribute("view", "/views/user/quanAo.jsp");
			int tag = Integer.parseInt(request.getParameter("tag"));
			request.setAttribute("tag", tag);
			request.setAttribute("list", getProduct(request, response, 1));
			request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
		} else if (uri.contains("giayDep")) {
			request.setAttribute("view", "/views/user/giayDep.jsp");
			int tag = Integer.parseInt(request.getParameter("tag"));
			request.setAttribute("tag", tag);
			request.setAttribute("list", getProduct(request, response, 2));
			request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
		} else if (uri.contains("phuKien")) {
			request.setAttribute("view", "/views/user/phuKien.jsp");
			int tag = Integer.parseInt(request.getParameter("tag"));
			request.setAttribute("tag", tag);
			request.setAttribute("list", getProduct(request, response, 3));
			request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
		} else if (uri.contains("loginIndex")) {
			loginIndex(request, response);
		} else if (uri.contains("registryIndex")) {
			registryIndex(request, response);
		} else if (uri.contains("forgetPasswordIndex")) {
			forgetPasswordIndex(request, response);
		} else if (uri.contains("loadmoreQuanAo")) {
			LoadMoreQuanAo(request, response);
		} else if (uri.contains("loadmorePhuKien")) {
			LoadMorePhuKien(request, response);
		} else if (uri.contains("loadmoreGiayDep")) {
			LoadMoreGiayDep(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		if (uri.contains("login")) {
			login(request, response);
		} else if (uri.contains("registry")) {
			registry(request, response);
		} else if (uri.contains("forgetPassword")) {
			forgetPasswordIndex(request, response);
		}
	}

	private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Category category = categoryDao.findById(1);
		Category category1 = categoryDao.findById(2);
		Category category2 = categoryDao.findById(3);
		List<Product> listQuanAo = productDao.next4(0, category);
		List<Product> listGiayDep = productDao.next4(0, category1);
		List<Product> listPhuKien = productDao.next4(0, category2);
		request.setAttribute("view", "/views/guest/home.jsp");
		request.setAttribute("listQuanAo", listQuanAo);
		request.setAttribute("listGiayDep", listGiayDep);
		request.setAttribute("listPhuKien", listPhuKien);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private List<Product> getProduct(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Category category = categoryDao.findById(id);
		int tag = Integer.parseInt(request.getParameter("tag"));
		List<Product> list = new ArrayList<Product>();
		if (tag == 0) {
			list = productDao.All(category);
		} else {
			list = productDao.All(tag, category);
		}
		return list;
	}

	private void loginIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("view", "/views/guest/login.jsp");
		String user = cookieUtils.read("email", request);
		String pass = cookieUtils.read("pass", request);
		String save = cookieUtils.read("save", request);
		request.setAttribute("email", user);
		request.setAttribute("pass", pass);
		request.setAttribute("save", save.equals("save") ? true : false);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void registryIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("view", "/views/guest/registry.jsp");
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void forgetPasswordIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("view", "/views/guest/forgetPassword.jsp");
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String email=request.getParameter("email");
		String pass=request.getParameter("pass");
		String save=request.getParameter("save");
		User user=userDao.findByEmail(email);
		int hour= save==null?0:30*24;
		if(user==null) {
			request.getSession().setAttribute("error", "Email sai");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/loginIndex");
		}else {
			if(EncryptUtil.check(pass, user.getPassword())) {
				if(user.getStatus()==1) {
					request.getSession().setAttribute("error", "Tài khoản đã bị vô hiệu hoá");
					response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/loginIndex");
				}else {
					request.getSession().setAttribute("user", user);
					request.getSession().setAttribute("role", user.getRole());
					request.setAttribute("role", user.getRole());
					cookieUtils.add("email", email, hour, response);
					cookieUtils.add("pass", pass, hour, response);
					cookieUtils.add("save", "save", hour, response);
					response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/home");
				}
			}else {
				request.getSession().setAttribute("error", "Password sai");
				response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/loginIndex");
			}
		}
	}

	private void registry(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			user.setStatus((byte) 0);
			user.setRole((byte) 0);
			user.setPassword(EncryptUtil.encrypt(user.getPassword()));
			userDao.create(user);
			request.getSession().setAttribute("message", "Đăng ký thành công. Hãy đăng nhập");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/loginIndex");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Đăng ký thất bại");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/registryIndex");
		}
	}

	private void LoadMoreQuanAo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		int amount = Integer.parseInt(request.getParameter("exits"));
		int page = amount;
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		Category category = categoryDao.findById(1);
		List<Product> list = productDao.next4(page, category);
		PrintWriter out = response.getWriter();
		for (Product product : list) {
			String tag = product.getTag() == 1 ? (product.getTag() == 2 ? "New" : "Hot") : "Sale";
			out.println("<div class=\"quanAo col-md-6 col-lg-4 col-xl-3 mt-4 p-2\">\r\n"
					+ "					<div class=\"\">\r\n"
					+ "						<div class=\"img position-relative\">\r\n"
					+ "							<a href=\"/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id="
					+ product.getId() + "\"><img\r\n"
					+ "								src=\"/PH17307_TrinhTienLuc_Asignment_SOF3011/imgUpload/"
					+ product.getImage() + "\"\r\n"
					+ "								class=\"w-100 productImg\"></a> <span\r\n"
					+ "								class=\"position-absolute text-white d-flex align-items-center justify-content-center\">\r\n"
					+ "								" + tag + "							</span>\r\n"
					+ "						</div>\r\n" + "						<div class=\"text-center\">\r\n"
					+ "							<div class=\"rating mt-3\">\r\n"
					+ "								<span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span>\r\n" + "							</div>\r\n"
					+ "							<p class=\"text-capitalize my-1\">" + product.getName() + "</p>\r\n"
					+ "							<p class=\"fw-bold\">\r\n"
					+ "								<fmt:formatNumber pattern=\"###,###,###\">"
					+ currencyVN.format(product.getPrice()) + "</fmt:formatNumber>\r\n"
					+ "								\r\n" + "							</p>\r\n"
					+ "						</div>\r\n" + "					</div>\r\n" + "				</div>");
		}
	}

	private void LoadMoreGiayDep(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		int amount = Integer.parseInt(request.getParameter("exits"));
		int page = amount;
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		Category category = categoryDao.findById(2);
		List<Product> list = productDao.next4(page, category);
		PrintWriter out = response.getWriter();
		for (Product product : list) {
			String tag = product.getTag() == 1 ? (product.getTag() == 2 ? "New" : "Hot") : "Sale";
			out.println("<div class=\"giayDep col-md-6 col-lg-4 col-xl-3 mt-4 p-2\">\r\n"
					+ "					<div class=\"\">\r\n"
					+ "						<div class=\"img position-relative\">\r\n"
					+ "							<a href=\"/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id="
					+ product.getId() + "\"><img\r\n"
					+ "								src=\"/PH17307_TrinhTienLuc_Asignment_SOF3011/imgUpload/"
					+ product.getImage() + "\"\r\n"
					+ "								class=\"w-100 productImg\"></a> <span\r\n"
					+ "								class=\"position-absolute text-white d-flex align-items-center justify-content-center\">\r\n"
					+ "								" + tag + "							</span>\r\n"
					+ "						</div>\r\n" + "						<div class=\"text-center\">\r\n"
					+ "							<div class=\"rating mt-3\">\r\n"
					+ "								<span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span>\r\n" + "							</div>\r\n"
					+ "							<p class=\"text-capitalize my-1\">" + product.getName() + "</p>\r\n"
					+ "							<p class=\"fw-bold\">\r\n"
					+ "								<fmt:formatNumber pattern=\"###,###,###\">"
					+ currencyVN.format(product.getPrice()) + "</fmt:formatNumber>\r\n"
					+ "								\r\n" + "							</p>\r\n"
					+ "						</div>\r\n" + "					</div>\r\n" + "				</div>");
		}
	}

	private void LoadMorePhuKien(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		int amount = Integer.parseInt(request.getParameter("exits"));
		int page = amount;
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		Category category = categoryDao.findById(3);
		List<Product> list = productDao.next4(page, category);
		PrintWriter out = response.getWriter();
		for (Product product : list) {
			String tag = product.getTag() == 1 ? (product.getTag() == 2 ? "New" : "Hot") : "Sale";
			out.println("<div class=\"phuKien col-md-6 col-lg-4 col-xl-3 mt-4 p-2\">\r\n"
					+ "					<div class=\"\">\r\n"
					+ "						<div class=\"img position-relative\">\r\n"
					+ "							<a href=\"/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id="
					+ product.getId() + "\"><img\r\n"
					+ "								src=\"/PH17307_TrinhTienLuc_Asignment_SOF3011/imgUpload/"
					+ product.getImage() + "\"\r\n"
					+ "								class=\"w-100 productImg\"></a> <span\r\n"
					+ "								class=\"position-absolute text-white d-flex align-items-center justify-content-center\">\r\n"
					+ "								" + tag + "							</span>\r\n"
					+ "						</div>\r\n" + "						<div class=\"text-center\">\r\n"
					+ "							<div class=\"rating mt-3\">\r\n"
					+ "								<span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span> <span class=\"text-warning\"> <i class=\"fas fa-star\"></i>\r\n"
					+ "								</span>\r\n" + "							</div>\r\n"
					+ "							<p class=\"text-capitalize my-1\">" + product.getName() + "</p>\r\n"
					+ "							<p class=\"fw-bold\">\r\n"
					+ "								<fmt:formatNumber pattern=\"###,###,###\">"
					+ currencyVN.format(product.getPrice()) + "</fmt:formatNumber>\r\n"
					+ "								\r\n" + "							</p>\r\n"
					+ "						</div>\r\n" + "					</div>\r\n" + "				</div>");
		}
	}
}
