package ro.andramates.realestate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.andramates.realestate.domain.Property;
import ro.andramates.realestate.domain.PropertyStatus;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.repository.PropertyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<Property> findAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> findById(Integer id) {
        return propertyRepository.findById(id);
    }

    public List<Property> findBySeller(User seller) {
        return propertyRepository.findBySeller(seller);
    }

    public List<Property> findByStatus(PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }

    public List<Property> findBySellerAndStatus(User seller, PropertyStatus status) {
        return propertyRepository.findBySellerAndStatus(seller, status);
    }

    public List<Property> searchByLocation(String location) {
        return propertyRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Property> searchByTitle(String title) {
        return propertyRepository.findByTitleContainingIgnoreCase(title);
    }

    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    public Property addProperty(Property property) {
        if (property.getStatus() == null) {
            property.setStatus(PropertyStatus.PENDING);
        }
        return propertyRepository.save(property);
    }

    public Property updateProperty(Property property) {
        return propertyRepository.save(property);
    }

    public void deleteById(Integer id) {
        propertyRepository.deleteById(id);
    }

    public void approveProperty(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Proprietatea nu a fost gasita."));

        property.setStatus(PropertyStatus.APPROVED);
        propertyRepository.save(property);
    }

    public void rejectProperty(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Proprietatea nu a fost gasita."));

        property.setStatus(PropertyStatus.REJECTED);
        propertyRepository.save(property);
    }
}