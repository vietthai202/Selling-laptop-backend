package com.fpt.swp391.service;
import com.fpt.swp391.model.Slide;
import com.fpt.swp391.repository.SlideRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SlideServiceImpl implements SlideService{
    private SlideRepository slideRepository = null;
    public SlideServiceImpl(SlideRepository slideRepositoryRepository) {
        this.slideRepository = slideRepositoryRepository;
    }
    @Override
    public Slide createrSlide(Slide slide) {
        slideRepository.save(slide);
        return slide;
    }
    @Override
    public Slide createSlide(Slide slide) {
        Slide sl = new Slide();
        sl.setName(slide.getName());
        sl.setImage(slide.getImage());
        sl.setSlug(slide.getSlug());
        slideRepository.save(sl);
        return sl;
    }
    @Override
    public Slide findById(Long id) {
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if (slideOptional.isPresent()) {
            Slide slide= slideOptional.get();
            return slide;
        }
        return null;
    }
    @Override
    public List<Slide> listAllSlide() {return slideRepository.findAll();
    }
    @Override
    public boolean deleteSlide(Long id) {
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if (slideOptional.isPresent()) {
            Slide slide = slideOptional.get();
            slideRepository.delete(slide);
            return true;
        }
        return false;
    }
    @Override
    public Slide updateSlide(Long id, Slide slide) {
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if(slideOptional.isPresent()){
            Slide sl= slideOptional.get();
            slide.setName(slide.getName());
            slide.setImage(slide.getImage());
            slide.setSlug(slide.getSlug());
            slideRepository.save(slide);
            return slide;
        }
        return null;
    }
}
