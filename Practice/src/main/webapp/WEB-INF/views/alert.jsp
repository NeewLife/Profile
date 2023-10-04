<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<script>
    window.onload = function(){
        var msg = "<c:out value='${msg}'/>";
        alert(msg);
        location.href = "/";
    }
</script>
<head>

</head>
<body>

</body>
</html>