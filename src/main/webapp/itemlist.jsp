<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>All The Items | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body" id="item_page">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Item List</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    
                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Item Info</h2>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/addnewitem") %>"><i class="fa fa-plus"></i> New Item</a>
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
                                                    <th>Description</th>                                                    <th>Quantity</th>
                                                    <th>Size</th>
                                                    <th>Historical</th> <!-- from here, link to the related activity details and edit page-->
                                                    <th>Image</th>
                                                    <th>Owner</th>


                                                    <th>Actions</th>
                                                </tr>
                                            </thead>

                                            <tbody  id="item_table">
                                                
                                               
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
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />
    <script type="text/javascript" src="./js/ajax_item.js"></script>

</body>

</html>