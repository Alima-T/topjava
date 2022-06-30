package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.repository.MealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    public MealRepository mealRepository;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mealIds = req.getParameter("deleteMeal");
        String[]mealIdsToDelete = mealIds.split("-");
        for(String id:mealIdsToDelete){
            mealRepository.mealDelete(id);
        }
        resp.sendRedirect("/meals");
    }
}
