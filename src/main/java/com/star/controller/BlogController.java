package com.star.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.entity.*;
import com.star.service.BlogService;
import com.star.service.TypeService;
import com.star.service.UserService;
import com.star.util.MyX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    //跳转博客新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    //博客新增
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTypeId(blog.getType().getId());
        blog.setUserId(blog.getUser().getId());
        int b = blogService.saveBlog(blog);
        if(b == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/blogs";
    }

    //博客列表
    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<BlogQuery> list = blogService.getAllBlog();
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }

    //删除博客
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes,HttpSession session){
        if(MyX.authCode1!=null&& MyX.phone1!=null){
            attributes.addFlashAttribute("message","手机注册用户无删除权力!");
            return "redirect:/admin/blogs";
        }
        if(MyX.EmailUsername!=null){
            attributes.addFlashAttribute("message","邮箱注册用户无删除权力!");
            return "redirect:/admin/blogs";
        }
        Blog blog = blogService.selectBlogById(id);
        Long user_id = blogService.getUserIdById(id);
        User user = (User) session.getAttribute("user");
        System.out.println("------------:"+user.getId());
        System.out.println("-----------user_id"+user_id);
        if(user.getId().equals(user_id) ){
            blogService.deleteBlog(id);
            attributes.addFlashAttribute("message","删除成功");
            return "redirect:/admin/blogs";
        }else{
            attributes.addFlashAttribute("message","没有删除权限!");
            return "redirect:/admin/blogs";
        }
    }

    //跳转到编辑修改文章
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        ShowBlog blogById = blogService.getBlogById(id);
        List<Type> allType = typeService.getAllType();
        model.addAttribute("blog",blogById);
        model.addAttribute("types",allType);
        return "admin/blogs-input";
    }

    //编辑修改文章
    @PostMapping("blogs/{id}")
    public String editPost(ShowBlog showBlog, RedirectAttributes attributes,HttpSession session,@PathVariable Long id){
        if(MyX.authCode1!=null&& MyX.phone1!=null){
            attributes.addFlashAttribute("message","手机登陆用户无编辑权力!");
            return "redirect:/admin/blogs";
        }
        if(MyX.EmailUsername!=null){
            attributes.addFlashAttribute("message","邮箱注册用户无编辑权力!");
            return "redirect:/admin/blogs";
        }
        Blog blog = blogService.selectBlogById(id);
        Long user_id = blogService.getUserIdById(id);
        User user = (User) session.getAttribute("user");
        System.out.println("------------:"+user.getId());
        System.out.println("-----------user_id"+user_id);
        if(user.getId().equals(user_id)){
            int b = blogService.updateBlog(showBlog);
            if(b == 0){
                attributes.addFlashAttribute("message","编辑失败");
            }else{
                attributes.addFlashAttribute("message","编辑成功");
            }
        }else{
            attributes.addFlashAttribute("message","没有编辑权限!");
        }
        return "redirect:/admin/blogs";
    }

    //搜索博客管理列表
    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog,Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        List<BlogQuery> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageHelper.startPage(pageNum,10);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogBySearch);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs :: blogList";
    }
}
