/**
 * Copyrigth (C), 2019-2019
 * FileAName:   UserReviewController
 * Author:
 * Date:        2019-04-21 00:34
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
package com.dbproject.makeup.web.user;
import com.dbproject.makeup.po.Review;
import com.dbproject.makeup.po.User;
import com.dbproject.makeup.po.UserInfo;
import com.dbproject.makeup.service.ReviewService;
import com.dbproject.makeup.vo.ReviewQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user_reviews")


public class UserReviewController {

    private static final String INPUT = "reviews-input";
    private static final String LIST = "user_reviews";
    private static final String REDIRECT_LIST = "redirect:/user_reviews";


    private final ReviewService reviewService;

    @Autowired
    public UserReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
//    @Autowired
//    private TypeService typeService;
//    @Autowired
//    private TagService tagService;

    @GetMapping("/user_reviews")
    public String reviews(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                          ReviewQuery review, Model model) {
//        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", reviewService.listReview(pageable, review));
        return LIST;
    }

    @PostMapping("/user_reviews/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         ReviewQuery review, Model model) {
        model.addAttribute("page", reviewService.listReview(pageable, review));
        return "reviews :: reviewList";
    }


    @GetMapping("/user_reviews/input")
    public String input(Model model) {
//        setTypeAndTag(model);
        model.addAttribute("review", new Review());
        return INPUT;
    }


    private void setTypeAndTag(Model model) {
//        model.addAttribute("types", typeService.listType());
//        model.addAttribute("tags", tagService.listTag());
    }


//    @GetMapping("/reviews/{id}/input")
//    public String editInput(@PathVariable Long id, Model model) {
//        setTypeAndTag(model);
//        Review review = reviewService.getReview(id);
////        review.init();
//        model.addAttribute("review",review);
//        return INPUT;
//    }



    @PostMapping("/user_reviews")
    public String post(Review review, RedirectAttributes attributes, HttpSession session) {
        review.setWriteUser((User) session.getAttribute("user"));
//        review.setType(typeService.getType(review.getType().getId()));
//        review.setTags(tagService.listTag(review.getTagIds()));
        Review r = reviewService.saveReview(review);
//        if (review.getReviewId() == null) {
//            r =  reviewService.saveReview(review);
//        } else {
//            r = reviewService.updateReview(review.getReviewId(), review);
//        }

        if (r == null) {
            attributes.addFlashAttribute("message", "Failed, try again!");
        } else {
            attributes.addFlashAttribute("message", "Nailed it!");
        }
        return REDIRECT_LIST;
    }


//    @GetMapping("/reviews/{id}/delete")
//    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
//        reviewService.deleteReview(id);
//        attributes.addFlashAttribute("message", "Successfully deleted!");
//        return REDIRECT_LIST;
//    }

}

//}
