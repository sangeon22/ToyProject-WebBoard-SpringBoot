package com.springboard.webboard.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomizedUserRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Override
//    public List<User> findByUsernameCustom(String username) {
//        QUser qUser = QUser.user;
//        JPAQuery<?> query = new JPAQuery<Void>(em);
//        List<User> users = query.select(qUser)
//                .from(qUser)
//                .where(qUser.username.contains(username))
//                .fetch();
//        return users;
//    }
//
//    @Override
//    public List<User> findByUsernameJdbc(String username) {
//        List<User> users = jdbcTemplate.query(
//                "SELECT * FROM USER WHERE username like ?",
//                new Object[]{"%" + username + "%"},
//                new BeanPropertyRowMapper(User.class));
//        return users;
//    }
}
