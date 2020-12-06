<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                       <h2>日報　詳細ページ</h2>

                    <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>承認状況</th>
                            <td>
                                <c:choose>
                                    <c:when test="${report.approval == 0}">未承認</c:when>
                                    <c:when test="${report.approval == 1}">承認待ち</c:when>
                                    <c:when test="${report.approval == 2}">承認済</c:when>
                                </c:choose>
                            </td>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value='/reports/edit?id=${report.id}' />">この日報を編集する</a></p>
                </c:if>
                <c:if test="${sessionScope.login_employee.admin_flag == 0}">
                    <p><a href="#" onclick="confirmApproval();">承認申請</a></p>

                <script>
                    function confirmApproval() {
                        if(confirm("承認申請しますか")) {
                            document.forms[0].submit();
                        }
                    }
                </script>
                </c:if>
                    <form method="POST" action="<c:url value='/reports/approval' />">
                        <input type="hidden" name="_token" value="${_token}" />
                    </form>

                <c:if test="${sessionScope.login_employee.admin_flag == 1 && report.approval == 1}">
                    <form method="POST" action="<c:url value='/reports/approval_response' />">
                        <select name="report.approval">
                            <option value="0"<c:if test="${report.approval == 0}">selected</c:if>>否決</option>
                            <option value="2"<c:if test="${report.approval == 2}">selected</c:if>>承認</option>
                        </select>
                        <input type="hidden" name="_token" value="${_token}" />
                    </form>
                    <p><a href="#" onclick="confirmApproval_response();">送信</a></p>

                <script>
                    function confirmApproval_response() {
                        if(confirm("承認状況を更新しますか")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
                </c:if>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>