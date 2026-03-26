package ro.andramates.realestate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.andramates.realestate.domain.Favorite;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.repository.FavoriteRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public List<Favorite> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    public List<Favorite> findByBuyer(User buyer) {
        return favoriteRepository.findByBuyer(buyer);
    }

    public Optional<Favorite> findByBuyerAndProperty(User buyer, Property property) {
        return favoriteRepository.findByBuyerAndProperty(buyer, property);
    }

    public boolean isFavorite(User buyer, Property property) {
        return favoriteRepository.existsByBuyerAndProperty(buyer, property);
    }

    public Favorite addToFavorites(User buyer, Property property) {
        if (favoriteRepository.existsByBuyerAndProperty(buyer, property)) {
            throw new RuntimeException("Proprietatea este deja la favorite.");
        }

        Favorite favorite = Favorite.builder()
                .buyer(buyer)
                .property(property)
                .build();

        return favoriteRepository.save(favorite);
    }

    public void removeFromFavorites(User buyer, Property property) {
        favoriteRepository.deleteByBuyerAndProperty(buyer, property);
    }
}