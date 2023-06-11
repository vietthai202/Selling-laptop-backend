package com.fpt.swp391.service;
import com.fpt.swp391.model.Slide;
import java.util.List;
public interface SlideService {
    Slide createSlide(Slide slide);

    Slide findById(Long id);

    List<Slide> listAllSlide();

    boolean deleteSlide(Long id);

    Slide updateSlide(Long id, Slide slide);
}
