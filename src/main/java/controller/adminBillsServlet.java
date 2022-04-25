package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import DAO.BillDao;
import DAO.DetailBillDao;
import DAO.ProductDao;
import DAO.UserDao;
import entities.Bill;
import entities.Detailbill;
import entities.Product;
import entities.User;

/**
 * Servlet implementation class adminBillsServlet
 */
@WebServlet({ "/admin/bills/history", "/admin/bills/cancel", "/admin/bills/success", "/backToHistory", "/backToUser",
		"/backToIndex", "/admin/bills/detail", "/admin/bills/index", "/admin/bills/more", "/admin/bills/CancelIndex",
		"/admin/bills/SuccessIndex" })
public class adminBillsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BillDao billDao;
	private UserDao userDao;
	private User user;
	private ProductDao productDao;
	DetailBillDao detailBillDao;
	private static Logger logger=Logger.getLogger(adminBillsServlet.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public adminBillsServlet() {
		super();
		billDao = new BillDao();
		userDao = new UserDao();
		productDao=new ProductDao();
		detailBillDao=new DetailBillDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("history")) {
			history(request, response);
		} else if (uri.contains("detail")) {
			detail(request, response);
		} else if (uri.contains("backToUser")) {
			user = null;
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index");
		} else if (uri.contains("cancel")) {
			changeStatus(request, response, 2, "Huỷ đơn");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/history?id=" + user.getId());
		} else if (uri.contains("success")) {
			Bill bill=changeStatus(request, response, 1, "Nhận đơn");
			updateProduct(request, response, bill);
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/history?id=" + user.getId());
		} else if (uri.contains("backToHistory")) {
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/history?id=" + user.getId());
		} else if (uri.contains("index")) {
			index(request, response);
		} else if (uri.contains("CancelIndex")) {
			changeStatus(request, response, 2, "Huỷ đơn");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/index");
		} else if (uri.contains("SuccessIndex")) {
			Bill bill=changeStatus(request, response, 1, "Nhận đơn");
			updateProduct(request, response, bill);
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/index");
		} else if (uri.contains("backToIndex")) {
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/index");
		} else if (uri.contains("more")) {
			more(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("admin")) {
			System.out.println(uri);
		}
	}

	private void history(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		user = userDao.findById(id);
		List<Bill> list = billDao.getAll(user);
		request.setAttribute("view", "/views/admin/bills/history.jsp");
		request.setAttribute("list", list);
		request.setAttribute("fullname", user.getFullname());
		request.setAttribute("isIndex", 0);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private Bill changeStatus(HttpServletRequest request, HttpServletResponse response, int status, String mess)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("idBill"));
		Bill bill = billDao.findById(id);
		bill.setStatus((byte) status);
		logger.info("Thay đổi trạng thái bill"+bill.toString());
		try {
			billDao.update(bill);
			request.getSession().setAttribute("message", mess + " thành công");
		} catch (Exception e) {
			request.getSession().setAttribute("error", mess + " thất bại");
		}
		return bill;
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Bill bill = billDao.findById(id);
		List<Detailbill> list = bill.getDetailbills();
		request.setAttribute("view", "/views/admin/bills/detail.jsp");
		request.setAttribute("list", list);
		request.setAttribute("trangThai", bill.getStatus());
		request.setAttribute("date", bill.getDate());
		request.setAttribute("isIndex", 0);
		request.setAttribute("tongTien", bill.getTotalMoney());
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void more(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Bill bill = billDao.findById(id);
		List<Detailbill> list = detailBillDao.getAll(bill);
		request.setAttribute("view", "/views/admin/bills/detail.jsp");
		request.setAttribute("list", list);
		request.setAttribute("trangThai", bill.getStatus());
		request.setAttribute("date", bill.getDate());
		request.setAttribute("isIndex", 1);
		request.setAttribute("tongTien", bill.getTotalMoney());
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Bill> list = billDao.getAll();
		request.setAttribute("view", "/views/admin/bills/index.jsp");
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
	private void updateProduct(HttpServletRequest request, HttpServletResponse response,Bill bill) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Detailbill> detailbills=bill.getDetailbills();
		for(Detailbill x:detailbills) {
			Product p=x.getProduct();
			p.setAmount(p.getAmount()-x.getAmount());
			try {
				productDao.update(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
