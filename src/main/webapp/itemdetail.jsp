<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>Item Detail - Ruzzante TMS</title>
  <jsp:include page="layout/assets_header.jsp" />
      <style>
    .form-control-1 {
    display: block;
    width: 100%;
    height: calc(1.25em + .75rem + 2px);
    padding: .375rem .75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #efefef;
    background-color: #272d33;
    background-clip: padding-box;
    border: none;
    border-radius: .25rem;
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
      }
    </style>
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
              <h3>Item Detail</h3>
            </div>
          </div>
          <div class="clearfix"></div>

          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="x_panel">
                
                <div class="x_content">
                  <form class="" action="" method="post" id="editItemForm">
                    <span class="section">Item Info</span>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Name:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" data-validate-length-range="1" name="name" required="required" disabled/>
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Description:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" name="description" required="required" disabled/>
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Quantity:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" name="quantity" required='required' disabled></div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Size:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" name="size" required="required" disabled/></div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Historical Genre:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" name="historicalGenre" required='required' disabled
                          /></div>
                    </div>

                    
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Image:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" name="image" required='required' disabled>
<!--                         <button>Go</button> -->
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Owner:<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control-1" data-validate-length-range="1" id="username" name="username"
                          required="required" disabled/>
                      </div>
                    </div>

                    

                    

                    <div class="ln_solid"></div>
                    <div class="form-group">
                      <div class="col-md-6 offset-md-3">
                          <button type='Edit' class="btn btn-primary" id = "goedit">Edit</button>
                          <a href="<%= response.encodeURL(request.getContextPath() + "/itemlist") %>"><button type='button' class="btn btn-warning">Cancel</button></a>
                      </div>
                    </div>
                  </form>
                  <div id="servMessage"></div>
                </div>
              </div>
            </div>

            

            </div>


          </div>
        </div>
      </div>

      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script src="./js/ajax_item_edit.js"></script>

      <script>load_item_data();</script>

      <script>
                $(document).ready(function() {

           
        $("#goedit").click(function(e) {
            var itemid = (new URLSearchParams(window.location.search)).get('itemid');
            //TRADITIONAL AJAX CALL

            var url = "./edititem?itemid=" + itemid;

            location.href = url;

            });


    });
      </script>

      <jsp:include page="layout/footer.jsp" />
    </div>
  </div>

  <jsp:include page="layout/assets_footer.jsp" />
</body>



</html>