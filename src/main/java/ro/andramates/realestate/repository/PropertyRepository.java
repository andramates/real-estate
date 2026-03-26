package ro.andramates.realestate.repository;

import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.PropertyStatus;
import ro.andramates.realestate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findBySeller(User seller);

    List<Property> findByStatus(PropertyStatus status);

    List<Property> findBySellerAndStatus(User seller, PropertyStatus status);

    List<Property> findByLocationContainingIgnoreCase(String location);

    List<Property> findByTitleContainingIgnoreCase(String title);

    List<Property> findByStatusAndLocationContainingIgnoreCase(PropertyStatus status, String location);
}