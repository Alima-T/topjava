package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

// TODO add userId Авторизация юзеров происходит на слое контроллеров, на слое веб.
//  После этого мы получаем id авторизированного юзера, сервисам и репозиториям не нужно ничего знать об авторизации.
// В результате мы можем любой юзер передавать, хотя бы для тестирования не создавая моков авторизации  на уровне сервисов.
// И изменение авторизации не повлечет за собой изменений во всем приложении, только на слое контроллеров
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userId );

    // false if meal does not belong to userId
    boolean delete(int id, int userId );

    // null if meal does not belong to userId
    Meal get(int id, int userId );

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId );

    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
