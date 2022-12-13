package zju.apd.yjn.appointmeet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;
import zju.apd.yjn.appointmeet.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private UserServiceImpl userService;

    private void setModelFromSesstion(Model model, HttpSession session){
        model.addAttribute("userBriefInfo", session.getAttribute("sessionUserInfo"));
        model.addAttribute("meetingDetailedInfo", session.getAttribute("meetingDetailedInfo"));
    }

    @RequestMapping("personal_info")
    public String personalInfo(Model model, HttpSession session){
        setModelFromSesstion(model, session);
        User user = (User)session.getAttribute("sessionUserInfo");
        if (user != null){
            Integer id = user.getId();
            User userDetailedInfo = userService.getUserInfoById(id, true);
            model.addAttribute("userDetailedInfo", userDetailedInfo);
        }
        return "personal_info";
    }

    @RequestMapping("create_meeting")
    public String createMeeting(Model model, HttpSession session){
        setModelFromSesstion(model, session);

        return "create_meeting";
    }

    @RequestMapping("my_meetings")
    public String getMeetings(Model model, HttpSession session){
        setModelFromSesstion(model, session);

        User userInfo = (User)session.getAttribute("sessionUserInfo");
        List<Meeting>[] meetingsList = userService.findParticipatedMeetingsByUserId(userInfo.getId());

        model.addAttribute("meetingList", (meetingsList[0].size()!=0)?meetingsList[0]:null);
        model.addAttribute("meetingExpiredList", (meetingsList[1].size()!=0)?meetingsList[1]:null);

        return "my_meetings";
    }

    @RequestMapping("search_meeting")
    public String searchMeeting(Model model, HttpSession session){
        setModelFromSesstion(model, session);

        return "search_meeting";
    }

    @RequestMapping("meeting_detail")
    public String meetingDetail(Model model, HttpSession session){
        setModelFromSesstion(model, session);

        return "meeting_detail";
    }

    @RequestMapping("modify_info")
    public String modifyInfo(Model model, HttpSession session){
        setModelFromSesstion(model, session);

        return "modify_info";
    }
}
