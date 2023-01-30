package com.example.demo.controllers;

import com.example.demo.models.Comment;
import com.example.demo.models.Profile;
import com.example.demo.repo.CommentRepository;
import com.example.demo.repo.ProfileRepository;
import com.example.demo.repo.UserRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.example.demo.models.*;
import com.example.demo.repo.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class BlogController  {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String blogMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Post post, Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        model.addAttribute("profile", users);

        return "blog-add";
    }
    public String crntUsNm()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }

        return null;
    }


    @PostMapping("/blog/add")
    public String blogPostAdd(@ModelAttribute ("post") @Valid Post postValid, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("user", users);
            model.addAttribute("profile", users);
            return "blog-add";
        }
        Post post = new Post(postValid.getTitle(), postValid.getAnons(), postValid.getFull_text(), postValid.getUser());
        postRepository.save(post);
        return "redirect:/";
    }



/*    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }*/

  /*  @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam (defaultValue = "") String title, Model model)
    {
        List<Post> results = postRepository.findByTitleContains(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }
*/
/*
    @PostMapping("/blog/filter/results")
    public String blogResults(@RequestParam (defaultValue = "") String title, Model model)
    {
        List<Post> results = postRepository.findByTitle(title);
        model.addAttribute("results", results);
 //       List<Post> result = postRepository.findByTitle(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }
*/


//    @GetMapping("/blog/filter")
//    public String blogFilter(@RequestParam(defaultValue = "") String title,
//                             @RequestParam(required = false) boolean accurate_search,
//                             Model model)
//    {
//        if (!title.equals("")) {
//            List<Post> results = accurate_search ? postRepository.findByTitle(title) : postRepository.findByTitleContains(title);
//            model.addAttribute("results", results);
//        }
//
//        model.addAttribute("title", title);
//        model.addAttribute("accurate_search", accurate_search);
//
//        return "blog-filter";
//    }


    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> results = postRepository.findByTitleContains(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }

    @PostMapping("/blog/filter/results")
    public String blogResults(@RequestParam String title, Model model)
    {
        List<Post> results = postRepository.findByTitle(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }

    @GetMapping("/blog/profile")
    public String blogProfileAdd(Model model)
    {
        User user = userRepository.findByUsername(crntUsNm());

        model.addAttribute("user", user);

        return "blog-profile";
    }

    @PostMapping("/blog/profile")
    public String blogPostProfile(@ModelAttribute ("profile") @Valid Profile profile,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "blog-profile";
        }

        return "redirect:/";

    }

    @GetMapping("/blog/comment")
    public String blogComm(Model model) {

        Iterable<Comment> comment = commentRepository.findAll();
        model.addAttribute("comment", comment);
        return "blog-comment";
    }

    @GetMapping("/blog/add/comm")
    public String blogCommentsAdd(Comment comment, Model model)
    {
        Iterable<Comment> comms = commentRepository.findAll();
        model.addAttribute("comms", comms);

        return "blog-add-comm";
    }
    @PostMapping("/blog/add/comm")
    public String blogCommentsAdd(@ModelAttribute ("comment") @Valid Comment comment,
                                  BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "blog-add-comm";
        }
        commentRepository.save(comment);

        return "redirect:/blog/comment";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        return "blog-details";
    }


    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("profile", users);
        Optional<Post> post = postRepository.findById(id);
        model.addAttribute("post", post.get());

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@ModelAttribute ("post") @Valid Post post,
                                 Model model, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
//            Iterable<User> users = userRepository.findAll();
//            model.addAttribute("profile", users);
//            model.addAttribute("users",users);
            return "blog-edit";
        }
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping ("/blog/{id}/remove")
    public String blogBlogDelete(@PathVariable ("id") long id, Model model)
   {
       Post post = postRepository.findById(id).orElseThrow();
       postRepository.delete(post);

       return "redirect:/";
   }

    @GetMapping("/blog/comment/{id}")
    public String blogCommDetails(@PathVariable(value = "id") long id,Model model)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        ArrayList<Comment> res = new ArrayList<>();
        comment.ifPresent(res::add);
        model.addAttribute("comment",res);
        if(!commentRepository.existsById(id)){
            return "redirect:/";
        }
        return "blog-details-comm";
    }
    @GetMapping("/blog/comment/{id}/edit")
    public String blogCommEdit(@PathVariable(value = "id") long id,Model model)
    {
        if(!commentRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Comment> comment = commentRepository.findById(id);
       /* ArrayList<Comment> res = new ArrayList<>();
        comment.ifPresent(res::add);*/
        model.addAttribute("comment",comment.get ());
        return "blog-edit-comm";
    }
    @PostMapping("/blog/comment/{id}/edit")
    public String blogCommUpdate(@ModelAttribute ("comment") @Valid Comment comment,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "blog-edit-comm";
        }

        commentRepository.save(comment);
        return "redirect:/blog/comment";
    }

    @PostMapping ("/blog/comment/{id}/remove")
    public String blogCommDelete(@PathVariable ("id") long id, Model model)
    {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);

        return "redirect:/blog/comment";
    }
    @GetMapping("/blog/profile/{id}/edit")
    public String blogEditProfiles(@PathVariable(value = "id") long id, Model model)
    {
        if(!userRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());

        return "blog-edit-profile";
    }

    @PostMapping("blog/profile/{id}/edit")
    public String blogProfilesUpdate(@ModelAttribute ("user") @Valid User user,
                                     BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "blog-edit-profile";
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/blog/filter/comm")
    public String blogFilterComments(@RequestParam(defaultValue = "") String header,
                                     @RequestParam(required = false) boolean accurate_search,
                                     Model model)
    {
        if (!header.equals("")) {
            List<Comment> results = accurate_search ? commentRepository.findByHeader(header) : commentRepository.findByHeaderContains(header);
            model.addAttribute("results", results);
        }

        model.addAttribute("header", header);
        model.addAttribute("accurate_search", accurate_search);

        return "blog-filter-comm";
    }


    @GetMapping("/blog/filter/profile")
    public String blogFilterProfile(@RequestParam(defaultValue = "") String name,
                                     @RequestParam(required = false) boolean accurate_search,
                                     Model model)
    {
        if (!name.equals("")) {
            List<Profile> result = accurate_search ? profileRepository.findByName(name) : profileRepository.findByNameContains(name);
            model.addAttribute("result", result);
        }

        model.addAttribute("name", name);
        model.addAttribute("accurate_search", accurate_search);

        return "blog-filter-profile";
    }


       @GetMapping("/blog/comm/addiction")
    public String blogCardAdd(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("profile", users);
        Iterable<Comment> comments = commentRepository.findAll();
        model.addAttribute("comment", comments);
        model.addAttribute("profile", users);
        return "blog-comm-addiction";
    }

    @PostMapping("/blog/comm/addiction")
    public String blogPostAddiction(@RequestParam String user, @RequestParam String comment, Model model)
    {
        User user2 = userRepository.findByUsername(user);
        Comment comment2 = commentRepository.findByTc(comment);
        user2.getComment().add(comment2);
        comment2.getUsers().add(user2);
        userRepository.save(user2);
        commentRepository.save(comment2);
        return "redirect:/";
    }

 /*  @PostMapping("/blog/comment/addiction")
   public String blogCardAdd(@RequestParam long user_id, @RequestParam long comment_id, Model model)
   {
       User user1 = userRepository.findById(user_id).get();
       Comment comment1 = commentRepository.findById(comment_id).get();
       comment1.getUsers().add(user1);
       commentRepository.save(comment1);
       return "redirect:/";
   }*/

}
