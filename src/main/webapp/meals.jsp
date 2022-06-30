<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="resources/css/style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<form action="/createMeal" method="get">
    <input class="inblock" type="submit" value="Add new meal" onclick="window.location.href= 'addNewMeal'"/>
</form>
<br>
<br>
<table class="table_left">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Operators</th>
    </tr>
    <c:forEach items="${allMeals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr>
            <c:url var="updateButton" value="/mealUpdate">
                <c:param name="mealId" value="${meal.id}"/>
            </c:url>
            <c:url var="deleteButton" value="/mealDelete">
                <c:param name="mealId" value="${meal.id}"/>
            </c:url>
        <tr class="${meal.excess? 'excess':'norm'}">
            <th>
                <%=TimeUtil.toString(meal.getDateTime())%>
                    ${fn:replace(meal.dateTime, 'T', ' ')}
            </th>
            <th>${meal.description}</th>
            <th>${meal.calories}</th>
            <th>
                <input type="button" value="update" onclick="window.location.href='${updateButton}'"/>
                <input type="button" value="delete" onclick="window.location.href='${deleteButton}'"/>
            </th>
        </tr>
    </c:forEach>
</table>
</body>
</html>