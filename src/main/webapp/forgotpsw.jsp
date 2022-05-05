<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Login - Ruzzante TMS</title>
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
      /* div.modal {
        z-index: 0;        
      } */
      
    </style>
</head>

<body class="login">
   
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>
      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <div class="background">
              <img src="images/logoruzzante.png" alt="logo Ruzzante 2018" style="height:80px">
            </div>
            <form id="forgotpsw" data-target=".bs-example-modal-sm">              
              <p>Theater Management System</p>
              <h1>Forgot Password</h1>
              <h6>If you forgot your password, enter your email in this form: you'll receive 
                a link to get back into your account. </h6>
              <div>
                <input type="email" class="form-control" placeholder="Email" required>
              </div>             
              <div class="x_content">
                <!-- <a class="btn btn-default submit" data-toggle="modal" data-target=".bs-example-modal-sm">Send reset link</a>                
                  Small modal -->
                <button type="submit" class="btn btn-default submit">Send reset link</button>
                
                
              </div>
             
              <div class="clearfix"></div>

              <div class="separator">
                
                <div class="clearfix"></div>
                <br />

                <div>
                  <h4><a href="http://www.ruzzante.eu">Ruzzante.eu</a></h4>                        
                </div>
              </div>
            </form>
          </section>
        </div>

        <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form>
              <h1>Create Account</h1>
              <div>
                <input type="text" class="form-control" placeholder="Username" required="" />
              </div>
              <div>
                <input type="email" class="form-control" placeholder="Email" required="" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="" />
              </div>
              <div>
                <a class="btn btn-default submit" href="index.html">Submit</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Already a member ?
                  <a href="#signin" class="to_register"> Log in </a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1>Ruzzante.eu</h1>                  
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
      <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-sm">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title" id="myModalLabel2">An email has been sent</h4>
            </div>
            <div class="modal-body">
              <h4>Check your inbox</h4>
              <p>An email with a confirmation link has been sent. In a few moments you will be able to access your profile and modify the current password.</p>
              <p>If you are having trouble receiving the email confirmation link, try to check your spam folder.</p>                          
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              <!-- <button type="button" class="btn btn-primary" href="login.jsp">Back to Login</button> -->
              <a type="submit" role='button' class="btn btn-primary" href="login.jsp">Back to Login</a>
            </div>
          </div>
        </div>
      </div>
      <jsp:include page="layout/assets_footer.jsp" />
</body>
</html>