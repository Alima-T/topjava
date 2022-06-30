package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public MealRepository mealRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("getAll");
        log.debug("redirect to meals");
        request.setAttribute("allMeals", MealsUtil.getTos(MealsUtil.meals, MealsUtil.calories_per_day)); // allMeals - название атрибута пойдет в jsp страницу в <c:forEach items="${allMeals}" var="meal">
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}