<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>hello.jsp</title>
</head>
<body>
    <p>data(EL문법) : ${myData} </p>
    <p>data(jstl문법 - java코드) : <%
    String getData = (String)request.getAttribute("myData");
    out.println(getData);
    %> </p>

    <form action="/hello/servlet/jsp/post" method="post">
        이름 : <input type="text" name="name"><br>
        이메일 : <input type="text" name="email"><br>
        패스워드 : <input type="text" name="password"><br>
        <button type="submit">submit</button>
    </form>
</body>
</html>