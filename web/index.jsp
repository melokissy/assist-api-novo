<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <h1>Register !</h1>
         <form method="post" action="upload" enctype="multipart/form-data">
             <div>
               Select File to Upload: <input type="file" name="avatar"/>
            </div>
            <div>
                <input type="submit" value="Submit" />
            </div>
            <div>
                 <% 
                    String message = (String) request.getAttribute("erreur");
                    if(message != null)
                        out.println( message );
                %>
        </form>
    </body>
</html>
