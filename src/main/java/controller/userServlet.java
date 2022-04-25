package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import DAO.BillDao;
import DAO.CartDao;
import DAO.CommentDao;
import DAO.DetailBillDao;
import DAO.ProductDao;
import entities.Bill;
import entities.Cart;
import entities.CartPK;
import entities.Comment;
import entities.CommentPK;
import entities.Detailbill;
import entities.Product;
import entities.User;

/**
 * Servlet implementation class userServlet
 */
@WebServlet({ "/logout", "/user/changePasswordIndex", "/user/changePassword", "/user/profileIndex", "/user/profile",
		"/product", "/user/cart", "/user/comment", "/user/cartIndex", "/user/datHang", "/user/history", "/user/bill",
		"/user/cancel", "/back" })
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productDao;
	private CommentDao commentDao;
	private CartDao cartDao;
	private BillDao billDao;
	private DetailBillDao detailBillDao;
	private static Logger logger = Logger.getLogger(userServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public userServlet() {
		super();
		productDao = new ProductDao();
		commentDao = new CommentDao();
		cartDao = new CartDao();
		billDao = new BillDao();
		detailBillDao = new DetailBillDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		if (uri.contains("logout")) {
			logout(request, response);
		} else if (uri.contains("changePasswordIndex")) {

		} else if (uri.contains("profileIndex")) {

		} else if (uri.contains("product")) {
			product(request, response);
		} else if (uri.contains("cartIndex")) {
			cartIndex(request, response);
		} else if (uri.contains("history")) {
			history(request, response);
		} else if (uri.contains("cancel")) {
			cancel(request, response);
		} else if (uri.contains("bill")) {
			bill(request, response);
		} else if (uri.contains("back")) {
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/history");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("changePassword")) {
			logout(request, response);
		} else if (uri.contains("profile")) {

		} else if (uri.contains("cart")) {
			cart(request, response);
		} else if (uri.contains("comment")) {
			comment(request, response);
		} else if (uri.contains("datHang")) {
			try {
				datHang(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		System.out.println((User) request.getSession().getAttribute("user"));
		response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/home");
	}

	private void product(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDao.findById(id);
		List<Comment> list = product.getComments();
		request.setAttribute("view", "/views/guest/product.jsp");
		request.setAttribute("listComment", list);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		User user = (User) request.getSession().getAttribute("user");
		Product product = productDao.findById(id);
		CartPK cardPk = new CartPK();
		cardPk.setProductId(id);
		cardPk.setUserId(user.getId());
		Cart cart = new Cart();
		try {
			try {
				cart.setAmount(Integer.parseInt(request.getParameter("amount")));
			} catch (Exception e) {
				request.getSession().setAttribute("error", "Số lượng sản phẩm quá lớn");
				response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
				return;
			}
			cart.setPrice(product.getPrice() * (100 - product.getDiscount()) / 100);
			cart.setId(cardPk);
			cart.setProduct(product);
			cart.setUser(user);
			if (cart.getAmount() > cart.getProduct().getAmount()) {
				request.getSession().setAttribute("error", "Không đủ số lượng sản phẩm");
				response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
				return;
			} else {
				logger.info("Thêm vào giỏ hàng " + cart.toString());
				cartDao.create(cart);
				request.getSession().setAttribute("message", "Thêm vào giỏ thành công");
				response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", "Trong giỏ đã có rồi");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
		}
	}

	private void comment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		User user = (User) request.getSession().getAttribute("user");
		Product product = productDao.findById(id);
		CommentPK commentPK = new CommentPK();
		commentPK.setProductId(id);
		commentPK.setUserId(user.getId());
		Comment comment = new Comment();
		try {
			comment.setId(commentPK);
			try {
				byte rate = Byte.parseByte(request.getParameter("rate"));
				if (rate > 0 && rate <= 5) {
					comment.setRate(rate);
				} else {
					comment.setRate(rate);
					request.getSession().setAttribute("message", "Điểm đánh giá không hợp lệ");
					response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
					return;
				}
			} catch (Exception e) {
				request.getSession().setAttribute("message", "Điểm đánh giá không hợp lệ");
				response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
				return;
			}
			comment.setContent(request.getParameter("content"));
			comment.setProduct(product);
			comment.setUser(user);
			logger.info("Viết comment " + comment.toString());
			commentDao.create(comment);
			product.addComment(comment);
			request.getSession().setAttribute("message", "Đã thêm bình luận");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", "Bạn đã bình luận rồi");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=" + id);
		}
	}

	private void cartIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		List<Cart> list = cartDao.getAll(user);
		request.setAttribute("view", "/views/user/cart.jsp");
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void datHang(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String listId[] = request.getParameterValues("product");
		if (listId != null) {
			Bill bill = createBill(request, response);
			try {
				for (int i = 0; i < listId.length; i++) {
					Product p = productDao.findById(Integer.parseInt(listId[i]));
					Detailbill detailbill = new Detailbill();
					detailbill.setBill(bill);
					detailbill.setProduct(p);
					try {
						detailbill.setAmount(Integer.parseInt(request.getParameter("amount" + listId[i])));
					} catch (Exception e) {
						request.getSession().setAttribute("error", "Số lượng sản phẩm quá lớn");
						response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/cartIndex");
						return;
					}
					detailbill.setPrice(p.getPrice() * (100 - p.getDiscount()) / 100);
					if (detailbill.getAmount() > detailbill.getProduct().getAmount()) {
						request.getSession().setAttribute("error", "Số lượng sản phẩm không đủ");
						response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/cartIndex");
						return;
					} else {
						logger.info("Thêm hoá đơn chi tiết " + detailbill.toString());
						detailBillDao.create(detailbill);
					}
				}
				deleteCart(request, response);
				request.getSession().setAttribute("message", "Đặt hàng thành công");
				response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/cartIndex");
			} catch (Exception e) {
			}
		} else {
			request.getSession().setAttribute("error", "Chưa có sản phẩm được chọn");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/cartIndex");
		}
	}

	private Bill createBill(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		int tongTien = 0;
		User user = (User) request.getSession().getAttribute("user");
		String listId[] = request.getParameterValues("product");
		Bill bill = new Bill();
		for (int i = 0; i < listId.length; i++) {
			Product p = productDao.findById(Integer.parseInt(listId[i]));
			Detailbill detailbill = new Detailbill();
			detailbill.setAmount(Integer.parseInt(request.getParameter("amount" + listId[i])));
			detailbill.setPrice(p.getPrice() * (100 - p.getDiscount()) / 100);
			tongTien = tongTien + detailbill.getPrice() * detailbill.getAmount();
		}
		try {
			bill.setTotalMoney(tongTien);
			bill.setDate(new Date());
			bill.setUser(user);
			bill.setUserId(user.getId());
			System.out.println(user.getId() + "-" + bill.getUserId());
			logger.info("Tạo hoá đơn " + bill.toString());
			bill = billDao.create(bill);
			return bill;
		} catch (Exception e) {
			return null;
		}
	}

	private void deleteCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		String listId[] = request.getParameterValues("product");
		for (int i = 0; i < listId.length; i++) {
			Product p = productDao.findById(Integer.parseInt(listId[i]));
			CartPK cardPk = new CartPK();
			cardPk.setProductId(p.getId());
			cardPk.setUserId(user.getId());
			try {
				Cart cart = cartDao.findById(cardPk);
				cartDao.delete(cart);
				logger.info("Xoá cart " + cart.toString());
				user.removeCart(cart);
				request.getSession().setAttribute("user", user);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	private void history(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		List<Bill> list = billDao.getAll(user);
		request.setAttribute("view", "/views/user/history.jsp");
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}

	private void cancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Bill bill = billDao.findById(id);
		bill.setStatus((byte) 2);
		try {
			billDao.update(bill);
			request.getSession().setAttribute("message", "Huỷ đơn thành công");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/history");
		} catch (Exception e) {
			request.getSession().setAttribute("error", "Huỷ đơn thất bại");
			response.sendRedirect("/PH17307_TrinhTienLuc_Asignment_SOF3011/user/history");
		}
	}

	private void bill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Bill bill = billDao.findById(id);
		List<Detailbill> list = detailBillDao.getAll(bill);
		request.setAttribute("view", "/views/user/detailBill.jsp");
		request.setAttribute("list", list);
		request.setAttribute("trangThai", bill.getStatus());
		request.setAttribute("date", bill.getDate());
		request.setAttribute("tongTien", bill.getTotalMoney());
		request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
	}
}
