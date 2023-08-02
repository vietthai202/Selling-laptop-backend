package com.fpt.swp391.service;

import com.fpt.swp391.dto.ReviewDto;
import com.fpt.swp391.dto.ReviewRequestDto;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.model.Review;
import com.fpt.swp391.model.User;
import com.fpt.swp391.repository.LaptopRepository;
import com.fpt.swp391.repository.ReviewRepository;
import com.fpt.swp391.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final LaptopRepository laptopRepository;

    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             LaptopRepository laptopRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.laptopRepository = laptopRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Review findById(Long id) {
        try {
            Review rw = reviewRepository.findById(id).orElse(null);
            if (rw != null) {
                return rw;
            }
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public ReviewDto createReview(ReviewDto review) {
        try {
            Review rw = new Review();
            rw.setTitle(review.getTitle());
            rw.setContent(review.getContent());
            rw.setEnable(review.isEnable());
            rw.setRating(review.getRating());
            Laptop lt = laptopRepository.findById(review.getLaptop_id()).orElse(new Laptop());
            rw.setLaptop(lt);
            User user = userRepository.findById(review.getUser_id()).orElse(new User());
            rw.setUser(user);
            reviewRepository.save(rw);
            ReviewDto dto = convertReviewDto(rw);
            return dto;
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto review) {
        try {
            Review rw = reviewRepository.findById(id).orElse(null);
            if (rw != null) {
                rw.setTitle(review.getTitle());
                rw.setContent(review.getContent());
                rw.setEnable(review.isEnable());
                rw.setRating(review.getRating());
                Laptop lt = laptopRepository.findById(review.getLaptop_id()).orElse(new Laptop());
                rw.setLaptop(lt);
                User user = userRepository.findById(review.getUser_id()).orElse(new User());
                rw.setUser(user);
                reviewRepository.save(rw);
                return convertReviewDto(rw);
            }
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public boolean deleteReview(Long id) {
        try {
            Review rw = reviewRepository.findById(id).orElse(null);
            if (rw != null) {
                reviewRepository.delete(rw);
                return true;
            }
        } catch (Exception e){

        }
        return false;
    }

    @Override
    public List<ReviewDto> listAllReview() {
        try {
            List<Review> list = reviewRepository.findAll();
            List<ReviewDto> listDto = new ArrayList<>();
            for (Review rw : list) {
                ReviewDto dto = new ReviewDto();
                dto.setId(rw.getId());
                dto.setTitle(rw.getTitle());
                dto.setContent(rw.getContent());
                dto.setEnable(rw.isEnable());
                dto.setRating(rw.getRating());
                dto.setLaptop_id(rw.getLaptop().getId());
                dto.setUser_id(rw.getUser().getId());
                listDto.add(dto);
            }
            return listDto;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public List<ReviewDto> listReviewByLaptopId(Long id) {
        try{
            Laptop laptop = laptopRepository.findById(id).orElse(null);
            List<ReviewDto> listDto = new ArrayList<>();
            if(laptop != null){
                List<Review> rw = reviewRepository.findReviewByLaptopId(laptop.getId());
                for(Review review : rw){
                    if(review.getId() != null){
                        ReviewDto dto = new ReviewDto();
                        dto.setTitle(review.getTitle());
                        dto.setContent(review.getContent());
                        dto.setEnable(review.isEnable());
                        dto.setRating(review.getRating());
                        dto.setLaptop_id(review.getLaptop().getId());
                        dto.setUser_id(review.getUser().getId());
                        listDto.add(dto);
                    }
                }
            }
            return listDto;
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public List<ReviewDto> listReviewByUserId(Long id) {
        try{
            User user = userRepository.findById(id).orElse(null);
            List<ReviewDto> listDto = new ArrayList<>();
            if(user != null){
                List<Review> rw = reviewRepository.findReviewByUserId(user.getId());
                for(Review review : rw){
                    if(review.getId() != null){
                        ReviewDto dto = new ReviewDto();
                        dto.setTitle(review.getTitle());
                        dto.setContent(review.getContent());
                        dto.setEnable(review.isEnable());
                        dto.setRating(review.getRating());
                        dto.setLaptop_id(review.getLaptop().getId());
                        dto.setUser_id(review.getUser().getId());
                        listDto.add(dto);
                    }
                }
            }
            return listDto;
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public List<ReviewRequestDto> getAll(Long laptopId) {
        Laptop lt = laptopRepository.findById(laptopId).orElse(null);
        Set<Review> reviews = lt.getReviews();
        List<ReviewRequestDto> listDto = new ArrayList<>();
        for(Review rw : reviews){
            ReviewRequestDto dto = convertToDto(rw);
            listDto.add(dto);
        }
        return listDto;
    }

    private ReviewDto convertReviewDto (Review review){
        ReviewDto rwdto = new ReviewDto();
        rwdto.setId(review.getId());
        rwdto.setTitle(review.getTitle());
        rwdto.setContent(review.getContent());
        rwdto.setEnable(review.isEnable());
        rwdto.setRating(review.getRating());
        rwdto.setLaptop_id(review.getLaptop().getId());
        rwdto.setUser_id(review.getUser().getId());
        return rwdto;
    }

    private ReviewRequestDto convertToDto(Review review){
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setLaptop_id(review.getLaptop().getId());
        dto.setAverageStar(review.getRating());
        return dto;
    }
}
