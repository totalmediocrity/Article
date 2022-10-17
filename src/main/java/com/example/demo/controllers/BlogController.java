package com.example.demo.controllers;

import com.example.demo.models.Comment;
import com.example.demo.models.Profile;
import com.example.demo.repo.CommentRepository;
import com.example.demo.repo.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController  {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CommentRepository commentRepository;
    @GetMapping("/")
    public String blogMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

   @GetMapping("/blog/add")
    public String blogAdd(Model model)
    {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, anons, full_text);
       postRepository.save(post);
        return "redirect:/";
    }

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
 //       List<Post> result = postRepository.findByTitle(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }

    @GetMapping("/blog/profile")
    public String blogProfile(Model model) {
        return "blog-profile";
    }

    @PostMapping("/blog/profile")
    public String blogPostProfile(@RequestParam String nik,
                                  @RequestParam String surname,
                                  @RequestParam String name,
                                  @RequestParam String patron,
                                  @RequestParam String yourself,
                                  @RequestParam int age, Model model) {
        Profile profile = new Profile(nik, surname, name, patron, yourself, age);
        profileRepository.save(profile);
        model.addAttribute("nik", nik);
        return "blog-profile";
    }

    @GetMapping("/blog/comment")
    public String blogComm(Model model) {
        return "blog-comment";
    }

    @PostMapping("/blog/comment")
    public String blogCommProf(@RequestParam String header,
                               @RequestParam String tc,
                               @RequestParam String writer,
                               @RequestParam String dOfp,
                               @RequestParam String app,
                                  Model model) {
        Comment comment = new Comment(header, tc, writer,dOfp,app);
        commentRepository.save(comment);
        model.addAttribute("tc", tc);
        return "blog-comment";
    }


}
