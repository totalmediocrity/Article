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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Iterable<Profile> prof = profileRepository.findAll();
        model.addAttribute("profile", prof);
        return "blog-profile";
    }

    @PostMapping("/blog/profile")
    public String blogPostProfile(@RequestParam String nik,
                                  @RequestParam String name,
                                  @RequestParam String yourself,
                                  @RequestParam char pol,
                                  @RequestParam int age,
                                  @RequestParam Date dOfs,
                                  Model model) {
        Profile profile = new Profile(nik, name,yourself, pol, age,dOfs);
        profileRepository.save(profile);
        return "blog-profile";
    }

    @GetMapping("/blog/comment")
    public String blogComm(Model model) {

        Iterable<Comment> comment = commentRepository.findAll();
        model.addAttribute("comment", comment);
        return "blog-comment";
    }

    @PostMapping("/blog/comment")
    public String blogCommProf(@RequestParam String header,
                               @RequestParam String tc,
                               @RequestParam int writer,
                               @RequestParam Date dOfp,
                               @RequestParam char app,
                               Model model) {
        Comment comment = new Comment(header, tc, writer,dOfp,app);
        commentRepository.save(comment);
        return "blog-comment";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id,Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
         ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
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
        ArrayList<Comment> res = new ArrayList<>();
        comment.ifPresent(res::add);
        model.addAttribute("comment",res);
        return "blog-edit-comm";
    }
    @PostMapping("/blog/comment/{id}/edit")
    public String blogCommUpdate(@PathVariable("id") long id,
                                 @RequestParam String header,
                                 @RequestParam String tc,
                                 @RequestParam int writer,
                                 @RequestParam Date dOfp,
                                 @RequestParam char app,
                                 Model model)
    {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setHeader(header);
        comment.setTc(tc);
        comment.setWriter(writer);
        comment.setdOfp(dOfp);
        comment.setApp(app);
        commentRepository.save(comment);
        return "redirect:/";
    }

    @PostMapping ("/blog/comment/{id}/remove")
    public String blogCommDelete(@PathVariable ("id") long id, Model model)
    {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);

        return "redirect:/";
    }

    @GetMapping("/blog/profile/{id}")
    public String blogProfDetails(@PathVariable(value = "id") long id,Model model)
    {
        Optional<Profile> profile = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        profile.ifPresent(res::add);
        model.addAttribute("profile",res);
        if(!profileRepository.existsById(id)){
            return "redirect:/";
        }
        return "blog-details-profile";
    }
    @GetMapping("/blog/profile/{id}/edit")
    public String blogProfEdit(@PathVariable(value = "id") long id,Model model)
    {
        if(!profileRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Profile> profile = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        profile.ifPresent(res::add);
        model.addAttribute("profile",res);
        /*model.addAttribute("profile", profile.get());*/
        return "blog-edit-profile";
    }


  /*  @PostMapping("/blog/Profile/{id}/edit")
    public String blogPostUpdateProfile(@ModelAttribute ("profile") @Valid Profile profile,
                                        BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){
            return "blog-editProfile";
        }
        profileRepository.save(profile);
        return "redirect:/";
    }*/

   @PostMapping("/blog/profile/{id}/edit")
    public String blogProfUpdate(@PathVariable("id") long id,
                                 @RequestParam String nik,
                                 @RequestParam String name,
                                 @RequestParam String yourself,
                                 @RequestParam char pol,
                                 @RequestParam int age,
                                 @RequestParam Date dOfs,
                                 Model model)
    {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profile.setNik(nik);
        profile.setName(name);
        profile.setYourself(yourself);
        profile.setPol(pol);
        profile.setAge(age);
        profile.setdOfs(dOfs);
        profileRepository.save(profile);
        return "redirect:/";
    }
    @PostMapping ("/blog/profile/{id}/remove")
    public String blogProfDelete(@PathVariable ("id") long id, Model model)
    {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profileRepository.delete(profile);

        return "redirect:/";
    }




}
