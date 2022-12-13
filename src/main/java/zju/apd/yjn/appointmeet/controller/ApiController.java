package zju.apd.yjn.appointmeet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zju.apd.yjn.appointmeet.controller.ex.*;
import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;
import zju.apd.yjn.appointmeet.service.ex.*;
import zju.apd.yjn.appointmeet.service.impl.UserServiceImpl;
import zju.apd.yjn.appointmeet.util.JsonResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@RestController
@RequestMapping("api")
public class ApiController extends BaseController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/register")
    public JsonResult<Integer> register(@ModelAttribute User user){
        Integer id = userService.register(user);
        user.setId(id);
        return new JsonResult<>(OK, id);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String ids, String password, HttpSession session){
        User data;
        // ids 可能是用户id也可能是用户邮箱 需要判断一下
        if (ids.matches("^[0-9]*$")){
            System.out.println("id " + ids);
            Integer id = Integer.parseInt(ids);
            data = userService.loginById(id, password);
        }
        // 其他情况就视为邮箱
        else {
            System.out.println("email " + ids);
            data = userService.loginByEmail(ids, password);
        }

        System.out.println(data);
        session.setAttribute("sessionUserInfo", data);
        System.out.println(session.getAttribute("sessionUserInfo"));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/get_user_info")
    public JsonResult<User> getUserInfo(Integer id){
        User userBriefInfo = userService.getUserInfoById(id, false);
        return new JsonResult<>(OK, userBriefInfo);
    }

    @RequestMapping("/search_meeting")
    public JsonResult<Void> searchMeeting(String ids, HttpSession session){
        Integer id = Integer.parseInt(ids);
        Meeting data = userService.getMeetingDetailedInfoByMeetingId(id);
        session.setAttribute("meetingDetailedInfo", data);
        System.out.println(data);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/create_meeting")
    public JsonResult<Void> createMeeting(String subject, String detail, String location, String startTime,
                                          Integer duration, String password, HttpSession session){
        Meeting meeting = new Meeting();
        meeting.setSubject(subject);
        meeting.setDetail(detail);
        meeting.setLocation(location);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date startTimeDate;

        try {
            startTimeDate = dateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            JsonResult<Void> ret = new JsonResult<>(7000);
            ret.setMessage("日期格式无法解析");
            return ret;
        }

        meeting.setStartTime(startTimeDate);
        meeting.setDuration(duration);
        meeting.setJoinPassword(password);
        System.out.println("create meeting: "+ meeting);
        User user = (User)session.getAttribute("sessionUserInfo");
        Integer organizerId = user.getId();
        meeting.setOrganizerId(organizerId);
        meeting.setPrivate(true);
        userService.createMeeting(meeting);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/join_meeting")
    public JsonResult<Void> joinMeeting(String password, Integer meetingId, HttpSession session){
        User userInfo = (User)session.getAttribute("sessionUserInfo");
        userService.joinMeeting(userInfo.getId(), password, meetingId);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/quit_meeting")
    public JsonResult<Void> quitMeeting(Integer meetingId, HttpSession session){
        User userInfo = (User)session.getAttribute("sessionUserInfo");
        userService.quitMeeting(userInfo.getId(), meetingId);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/modify_user_name")
    public JsonResult<Void> modifyUserName(String username, HttpSession session){
        User userInfo = (User)session.getAttribute("sessionUserInfo");
        Integer userId = userInfo.getId();
        userService.modifyName(userId, username);
        userInfo = userService.getUserInfoById(userId, false);
        session.setAttribute("sessionUserInfo", userInfo);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/modify_user_email")
    public JsonResult<Void> modifyUserEmail(String useremail, HttpSession session){
        User userInfo = (User)session.getAttribute("sessionUserInfo");
        Integer userId = userInfo.getId();
        userService.modifyEmail(userId, useremail);
        userInfo = userService.getUserInfoById(userId, false);
        session.setAttribute("sessionUserInfo", userInfo);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/modify_user_password")
    public JsonResult<Void> modifyUserPassword(String oldPassword, String newPassword, HttpSession session){
        User userInfo = (User)session.getAttribute("sessionUserInfo");
        Integer userId = userInfo.getId();
        userService.modifyPassword(userId, oldPassword, newPassword);

        return new JsonResult<>(OK);
    }

    // 文件最大大小
    public static final int AVATAR_FILE_MAX_SIZE = 10 * 1024 * 1024;
    // 限制文件类型
    public static final List<String> AVATAR_FILE_TYPE = new ArrayList<>();
    static {
        AVATAR_FILE_TYPE.add("image/jpg");
        AVATAR_FILE_TYPE.add("image/jpeg");
        AVATAR_FILE_TYPE.add("image/png");
        AVATAR_FILE_TYPE.add("image/bmp");
        AVATAR_FILE_TYPE.add("image/gif");
    }

    @RequestMapping("modify_avatar")
    public JsonResult<String> modifyAvatarByStuId(HttpSession session, MultipartFile avatarFile){
        // 文件限制
        if (avatarFile == null) {
            throw new FileEmptyException("文件为空");
        }
        if (avatarFile.getSize() > AVATAR_FILE_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }
        String avatarFileType = avatarFile.getContentType();
        if (!AVATAR_FILE_TYPE.contains(avatarFileType)) {
            throw new FileTypeException("文件类型不支持");
        }

        // 文件保存路径 /upload/avatar
//        String parent = session.getServletContext().getRealPath("/upload/avatar/");
        String parent = "/root/appointmeet/resource/avatar/";
//        String parent = "C:/Users/Asus/Desktop/temp/avatar/";
        File dir = new File(parent);
        if (!dir.exists()){
            dir.mkdirs();
        }
        System.out.println(dir);

        // 获取文件名 随机生成一个串代替
        String oriFilename = avatarFile.getOriginalFilename();
        int indexOfDot = oriFilename.lastIndexOf(".");
        String suffix = oriFilename.substring(indexOfDot);
        String newFilename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File des = new File(dir, newFilename); // 新建一个空文件

        // 写入文件
        try {
            avatarFile.transferTo(des);
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        // 文件名存入数据库
        User userInfo = (User)session.getAttribute("sessionUserInfo");
        Integer userId = userInfo.getId();
        String avatarFilePath = "/upload/avatar/" + newFilename;
        String oldAvatarString = userInfo.getAvatar();

        userService.modifyAvatar(userId, avatarFilePath);
        // 删除旧头像
        if (oldAvatarString != null){
            String oldAvatarFilename = oldAvatarString.substring(oldAvatarString.lastIndexOf("/") + 1);
            File oldAvatarFile = new File(dir, oldAvatarFilename);
            if (oldAvatarFile.exists()){
                try {
                    oldAvatarFile.delete();
                    System.out.println("Delete file: " + oldAvatarFile);
                }
                catch (SecurityException e){
                    System.out.println("Could not delete file:" + oldAvatarFile);
                }

            }
        }

        userInfo = userService.getUserInfoById(userId, false);
        session.setAttribute("sessionUserInfo", userInfo);

        return new JsonResult<>(OK, avatarFilePath);
    }



}
