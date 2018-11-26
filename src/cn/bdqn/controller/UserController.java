package cn.bdqn.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.bdqn.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.bdqn.pojo.User;


@RequestMapping(value="/user")
@Controller(value="userController")
public class UserController {

    @Resource(name="userService")
    private UserService userService;


    private Logger logger = Logger.getLogger(UserController.class);
    private List<User> userList= new ArrayList<User>();
    private ArrayList<User> queryUserList = new ArrayList<User>();
    public UserController(){
        logger.debug("UserController");
        try{
            userList.add(new User(1,"test001","测试用户001","1111111",1,new SimpleDateFormat("yyyy-MM-dd").parse("1986-12-10"),"13566669998","北京市朝阳区北苑",1,1,new Date(),1,new Date()));
            userList.add(new User(2,"zhaoyan","赵燕","2222222",1,new SimpleDateFormat("yyyy-MM-dd").parse("1984-11-10"),"18678786545","北京市海淀区成府路",1,1,new Date(),1,new Date()));
            userList.add(new User(3,"test003","测试用户003","3333333",1,new SimpleDateFormat("yyyy-MM-dd").parse("1980-11-11"),"13121334531","北京市通州北苑",1,1,new Date(),1,new Date()));
            userList.add(new User(4,"wanglin","王林","4444444",1,new SimpleDateFormat("yyyy-MM-dd").parse("1989-09-10"),"18965652364","北京市学院路",1,1,new Date(),1,new Date()));
            userList.add(new User(5,"zhaojing","赵静","5555555",1,new SimpleDateFormat("yyyy-MM-dd").parse("1981-08-01"),"13054784445","北京市广安门",1,1,new Date(),1,new Date()));
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    //没有查询条件的情况下，获取userList(公共查询)
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public String list(Model model){
        logger.info("无查询条件下，获取userList(公共查询)==== userList");
        model.addAttribute("queryUserList",userList);
        return "user/userlist";
    }
    //增加查询条件：userName
    @RequestMapping(value="/list",method=RequestMethod.POST)
    public String list(@RequestParam(value="userName",required=false) String userName,Model model){
        logger.info("查询条件：userName: " + userName +", 获取userList==== ");
        if(userName != null && !userName.equals("")){
            for(User user:userList){
                if(user.getUserName().indexOf(userName) != -1){
                    queryUserList.add(user);
                }
            }
            model.addAttribute("queryUserList",queryUserList);
        }else{
            model.addAttribute("queryUserList",userList);
        }
        return "user/userlist";
    }
    @RequestMapping(value="/login.html")
    public String login(){
        return "login";
    }
    @RequestMapping(value="/doLogin.html",method=RequestMethod.POST)
    public String doLogin(@RequestParam(value="userCode")String userCode,
                          @RequestParam(value="password")String password,Model model,
                          HttpSession session,HttpServletRequest request){

        User user = userService.login(userCode, password);
        if (user!=null) {
            session.setAttribute("userSession", user);
            return "frame";
        }
        ///model.addAttribute("error", "用户名或密码不正确！");
        request.setAttribute("error", "用户名或密码不正确！");
        return "forward:/WEB-INF/jsp/login.jsp";

    }
}
