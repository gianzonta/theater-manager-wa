<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Financial | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="clearfix"></div>
                <ul class="nav nav-tabs bar_tabs mb-3" id="myTab" role="tablist">

                    <c:forEach var="year" items="${range}">

                        <li class="nav-item">
                            <a class='nav-link ${year == selectedYear ? "active" : ""}' href="?year=${year}" role="tab">
                                <c:out value="${year}" /></a>
                        </li>

                    </c:forEach>

                </ul>

                <div class="row">
                    <div class="col-md-4 col-lg-2">
                        <div class="animated flipInY">
                            <div class="tile-stats p-2">
                                <div class="count d-flex justify-content-between align-items-center">
                                    <h3 class="ml-0">Year</h3>
                                    <span>
                                        <c:out value="${selectedYear}" /></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12 col-xl-10">
                        <div class="row">
                            <div class="animated flipInY col-md-4">
                                <div class="tile-stats p-2">
                                    <div class="count d-flex justify-content-between align-items-center">
                                        <h3 class="ml-0">Revenues</h3>
                                        <span>&euro;
                                            <c:out value="${revenuesTotal}" /></span>
                                    </div>
                                </div>
                            </div>
                            <div class="animated flipInY col-md-4">
                                <div class="tile-stats p-2">
                                    <div class="count d-flex justify-content-between align-items-center">
                                        <h3 class="ml-0">Expenses</h3>
                                        <span>&euro;
                                            <c:out value="${expensesTotal}" /></span>
                                    </div>
                                </div>
                            </div>
                            <div class="animated flipInY col-md-4">
                                <div class="tile-stats p-2">
                                    <div class="count d-flex justify-content-between align-items-center">
                                        <h3 class="ml-0">Total</h3>
                                        <span>&euro;
                                            <c:out value="${total}" /></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



                <div class="row">
                    <div class="col-sm-12 col-md-6">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Incomings</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a
                                            href="<%= response.encodeURL(request.getContextPath() + "/transactionlist") %>"><i
                                                class="fa fa-list"></i> Show All</a></li>
                                    <li><a
                                            href="<%= response.encodeURL(request.getContextPath() + "/transactionnew") %>"><i
                                                class="fa fa-plus"></i> New Transaction</a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="card-box table-responsive">
                                            <!--table1 palcement-->
                                            <table id="datatable-responsive"
                                                class="table table-striped table-bordered dt-responsive table-hover nowrap"
                                                cellspacing="0" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <!-- from here, link to the transaction details and edit page-->
                                                        <th>Amount</th>
                                                        <th>Date</th>
                                                        <th>Managed By</th>
                                                        <!-- from here, link to the related activity details and edit page-->
                                                    </tr>
                                                </thead>

                                                <tbody>

                                                    <c:forEach var="transaction" items="${incomingTransactions}">

                                                        <tr>
                                                            <td><a class="transaction-item-ref"
                                                                    href="transactionedit?transid=${transaction.transID}">
                                                                    <c:out value="${transaction.name}" /></a></td>
                                                            <td>
                                                                <c:out value="${transaction.amount}" />
                                                            </td>
                                                            <td>
                                                                <c:out value="${transaction.date}" />
                                                            </td>
                                                            <td>
                                                                <c:out value="${transaction.username}" />
                                                            </td>
                                                        </tr>

                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>


                    <div class="col-sm-12 col-md-6">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Outgoings</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a
                                            href="<%= response.encodeURL(request.getContextPath() + "/transactionlist") %>"><i
                                                class="fa fa-list"></i> Show All</a></li>
                                    <li><a
                                            href="<%= response.encodeURL(request.getContextPath() + "/transactionnew") %>"><i
                                                class="fa fa-plus"></i> New Transaction</a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="card-box table-responsive">
                                            <!--table1 palcement-->
                                            <table id="datatable-responsive"
                                                class="table table-striped table-bordered dt-responsive table-hover nowrap"
                                                cellspacing="0" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <!-- from here, link to the transaction details and edit page-->
                                                        <th>Amount</th>
                                                        <th>Date</th>
                                                        <th>Managed By</th>
                                                        <!-- from here, link to the related activity details and edit page-->
                                                    </tr>
                                                </thead>

                                                <tbody>

                                                    <c:forEach var="transaction" items="${outgoingTransactions}">

                                                        <tr>
                                                            <td><a class="transaction-item-ref"
                                                                    href="transactionedit?transid=${transaction.transID}">
                                                                    <c:out value="${transaction.name}" /></a></td>
                                                            <td>
                                                                <c:out value="${transaction.amount}" />
                                                            </td>
                                                            <td>
                                                                <c:out value="${transaction.date}" />
                                                            </td>
                                                            <td>
                                                                <c:out value="${transaction.username}" />
                                                            </td>
                                                        </tr>

                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
            <!-- /page content -->

            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />
</body>

</html>