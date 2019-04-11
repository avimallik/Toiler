<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

      <script>
        $(document).ready(function(){

            $("#add").click(function(){
                  if(!regX($("#val_one").val()) || !regX($("#val_two").val())){
                     showMsg();
                  }else{
                    var val_res = parseFloat($("#val_one").val()) + parseFloat( $("#val_two").val() ); 
                    $("#val_result").val(val_res);
                    hideMsg();
                  }
            });

            $("#subtract").click(function(){

              if(!regX($("#val_one").val()) || !regX($("#val_two").val())){
                  showMsg();
              }else{
                  var val_res = parseFloat($("#val_one").val()) - parseFloat( $("#val_two").val() ); 
                  $("#val_result").val(val_res);  
                  hideMsg();
              }
            });

            $("#division").click(function(){
              if(!regX($("#val_one").val()) || !regX($("#val_two").val())){
                 showMsg();
              }else{
                 var val_res = parseFloat($("#val_one").val()) / parseFloat( $("#val_two").val() ); 
                 $("#val_result").val(val_res);
                 hideMsg();
              }
              
            });

            $("#multiplication").click(function(){
              if(!regX($("#val_one").val()) || !regX($("#val_two").val())){
                showMsg();
              }else{
                var val_res = parseFloat($("#val_one").val()) * parseFloat( $("#val_two").val() ); 
                $("#val_result").val(val_res);
                hideMsg();
              }
            });
            
            //Regular Expression Check
            function regX(checkRegX){
               var pattern = new RegExp(/^(?=.*\d)\d*(?:\.\d*)?$/);
               return pattern.test(checkRegX);
            }

            function showMsg(){
              $("#msg").show(600);
            }

            function hideMsg(){
              $("#msg").hide(600);
            }

        });
      </script>
    </head>


<body>
  
  <header>
 <!-- Fixed navbar -->
 <nav class="navbar navbar-inverse">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#"><font size = "5pt">Armapprise</font></a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <li class="active"><a href="#">Home</a></li>
          <li class="active"><a href="postarium.php">Postarium</a></li>
          <li><a href="#about">About</a></li>
          <li><a href="#contact">Contact</a></li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li class="dropdown-header">Software</li>
              <li><a href="#">Android Projects</a></li>
              <li><a href="#">Java Projects</a></li>
              <li><a href="ajaxtest.html">Web Application Projects</a></li>
              <li><a href="#">Chat Bot Projects</a></li>
              <li role="separator" class="divider"></li>
              <li class="dropdown-header">Embedded System</li>
              <li><a href="#">Arduino</a></li>
              <li><a href="#">Embedded System Software Projects</a></li>
            </ul>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li><a href="../navbar/">Default</a></li>
          <li><a href="../navbar-static-top/">Static top</a></li>
          <li class="active"><a href="./">Fixed top <span class="sr-only">(current)</span></a></li>
        </ul>
      </div><!--/.nav-collapse -->
    </div>
  </nav>
</header>

      <br><br><br><br>

<center><h2><b>JQuery Calculator</b></h2></center> <br>
<center><p id = "msg" hidden><font color = red><b>Enter Valid Number : </b></p></font></center> <br>

<div class="container">
    <form>
      <center>
        <div class="form-group">
            <input type="text" class="form-control" id = "val_one" placeholder="Number One" style="width:300px;height:45px;font-size:12pt;text-align:center">
          </div>
       </center>   
       <center>
          <div class="form-group">
              <input type="text" class="form-control" id = "val_two" placeholder="Number Two" style="width:300px;height:45px;font-size:12pt;text-align:center">
            </div>
         </center> 
         <center>
            <div class="form-group">
                <input type="text" class="form-control" id = "val_result" placeholder="Result" style="width:300px;height:45px;font-size:12pt;text-align:center" disabled>
              </div>
           </center> 

           <center>
           <div class="btn-group">
              <button type="button" class="btn btn-primary" style="width:76px;height:45px" id = "add"><font size = 5pt>+</font></button>
              <button type="button" class="btn btn-primary" style="width:76px;height:45px" id = "subtract"><font size = 5pt>-</font></button>
              <button type="button" class="btn btn-primary" style="width:76px;height:45px" id = "multiplication"><font size = 5pt>*</font></button>
              <button type="button" class="btn btn-primary" style="width:76px;height:45px" id = "division"><font size = 5pt>/</font></button>
            </div>
            </center>

      </form>
</div>

 

</body>
</html>
