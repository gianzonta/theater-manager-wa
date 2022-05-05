<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>404 Not Found | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
    
    <style type="text/css">
      img {
        margin: 0 auto;
      }
      div.background {
        text-align: center;
        border: 0px;
      }
      div.login_wrapper {
        margin-top: 20px;
        margin-bottom: -10px;
      }
    </style>    
</head>

<body class="nav-md"> 

  <div class="container body">
    <div class="main_container">
      <!-- page content -->
      <div class="col-md-12">
        <div class="col-middle">
          <div class="text-center text-center">
            <h1 class="error-number">404</h1>
            <h2>Sorry but we couldn't find this page</h2>
            <p>This page you are looking for does not exist
            </p>
            <div class="mid_center">
              <form>
                <div class="  form-group pull-right top_search">
                  <div class="background">
                    <a href="<%= response.encodeURL(request.getContextPath() + "/dashboard") %>"><img src="images/logoruzzante.png" alt="logo Ruzzante 2018" style="height:80px"></a>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <!-- /page content -->
    </div>
  </div>
  </body>
</html>