<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Accident App</title>
</head>
<body>

<div class="container pt-3">

    <h4>
        Login as : ${user}
    </h4>

    <div class="row">

        <table class="table">
            <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>text</th>
                <th>address</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${accidents}" var="accident">
                <tr>
                    <td>${accident.id}</td>
                    <td>${accident.name}</td>
                    <td>${accident.text}</td>
                    <td>${accident.address}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

</div>
</body>
</html>