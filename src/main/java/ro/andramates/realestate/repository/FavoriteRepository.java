package ro.andramates.realestate.repository;

import ro.andramates.realestate.domain.Favorite;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByBuyer(User buyer);

    Optional<Favorite> findByBuyerAndProperty(User buyer, Property property);

    boolean existsByBuyerAndProperty(User buyer, Property property);

    void deleteByBuyerAndProperty(User buyer, Property property);
}