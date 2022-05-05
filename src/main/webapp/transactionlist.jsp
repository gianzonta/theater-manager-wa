<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>All The Transactions | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body" id="whole_page">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Transaction List</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    
                    <div class="row">
                        <div class="col-md-7 col-sm-7">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Latest transactions</h2>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/transactionnew") %>"><i class="fa fa-plus"></i> New Transaction</a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="card-box table-responsive">
                                    <!--table1 palcement-->
                                        <table id="datatable-responsive" class="table table-bordered table-hover dt-responsive nowrap " cellspacing="0" width="100%" >
                                            <thead>
                                                <tr>
                                                    <th>Name</th> <!-- from here, link to the transaction details and edit page-->
                                                    <th>Amount</th>
                                                    <th>Date</th>
                                                    <th>Managed By</th> <!-- from here, link to the related activity details and edit page-->
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>

                                            <tbody style="cursor:pointer;" class= "align-middle" id="transactions_table">
                                                
                                                <!--
                                                <tr class="transEntry" id="1">
                                                    <td><a class="transaction-item-ref" >Rehearsals rooms, AY 2018/2019</a></td> 
                                                    <td>-850,55</td>
                                                    <td>15/09/2018</td>
                                                    <td>Jack Linn</td>
                                                    <td><a href="transactionedit"> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>

                                                <tr class="transEntry" id="2">
                                                    <td>Facebook sponsorship</td>
                                                    <td>-20</td>
                                                    <td>25/04/2019</td>
                                                    <td>Marta Brown</td>
                                                    <td><a href=""> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>

                                                <tr>
                                                    <td>Event offers</td>
                                                    <td>30</td>
                                                    <td>11/05/2019</td>
                                                    <td>Roland Berry</td>
                                                    <td><a href=""> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>

                                                <tr >
                                                    <td>Facebook sponsorship</td>
                                                    <td>-20</td>
                                                    <td>25/04/2019</td>
                                                    <td>Marta Brown</td>
                                                    <td><a href=""> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>

                                                <tr>
                                                    <td>Event offers</td>
                                                    <td>30</td>
                                                    <td>11/05/2019</td>
                                                    <td>Roland Berry</td>
                                                    <td><a href=""> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>

                                                <tr >
                                                    <td>Facebook sponsorship</td>
                                                    <td>-20</td>
                                                    <td>25/04/2019</td>
                                                    <td>Marta Brown</td>
                                                    <td><a href=""> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>

                                                <tr>
                                                    <td>Event offers</td>
                                                    <td>30</td>
                                                    <td>11/05/2019</td>
                                                    <td>Roland Berry</td>
                                                    <td><a href=""> <button class="btn btn-primary small-tab-btn" type="button">Edit</button></a></td>
                                                </tr>
                                            -->
                                            </tbody>
                                        </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>

                        <div class="col-md-5 col-sm-5">
                            <div class="position-fixed">
                            <div class="x_panel info-panel" >
                                <div class="x_title">
                                    <h2>Transaction detail</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="card-box table-responsive">
                                    <!--table1 palcement-->
                                    <table class="table table-striped table-bordered dt-responsive nowrap" >


                                        <tbody>

                                          <tr>
                                            <th scope="row">Transaction ID</th>
                                            <td id = "ID" ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Name</th>
                                            <td id = "name" ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Amount</th>
                                            <td id = "amount" ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Date</th>
                                            <td id = "date"  ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Description</th>
                                            <td id = "desc" ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Related activity</th>
                                            <td id = "relAct" ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Related Item</th>
                                            <td id = "relItem" ></td>
                                          </tr>


                                          <tr>
                                            <th scope="row">Invoice</th>
                                            <td id = "invoice" ></td>
                                          </tr>

                                          <tr>
                                            <th scope="row">Managed By</th>
                                            <td id = "mngby" ></td>
                                          </tr>

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
                </div>
            </div>
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />
    <script type="text/javascript" src="./js/ajax_transactionlist.js"></script>

</body>

</html>