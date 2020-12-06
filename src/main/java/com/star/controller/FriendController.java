package com.star.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.entity.FriendLink;
import com.star.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class FriendController {

    @Autowired
    private FriendLinkService friendLinkService;

    //查询所有友链
    @GetMapping("/friendlinks")
    public String friend(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,10);
        List<FriendLink> friendLinkList = friendLinkService.listFriendLink();
        PageInfo<FriendLink> pageInfo = new PageInfo<>(friendLinkList);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/friendlinks";
    }


    //跳转友链新增界面
    @GetMapping("/friendlinks/input")
    public String input(Model model){
        model.addAttribute("friendlink",new FriendLink());
        return "admin/friendlinks-input";
    }


    //新增友链
    @PostMapping("/friendlinks")
    public String post(FriendLink friendLink, BindingResult result, RedirectAttributes attributes){
        FriendLink type1 = friendLinkService.getFriendLinkByBlogaddress(friendLink.getBlogaddress());
        if(type1 != null){
            attributes.addFlashAttribute("message","不能添加相同网址");
            return "redirect:/admin/friendlinks/input";
        }
        if(result.hasErrors()){
            return "admin/friendlinks-input";
        }
        friendLink.setCreateTime(new Date());

        int f = friendLinkService.saveFriendLink(friendLink);
        if(f == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/friendlinks";
    }


    //跳转友链修改界面
    @GetMapping("friendlinks/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("friendlink",friendLinkService.getFriendLink(id));
        return "admin/friendlinks-input";
    }
    //友链修改
    @PostMapping("/friendlinks/{id}")
    public String editPost(FriendLink friendLink,RedirectAttributes attributes){
        int t = friendLinkService.updateFriendLink(friendLink);
        if(t == 0){
            attributes.addFlashAttribute("message","编辑成功");
        }else{
            attributes.addFlashAttribute("message","编辑失败");
        }
        return "redirect:/admin/friendlinks";
    }
    //友链删除
    @GetMapping("/friendlinks/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        friendLinkService.deleteFriendLink(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/friendlinks";
    }

}
