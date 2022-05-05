<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>New Item | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">

                <div id="servMessage"></div>

                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Add New Item</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>New Item Info</h2>
                                    <div class="clearfix"></div>
                                </div>

                                Fill the following fields, the ones marked with * are mandatory.

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" method="post" id="newActEntry">
    
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Name*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="name" required="required"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="description"/>
                                                </div>
                                            </div>

                                            

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Quantity</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class="form-control" type='number' name="quantity"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Size</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="size"  />
                                                </div>
                                            </div>


                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Historical Genre</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="historicalGenre" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Image</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="image" />
                                                </div>
                                            </div>

                                            

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Owner*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="username" required="required" />
                                                </div>
                                            </div>


                                            
                                        
                                            <div class="divider-dashed"></div>
    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-5"> 
                                                    <button type='submit' id='submitButton' class="btn btn-primary">Create Item</button>
                                                    <a href="<%= response.encodeURL(request.getContextPath() + "/itemlist") %>"><button type='button' class="btn btn-warning">Cancel</button></a>
                                                </div>
                                            </div>
                                        </form>                                          

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
            </div>
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />

    <script>
        

        
        $(function() {

            $("#newActEntry").submit(function(e) {

                e.preventDefault();
                
                var elem = document.getElementById('newActEntry')

                var fData = new FormData(elem);
                var x = {};

                x.name = fData.get("name");
                x.description = fData.get("description");
                x.quantity = fData.get("quantity");

                x.size = fData.get("size");
                x.historicalGenre = fData.get("historicalGenre");
                x.image = fData.get("image");

                x.username = fData.get("username");
                

               


                
                var jsonData = JSON.stringify(x);

                jsonData = '{ "item": ' + jsonData + "}";

                
                //TRADITIONAL AJAX CALL

                var url = "./content/item";

                var xhreq = new XMLHttpRequest();
                xhreq.onreadystatechange = alertState;

                xhreq.open('POST', url);
                xhreq.setRequestHeader('Content-type', 'application/json');
                xhreq.setRequestHeader('Data-type', 'application/json');
                xhreq.send(jsonData);

                function alertState() {
                    if (xhreq.readyState === XMLHttpRequest.DONE) {
                        if (xhreq.status == 201) {
                            var jsonData = JSON.parse(xhreq.responseText);
                            var displayData = "The item <strong> " + jsonData.item.name + " </strong>has been added to the system";
                          //  var newUrl =  "itemdetail?itemid=" + jsonData.item.itemID;
                         //   console.log(newUrl);
                            addSuccessMessage(displayData);
                        } else {
                            addErrorMessage();
                            var jsonData = JSON.parse(xhreq.responseText);
                            console.log(jsonData);
                        }
                    }
                };

            });


        });
    </script>

</body>

</html>