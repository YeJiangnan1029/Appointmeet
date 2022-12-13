package zju.apd.yjn.appointmeet.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;
import zju.apd.yjn.appointmeet.service.ex.ServiceException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired(required = false)
    private IUserService userService;

    @Test
    public void register(){
        try {
            User user = new User("yjn", "123456", "tttt");
            Integer userId = userService.register(user);
            System.out.println(userId);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void loginById(){
        try {
            Integer id = 2;
            String password = "123456";
            User userBriefInfo = userService.loginById(id, password);
            System.out.println(userBriefInfo);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void loginByEmail(){
        try {
            String email = "dsadas";
            String password = "123456";
            User userBriefInfo = userService.loginByEmail(email, password);
            System.out.println(userBriefInfo);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getUserInfoById(){
        try {
            Integer id = 2;
            User userBriefInfo = userService.getUserInfoById(id, false);
            User userDetailedInfo = userService.getUserInfoById(id, true);
            System.out.println(userBriefInfo);
            System.out.println(userDetailedInfo);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void modifyPassword(){
        try {
            Integer id = 2;
            userService.modifyPassword(id, "12345", "123456");
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void modifyName(){
        try {
            Integer id = 1;
            userService.modifyName(id, "test");
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void modifyEmail(){
        try {
            Integer id = 1;
            userService.modifyEmail(id, "email.com");
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void modifyAvatar(){
        try {
            Integer id = 2;
            userService.modifyAvatar(id, "aaa.png");
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void createMeeting(){
        try {
            Meeting meeting = new Meeting();
            meeting.setId(3);
            meeting.setSubject("future");
//            meeting.setPrivate(false);
            Date date = new Date();
            date.setYear(2000-1900);
            meeting.setStartTime(date);
            meeting.setOrganizerId(2);
            meeting.setPrivate(true);
            meeting.setJoinPassword("123456");
            userService.createMeeting(meeting);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void dismissMeeting(){
        try {
            Integer meetingId = 2;
            userService.dismissMeeting(meetingId);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void joinMeeting(){
        try {
            Integer userId = 4;
            String password = "123456";
            Integer meetingId = 17;
            userService.joinMeeting(userId, password, meetingId);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void quitMeeting(){
        try {
            Integer userId = 1;
            Integer meetingId = 10;
            userService.quitMeeting(userId, meetingId);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findParticipatedMeetingsByUserUserId(){
        try {
            Integer id = 2;
            List<Meeting>[] meetingTotalLists = userService.findParticipatedMeetingsByUserId(id);
            List<Meeting> meetingList = meetingTotalLists[0];
            List<Meeting> meetingExpiredList = meetingTotalLists[1];
            System.out.println("Meetings number: " + meetingList.size());
            for (Meeting m: meetingList){
                System.out.println(m);
            }
            System.out.println("Expired meetings number: " + meetingExpiredList.size());
            for (Meeting m: meetingExpiredList){
                System.out.println(m);
            }

        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getMeetingDetailedInfoByMeetingId(){
        try {
            Integer meetingId = 2;
            Meeting meeting = userService.getMeetingDetailedInfoByMeetingId(meetingId);
            System.out.println(meeting);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getParticipatorsByMeetingId(){
        try {
            Integer meetingId = 10;
            List<User> userList = userService.getParticipatorsByMeetingId(meetingId);
            System.out.println(userList.size());
            for (User user: userList){
                System.out.println(user);
            }

        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

}
