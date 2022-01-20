<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Accident App</title>
</head>
<body>

<div class="card mx-auto" style="width: 30%">

    <div class="card-header bg-primary text-white" style="font-size: large">
        Login
    </div>

    <div class="card-body">
        <form name='login' action="<c:url value='/login'/>" method='POST'>
            <table>
                <tr>
                    <td>UserName:</td>
                    <td><input type='text' name='username'></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='password'/></td>
                </tr>
                <tr>
                    <td colspan='2'>
                        <input class="btn btn-primary" name="submit" type="submit" value="submit" />
                        <a class="btn btn-primary" href="<c:url value='/reg'/>">registration</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>

        <c:if test="${not empty errorMessage}">
            <div style="color:red; font-weight: bold; margin: 30px 0px;">
                    ${errorMessage}
            </div>
        </c:if>

    </div>

</div>

</body>
</html>