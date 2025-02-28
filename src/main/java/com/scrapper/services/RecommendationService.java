package com.scrapper.services;

import com.scrapper.entities.Offer;
import com.scrapper.entities.Price;
import com.scrapper.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecommendationService {

    public double epsilon = Double.MIN_VALUE;
    private double priceRentalMax = Double.MIN_VALUE;
    private double priceRentalMin = Double.MAX_VALUE;
    private double priceSellMax = Double.MIN_VALUE;
    private double priceSellMin = Double.MAX_VALUE;
    private double distanceFromCenterMax = Double.MIN_VALUE;
    private double distanceFromCenterMin = Double.MAX_VALUE;
    private double imagesRatingMax = Double.MIN_VALUE;
    private double imagesRatingMin = Double.MAX_VALUE;
    private double apartmentSizeMax = Double.MIN_VALUE;
    private double apartmentSizeMin = Double.MAX_VALUE;
    private double roomsQuantityMax = Double.MIN_VALUE;
    private double roomsQuantityMin = Double.MAX_VALUE;

    public RecommendationService(OfferRepository offerRepository) {
        Optional<Offer> maxPriceRentalOffer = offerRepository.findMaxPriceRentalOffer();
        maxPriceRentalOffer.map(Offer::getPrice).map(Price::getValue).ifPresent(this::setPriceRentalMax);

        Optional<Offer> minPriceRentalOffer = offerRepository.findMinPriceRentalOffer();
        minPriceRentalOffer.map(Offer::getPrice).map(Price::getValue).ifPresent(this::setPriceRentalMin);

        Optional<Offer> maxPriceSellOffer = offerRepository.findMaxPriceSellOffer();
        maxPriceSellOffer.map(Offer::getPrice).map(Price::getValue).ifPresent(this::setPriceSellMax);

        Optional<Offer> minPriceSellOffer = offerRepository.findMinPriceSellOffer();
        minPriceSellOffer.map(Offer::getPrice).map(Price::getValue).ifPresent(this::setPriceSellMin);

        Optional<Offer> maxDistanceFromCenterOffer = offerRepository.findMaxDistanceFromCenterOffer();
        maxDistanceFromCenterOffer.map(Offer::getDistanceFromCenter).ifPresent(this::setDistanceFromCenterMax);

        Optional<Offer> minDistanceFromCenterOffer = offerRepository.findMinDistanceFromCenterOffer();
        minDistanceFromCenterOffer.map(Offer::getDistanceFromCenter).ifPresent(this::setDistanceFromCenterMin);

        Optional<Offer> maxImagesRatingOffer = offerRepository.findMaxImagesRatingOffer();
        maxImagesRatingOffer.map(Offer::getImagesRating).ifPresent(this::setImagesRatingMax);

        Optional<Offer> minImagesRatingOffer = offerRepository.findMinImagesCenterOffer();
        minImagesRatingOffer.map(Offer::getImagesRating).ifPresent(this::setImagesRatingMin);
    }

    public double calculateOfferAttractiveness(Offer offer){
        if (offer == null){
            return -1.0;
        }

        Price priceObj = offer.getPrice();
        double price = offer.isOnSell() ? normalizeSellPrice(priceObj) : normalizeRentalPrice(priceObj);
        double imagesRating = normalizeImagesRating(offer.getImagesRating());
        double distanceFromCenter = normalizeDistanceFromCenter(offer.getDistanceFromCenter());

        double apartmentSize = normalizeApartmentSize(offer.getApartmentSize());
        double roomsQuantity = normalizeRoomsQuantity(offer.getNumberOfRooms());
        return  (0.25 * price) + (0.25 * distanceFromCenter) + (imagesRating * 0.25) + (apartmentSize * 0.05) + (roomsQuantity * 0.20);
    }

    protected double normalizeRentalPrice(Price priceObj){
        if (priceObj == null){
            return -1.0;
        }

        double price = priceObj.getValue();
        setPriceRentalMax(price);
        setPriceRentalMin(price);

        return (priceRentalMax - price)/(priceRentalMax - priceRentalMin + epsilon);
    }

    protected double normalizeSellPrice(Price priceObj){
        if (priceObj == null){
            return -1.0;
        }

        double price = priceObj.getValue();
        setPriceSellMax(price);
        setPriceSellMin(price);

        return (priceSellMax - price)/(priceSellMax - priceSellMin + epsilon);
    }

    protected double normalizeDistanceFromCenter(double distanceFromCenter){
        setDistanceFromCenterMax(distanceFromCenter);
        setDistanceFromCenterMin(distanceFromCenter);

        return (distanceFromCenterMax - distanceFromCenter)/(distanceFromCenterMax - distanceFromCenterMin + epsilon);
    }

    protected double normalizeImagesRating(double imagesRating){
        setImagesRatingMax(imagesRating);
        setImagesRatingMin(imagesRating);

        return (imagesRating - imagesRatingMin)/(imagesRatingMax - imagesRatingMin + epsilon);
    }

    protected double normalizeApartmentSize(double apartmentSize){
        setApartmentSizeMax(apartmentSize);
        setApartmentSizeMin(apartmentSize);

        return (apartmentSize - apartmentSizeMin)/(apartmentSizeMax - apartmentSizeMin + epsilon);
    }

    protected double normalizeRoomsQuantity(int numberOfRooms){
        setRoomsQuantityMax(numberOfRooms);
        setRoomsQuantityMin(numberOfRooms);

        return (numberOfRooms - roomsQuantityMin)/(roomsQuantityMax - roomsQuantityMin + epsilon);
    }

    public void setPriceRentalMax(double priceRentalMax) {
        this.priceRentalMax = Math.max(this.priceRentalMax, priceRentalMax);
    }

    public void setPriceRentalMin(double priceRentalMin) {
        this.priceRentalMin = Math.min(this.priceSellMin, priceRentalMin);
    }

    public void setPriceSellMax(double priceSellMax) {
        this.priceSellMax = Math.max(this.priceSellMax, priceSellMax);
    }

    public void setPriceSellMin(double priceSellMin) {
        this.priceSellMin = Math.min(this.priceSellMin, priceSellMin);
    }

    public void setDistanceFromCenterMax(double distanceFromCenterMax) {
        this.distanceFromCenterMax = Math.max(this.distanceFromCenterMax, distanceFromCenterMax);
    }

    public void setDistanceFromCenterMin(double distanceFromCenterMin) {
        this.distanceFromCenterMin = Math.min(this.distanceFromCenterMin, distanceFromCenterMin);
    }

    public void setImagesRatingMax(double imagesRatingMax) {
        this.imagesRatingMax = Math.max(this.imagesRatingMax, imagesRatingMax);
    }

    public void setImagesRatingMin(double imagesRatingMin) {
        this.imagesRatingMin = Math.min(this.imagesRatingMin, imagesRatingMin);
    }

    public void setApartmentSizeMax(double apartmentSizeMax) {
        this.apartmentSizeMax = Math.max(this.apartmentSizeMax, apartmentSizeMax);
    }

    public void setApartmentSizeMin(double apartmentSizeMin) {
        this.apartmentSizeMin = Math.min(this.apartmentSizeMin, apartmentSizeMin);
    }

    public void setRoomsQuantityMax(int roomsQuantityMax) {
        this.roomsQuantityMax = Math.max(this.roomsQuantityMax, roomsQuantityMax);
    }

    public void setRoomsQuantityMin(int roomsQuantityMin) {
        this.roomsQuantityMin = Math.min(this.roomsQuantityMin, roomsQuantityMin);
    }

    public double getPriceRentalMax() {
        return priceRentalMax;
    }

    public double getPriceRentalMin() {
        return priceRentalMin;
    }

    public double getPriceSellMax() {
        return priceSellMax;
    }

    public double getPriceSellMin() {
        return priceSellMin;
    }

    public double getDistanceFromCenterMax() {
        return distanceFromCenterMax;
    }

    public double getDistanceFromCenterMin() {
        return distanceFromCenterMin;
    }

    public double getImagesRatingMax() {
        return imagesRatingMax;
    }

    public double getImagesRatingMin() {
        return imagesRatingMin;
    }

    public double getApartmentSizeMax() {
        return apartmentSizeMax;
    }

    public double getApartmentSizeMin() {
        return apartmentSizeMin;
    }

    public double getRoomsQuantityMax() {
        return roomsQuantityMax;
    }

    public double getRoomsQuantityMin() {
        return roomsQuantityMin;
    }
}
