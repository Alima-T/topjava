package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;


    @Override
    public User save(User user) {

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userRepository.put(user.getId(), user);
            return user;
        }
        return userRepository.computeIfPresent(user.getId(), (id, oldUser) -> user); // вместо put берем computeIfPresent, позволяет не добавлять юзера, если он уже удалился или если пришел с id, который отсутствует в map, то он не будет добавляться
    }

    @Override
    public boolean delete(int id) {
        return userRepository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        for (User user : userRepository.values()) {
            list.add(user);
        }
        list.sort(Comparator.comparing((User user1) -> user1.getName()).thenComparing(user -> user.getEmail()));
        return list;
    }
    //В java 8, появился который позволяет вызывать функцию, которая должна возвращать класс String который реализует Comparable. Т.к. имя неуникально, то добавляем доп сроавнение по уникальному признаку email)

//    Short
//    @Override
//    public List<User> getAll() {
//       return userRepository.values()
//               .stream()
//                .sorted(Comparator.comparing(User::getName).thenComparing(user -> user.getEmail()))
//            .collect(Collectors.toList());
//}

    @Override
    public User getByEmail(String email) {
        return userRepository.values()
                .stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst() //returns Optional, a container object which may or may not contain a non-null value, можем фильтровать, давать ему map и не бояться получить NullPointerException
                .orElse(null);
    }
}
