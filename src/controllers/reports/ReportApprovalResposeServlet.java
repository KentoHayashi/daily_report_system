package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportApprovalResposeServlet
 */
@WebServlet("/reports/approval_response")
public class ReportApprovalResposeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportApprovalResposeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));

            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            r.setApproval(Integer.parseInt(request.getParameter("report.approval")));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "承認状況を送信しました。");
            request.getSession().removeAttribute("report_id");
            response.sendRedirect(request.getContextPath() + "/reports/index");
         }
    }

}
