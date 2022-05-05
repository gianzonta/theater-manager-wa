<!DOCTYPE html>
<html lang="en">

<head>
  <script type="text/javascript" src="./js/userslist.js"></script>
  <jsp:include page="layout/header.jsp" />
  <title>Member list | Ruzzante TMS</title>
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

          <div class="clearfix"></div>

          <div class="row">
            <div class="col-md-12 col-sm-12 ">
              <div class="x_panel">
                <div class="x_title">
                  <h2>Member list</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a href="./addnewuser"><i class="fa fa-plus"></i> Add New Member</a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <div class="row">
                    <div class="col-sm-12">
                      <div class="card-box table-responsive">
                        <table id="members_table" class="table table-bordered table-striped table-hover"
                          style="width:100%">
                          <thead>
                            <tr>
                              <th>Username</th>
                              <th>Name</th>
                              <th>Surname</th>
                              <th>Birth Date</th>
                              <th>Email</th>
                              <th>Phone</th>
                              <th>Is PRO</th>
                              <th>Is UniPD</th>
                              <th>Assign Play</th>
                            </tr>
                          </thead>


                          <tbody id="userlist_all_table">

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

</body>


<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.js"></script>


<!-- Bootstrap -->
<link href="cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
<link href="../vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="../vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<!-- NProgress -->
<link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
<!-- iCheck -->
<link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
<!-- Datatables -->

<link href="./vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="./build/css/custom.min.css" rel="stylesheet">
<link href="./build/css/thecrew.css" rel="stylesheet">


</html>