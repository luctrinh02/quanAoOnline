package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import DAO.UserDao;
import Utils.EncryptUtil;
import entities.User;

/**
 * Servlet implementation class adminProductsServlet
 */
@WebServlet({
	"/admin/users/index",
	"/admin/users/create",
	"/admin/users/store",
	"/admin/users/edit",
	"/admin/users/update",
	"/admin/users/delete",
	"/admin/users/restore"
})
public class adminUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UserDao userDao;
    private static Logger logger=Logger.getLogger(adminUsersServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
        userDao=new UserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		List<User> list=userDao.getAll();
		request.setAttribute("view", "/views/admin/users/index.jsp");
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void create(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("view", "/views/admin/users/create.jsp");
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		User user=userDao.findById(id);
		request.setAttribute("view", "/views/admin/users/edit.jsp");
		request.setAttribute("user", user);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void delete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		User user=userDao.findById(id);
		try {
			user.setStatus((byte) 1);
			logger.info("Vô hiệu user "+user.toString());
			userDao.update(user);
			request.getSession().setAttribute("message", "Đã vô hiệu user "+user.getId()+" "+user.getFullname());
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Vô hiệu thất bại");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		}
	}
	private void store(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			User user=new User();
			BeanUtils.populate(user, request.getParameterMap());
			user.setStatus((byte) 0);
			user.setPassword(EncryptUtil.encrypt(user.getPassword()));
			logger.info("Thêm mới user "+user.toString());
			userDao.create(user);
			request.getSession().setAttribute("message", "Thêm thành công");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Thêm thất bại");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/create");
		}
	}
	private void update(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			int id=Integer.parseInt(request.getParameter("id"));
			User user=userDao.findById(id);
			BeanUtils.populate(user, request.getParameterMap());
			logger.info("Chỉnh sửa user "+user.toString());
			userDao.update(user);
			request.getSession().setAttribute("message", "Sửa thành công");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Sửa thất bại");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/update");
		}
	}
	private void restore(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		User user=userDao.findById(id);
		try {
			user.setStatus((byte) 0);
			logger.info("Khôi phục user "+user.toString());
			userDao.update(user);
			request.getSession().setAttribute("message", "Đã khôi phục "+user.getId()+" "+user.getFullname());
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Khôi phục thất bại");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		}
	}

}
