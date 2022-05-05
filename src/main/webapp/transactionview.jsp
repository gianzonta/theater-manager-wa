<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>View Transaction | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>View transaction</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Transaction details</h2>

                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/transactionedit") %>"><i class="fa fa-wrench"></i> Edit this transaction</a>
                                        </li>
                                    </ul>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-8 offset-sm-2">
                
                
                                            <!-- transaction details --> 

                                            <table class="table table-hover">


                                                <tbody>

                                                  <tr>

                                                    <th scope="row">Transaction ID</th>

                                                    <td>12568</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Name</th>

                                                    <td>Rehearsals Rooms, AY 2018/2019</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Amount</th>

                                                    <td>-850,55</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Date</th>

                                                    <td>15/09/2018</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Description</th>

                                                    <td>Yearly Rehearsal rooms renting, 15/10/2018-30/05/2019, Padua Arts center</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Related activity</th>

                                                    <td>n/d</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Related Item</th>

                                                    <td>n/d</td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Invoice</th>

                                                    <td>Open PDF here <i class="fa fa-external-link-square"></i></td>

                                                  </tr>


                                                  <tr>

                                                    <th scope="row">Managed By</th>

                                                    <td>Jack Linn</td>

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
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />




</body>

</html>