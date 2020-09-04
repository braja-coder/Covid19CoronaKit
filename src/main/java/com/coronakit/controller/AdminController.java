package com.coronakit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coronakit.dao.ProductDao;
import com.coronakit.model.Product;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productMasterDao;
	HttpSession session;

	public void setProductMasterDao(ProductDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {

		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.productMasterDao = new ProductDao(jdbcURL, jdbcUsername, jdbcPassword);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "login":
				viewName = adminLogin(request, response);
				break;
			case "insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "updateproduct":
				viewName = updateProduct(request, response);
				break;
			case "list":
				viewName = listAllProducts(request, response);
				break;
			case "logout":
				viewName = adminLogout(request, response);
				break;
			default:
				viewName = "notfound.jsp";
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		if (viewName == null || viewName.equals("")) {
			response.sendError(response.SC_NOT_FOUND);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher(viewName);
			dispatch.forward(request, response);
		}
	}

	private String adminLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return "index.jsp";
	}

	private String listAllProducts(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		List<Product> products = this.productMasterDao.getAllproductRecords();
		request.setAttribute("products", products);
		return "listproducts.jsp";
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		String pid = request.getParameter("pid");
		String pname = request.getParameter("pName");
		String pcost = request.getParameter("pCost");
		String pdescription = request.getParameter("pDescription");

		this.productMasterDao.editProduct(pid, pname, pcost, pdescription);
		return "admin?action=list";
	}

	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		String id = request.getParameter("id");
		request.setAttribute("product", this.productMasterDao.getproductRecord(id));
		return "editproduct.jsp";
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		String id = request.getParameter("id");
		this.productMasterDao.deleteProduct(id);
		return "admin?action=list";
	}

	private String insertProduct(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		String pname = request.getParameter("pName");
		String pcost = request.getParameter("pCost");
		String pdescription = request.getParameter("pDescription");
		this.productMasterDao.addProduct(pname, pcost, pdescription);

		return "admin?action=list";
	}

	private String adminLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("loginid");
		String password = request.getParameter("password");

		if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {

			return "admin?action=list";
		} else {
			request.setAttribute("msg", "Invalid Crediantials");
			request.setAttribute("username", username);
			return "index.jsp";
		}
	}

}