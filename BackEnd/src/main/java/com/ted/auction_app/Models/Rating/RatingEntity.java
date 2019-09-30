package com.ted.auction_app.Models.Rating;

import com.ted.auction_app.Models.User.UserEntity;

import javax.persistence.*;

@Entity
@Table(name="ratings")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rated")
    private UserEntity rated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "was_rated")
    private UserEntity wasRated;

    @Column(name = "rating")
    private Integer rating;

    public RatingEntity() {
        rated = new UserEntity();
        wasRated = new UserEntity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getRated() {
        return rated;
    }

    public void setRated(UserEntity rated) {
        this.rated = rated;
    }

    public UserEntity getWasRated() {
        return wasRated;
    }

    public void setWasRated(UserEntity wasRated) {
        this.wasRated = wasRated;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
