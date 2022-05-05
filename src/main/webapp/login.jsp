<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Login - Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
    
</head>

<body class="login"> 
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>
    <div class="login_wrapper"> 

      <div class="animate form login_form">
        <section class="login_content">
          <div class="login-background">
            <img class = "logo-img" src="images/logoruzzante.png" alt="logo Ruzzante 2018" style="height:80px">
          </div>
          <form id = "loginForm">              
            <p>Theater Management System</p>
            <h1>Login</h1>
            <div>
              <input type="text" class="form-control" placeholder="Username" required="" name="username"/>
            </div>
            <div>
              <input type="password" class="form-control" placeholder="Password" required="" name="password" id="pswField" />
            </div>
            <div>
              <a id="submitButton" type="submit" class="btn btn-secondary">Log in</a>
              <a type="submit" class="btn btn-secondary" href="<%= response.encodeURL(request.getContextPath() + "/forgotpsw.jsp") %>">Forget your password?</a>
            </div>
             <div class="clearfix"></div>
             <div class="separator">                
              <div class="clearfix"></div>

              <div id="servMessage"></div> 

              <br />
              <div>
                <h4><a href="http://www.ruzzante.eu">Ruzzante.eu</a></h4>                        
              </div>
            </div>
          </form>
        </section>
      </div>
    </div>

    <script src="./vendors/jquery/dist/jquery.min.js"></script>
    <script src="./vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="./build/js/thecrew.js"></script>
    
    <script>

      $(document).ready(function() {

        
        var input = document.getElementById("pswField");

        // Execute a function when the user releases a key on the keyboard
        input.addEventListener("keyup", function(event) {
          // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // Cancel the default action, if needed
        event.preventDefault();
            // Trigger the button element with a click
        document.getElementById("submitButton").click();
        }
        });

        
        $("#submitButton").click(function (e) {

          var elem = document.getElementById('loginForm')

          var fData = new FormData(elem);
          var x = {};

          user = fData.get("username"); //eventually here place .toLowerCase()
          psw = fData.get("password");

          var authString = user + ":" + psw;
          authString = btoa(authString);
          
          $.ajax({
              url: './data/login.json',
              type: 'GET',
              headers: {
              Authorization: 'Basic '+ authString
              },
              cache: false,
              success: function() {
                var url = window.location.href;
                
                if (url.includes("login")) {
                  window.location.replace("./dashboard");
                  //$(location).attr('href', './dashboard');
               } else {
                location.reload(); 
                
               }
              },
              error: function() {addErrorMessage("Invalid username or password");}
          });

          function addErrorMessage(text) {
            if (text == null){
                text = "Something went wrong"
            }
            var htmlcontent ='<div class="alert alert-danger alert-dismissible fade show">';
                htmlcontent = htmlcontent + text;
                htmlcontent = htmlcontent + '<button type="button" class="close" data-dismiss="alert">&times;</button></div>'; 
        
             $("#servMessage").append(htmlcontent);
          }

        })

      })


    </script>
  </body>
</html>