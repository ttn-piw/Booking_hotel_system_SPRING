package com.example.bookinghotel.services;

import com.example.bookinghotel.models.review;
import com.example.bookinghotel.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewsService {
    @Autowired
    ReviewRepository reviewRepository;
    public List<review> getReviewsService() {
        return reviewRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String,Object>> getReviewsServiceJSON() {
        List<String> columnNamesReviews = List.of("ID","Customer","Hotel Name","Room Type","Score","Contents");

        String queryReviews =
                "SELECT review.review_id, person.PName, h.HName, ctg.CTGName, r.rating, r.rating_text " +
                        "FROM review r JOIN person p ON r.person.PID = p.PID JOIN room ctg ON r.room.CTGID = ctg.CTGID " +
                        "JOIN hotel h ON ctg.hotels.HID = h.HID "+
                        "ORDER BY h.HName ASC";

        List<Object[]> results = entityManager.createQuery(queryReviews).getResultList();

        List<Map<String,Object>> data = new ArrayList<>();
        for (Object[] row : results){
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < columnNamesReviews.size(); i++) {
                rowMap.put(columnNamesReviews.get(i), row[i]);
            }
            data.add(rowMap);
        }
        return data;
    }

    public List<Map<String, Object>> getReviewsByRID(Integer rid) {
        String query = "SELECT r.review_id, r.room.CTGName, r.person.PName, r.rating, r.rating_text " +
                "FROM review r WHERE r.room.CTGID = :rid";
        List<Object[]> results = entityManager.createQuery(query)
                .setParameter("rid", rid)
                .getResultList();

        List<String> columnNames = List.of("review_id", "CTGName", "PName", "rating", "rating_text");

        List<Map<String, Object>> data = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < columnNames.size(); i++) {
                rowMap.put(columnNames.get(i), row[i]);
            }
            data.add(rowMap);
        }

//        Map<String, Object> response = new HashMap<>();
////        response.put("columns", columnNames);
//        response.put("data", data);
        return data;
//        return response;
    }

    @Transactional
    public List<review> getBestReviewsService() {
        String query = "SELECT p.PName,r.rating_text FROM review r JOIN person p ON r.person.PID = p.ID WHERE r.rating = 5";
        return (List<review>) entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public List<review> getReviewsByHID(Integer HID) {
        String query =  "SELECT rv.rating, rv.rating_text, r.CTGName, rv.person.PName FROM review rv " +
                        "JOIN rv.room r " +
                        "JOIN rv.person p " +
                        "WHERE rv.room.hotels.HID = :HID";
        return entityManager.createQuery(query).setParameter("HID", HID).getResultList();
    }

    @Transactional
    public Boolean postReview(Integer pid, Integer ctgid, Double rating, String text) {
        String booked = "INSERT INTO reviews (ctgid, pid, rating, rating_text) " +
                "VALUES (:ctgid, :pid, :rating, :text)";
        try {
            entityManager.createNativeQuery(booked)
                    .setParameter("pid", pid)
                    .setParameter("ctgid", ctgid)
                    .setParameter("rating", rating)
                    .setParameter("text", text)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public void deleteReview(Integer rid) {
        String query = "DELETE FROM reviews WHERE review_id = :rid";
        entityManager.createNativeQuery(query).setParameter("rid", rid).executeUpdate();
    }
}
