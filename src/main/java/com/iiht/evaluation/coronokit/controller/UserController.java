package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.ProductMaster;
import com.iiht.evaluation.coronokit.model.UserDetails;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;
	List<KitDetail> noOfKits = null;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				insertNewUser(request, response);
				viewName = showAllProducts(request, response);
				break;
			case "showAllProducts":
				viewName = showAllProducts(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;
			default:
				viewName = "notfound.jsp";
				break;
			}
		} catch (Exception ex) {

			throw new ServletException(ex);
		}

		if (viewName == null || viewName.equals("")) {
			response.sendError(response.SC_NOT_FOUND);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher(viewName);
			dispatch.forward(request, response);
		}

	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		HttpSession session = request.getSession();

		String Address = request.getParameter("pAddress");
		UserDetails usrdt = (UserDetails) session.getAttribute("userdetails");
		List<KitDetail> kitDetails = (List<KitDetail>) request.getSession().getAttribute("selectedKitDetails");

		int totalamount = 0;

		for (KitDetail kit : kitDetails) {

			totalamount += kit.getAmount();
		}
		int coronkitID = kitDAO.addCoronakit(usrdt.getUserName(), usrdt.getEmail(), usrdt.getPhone(), totalamount,
				Address, java.time.LocalDate.now().toString(), true);

		for (KitDetail kit : kitDetails) {

			kitDAO.addKitdetails(coronkitID, kit.getProductId(), kit.getQuantity(), kit.getAmount());
		}

		request.setAttribute("ckit", new CoronaKit(0, usrdt.getUserName(), usrdt.getEmail(), usrdt.getPhone(),
				totalamount, Address, java.time.LocalDate.now().toString(), true));
		request.setAttribute("KitDetails", new ArrayList<>(kitDetails));
		request.getSession().invalidate();
		return "ordersummary.jsp";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {

		return "placeorder.jsp";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {

		for (int i = 0; i < Integer.parseInt(request.getParameter("psize")); i++) {

			String id = request.getParameter("pid" + i);
			String quantityStr = request.getParameter("quantity" + i);
			if (quantityStr != null && !quantityStr.trim().equals("")) {

				int quantity = Integer.valueOf(quantityStr.trim());
				if (0 == quantity) {
					continue;
				}

				String costStr = request.getParameter("cost" + i);
				int cost = Integer.parseInt(costStr);
				int productId = Integer.parseInt(id);

				String pname = request.getParameter("pname" + i);

				KitDetail kitDetail = new KitDetail(productId, 0, productId, quantity, cost * quantity, pname);
				List<KitDetail> values = (List<KitDetail>) request.getAttribute("KitDetails");
				if (null == values) {
					values = new ArrayList<>();
				}
				values.add(kitDetail);
				request.setAttribute("KitDetails", values);
				request.getSession().setAttribute("selectedKitDetails", values);
			}

		}
		return "showkit.jsp";
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		List<ProductMaster> products = this.productMasterDao.getAllproductRecords();
		request.setAttribute("products", products);

		try {
			List<KitDetail> kitDetails = (List<KitDetail>) request.getSession().getAttribute("selectedKitDetails");
			if (null != kitDetails) {
				for (ProductMaster prod : products) {
					for (KitDetail kit : kitDetails) {
						if (kit.getId() == prod.getId()) {
							prod.setQuantity(kit.getQuantity());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showproductstoadd.jsp";
	}

	private void insertNewUser(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		String name = request.getParameter("pname");
		String email = request.getParameter("pemail");
		String phone = request.getParameter("pphone");
		this.kitDAO.addNewVisitor(name, email, phone);
		UserDetails usrdetails = new UserDetails(name, email, phone);
		request.getSession().setAttribute("userdetails", usrdetails);

	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {

		return "newuser.jsp";
	}
}