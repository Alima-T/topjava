package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * @Alima-T 7/10/2022
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }
@Autowired
private  MealService mealService;

    @Test
    public void create() {
        Meal created = mealService.create(getNew(), USER_ID);
        Integer newID = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newID);
        assertMatch(created,newMeal);
        assertMatch(mealService.get(newID, USER_ID), newMeal);
    }

    @Test
    public void get() {
     Meal actual = mealService.get(MEAL1_ID, USER_ID);
     assertMatch(actual, meal1);
    }

    @Test
    public void getAdminMeal() {
        Meal actual= mealService.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, adminMeal1);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, ()->mealService.get(MEAL1_ID, USER_ID));
//                new ThrowingRunnable() {
//                    @Override
//                    public void run() throws Throwable {
//                        mealService.get(MEAL1_ID, USER_ID);
//                    }
//                })
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(mealService.getBetweenInclusive(
                LocalDate.of(2022, Month.JULY,30),
                LocalDate.of(2022, Month.JULY,30),
                USER_ID), meal1,meal2,meal3);
    }

    @Test
    public void getAll() {
        List<Meal>allMeals = mealService.getAll(USER_ID);
        assertMatch(allMeals, meals);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated,USER_ID);
        assertMatch(mealService.get(MEAL1_ID, USER_ID), getUpdated());
    }
}