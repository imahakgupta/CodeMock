<%@page import="com.codemock.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // SECURITY CHECK: Agar koi user bina login kiye (direct URL daal kar) yahan aana chahe
    User activeUser = (User) session.getAttribute("activeUser");
    if (activeUser == null) {
        response.sendRedirect("index.jsp"); // Wapas login page par fenk do
        return; // Niche ka code run mat karo
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard - CodeMock</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

    <div style="text-align: center; margin-top: 100px;">
        <h1>Welcome to CodeMock Dashboard!</h1>
        <h2 style="color: var(--primary-color);">Hello, <%= activeUser.getFullName() %> 👋</h2>
        <p>Your verified email is: <%= activeUser.getEmail() %></p>
        
        <br><br>
        <a href="index.jsp" style="padding: 10px 20px; background: red; color: white; text-decoration: none; border-radius: 5px;">Logout (Demo)</a>
    </div>

</body>
</html>