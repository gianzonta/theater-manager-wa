<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>Edit Item - Ruzzante TMS</title>
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
              <h3>Edit Item</h3>
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
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Item ID<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" data-validate-length-range="1" name="itemID" required="required" readonly />
                      </div>
                    </div>

                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Name<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" data-validate-length-range="1" name="name" required="required" />
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Description<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" name="description" />
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Quantity<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" name="quantity" ></div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Size<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" name="size"  /></div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Historical Genre<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" name="historicalGenre" 
                          /></div>
                    </div>

                    
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Image<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" name="image" >
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Owner<span
                          class="required"></span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" data-validate-length-range="1" id="username" name="username" readonly />
                      </div>
                    </div>

                    

                    

                    <div class="ln_solid"></div>
                    <div class="form-group">
                      <div class="col-md-6 offset-md-3">
                        <button type='submit' class="btn btn-primary">Submit</button>
                        <button type="button" class="btn btn-danger" id = "delete">Delete</button>
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

           
        $("#delete").click(function(e) {
            var itemid = (new URLSearchParams(window.location.search)).get('itemid');
            //TRADITIONAL AJAX CALL

            var url = "./content/item/" + itemid;

            var xhreq = new XMLHttpRequest();
            xhreq.onreadystatechange = alertState;

            xhreq.open('DELETE', url);
            xhreq.setRequestHeader('Content-type', 'application/json');
            xhreq.setRequestHeader('Data-type', 'application/json');
            xhreq.send();

            function alertState() {
                if (xhreq.readyState === XMLHttpRequest.DONE) {
                    if (xhreq.status == 201 || xhreq.status == 200) {
                        var jsonData = JSON.parse(xhreq.responseText);
                        var displayData = "The item <strong> " + jsonData.item.name + " </strong>has been deleted from the system, you will be redeirected to the item list in a few seconds";
                        addWarningMessage(displayData);
                        setTimeout(function() {
                            window.location.href = "itemlist";
                        }, 2000);
                        
                    } else {
                        addErrorMessage();
                    }
                }
            };

            });


    });
    </script>

      

      <jsp:include page="layout/footer.jsp" />
    </div>
  </div>

  <jsp:include page="layout/assets_footer.jsp" />
</body>



</html>