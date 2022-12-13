package zju.apd.yjn.appointmeet.mapper;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MeetingMapperTest {
    @Autowired(required=false)
    private MeetingMapper meetingMapper;

    @Test
    public void addMeeting(){
        Meeting meeting = new Meeting();
        meeting.setSubject("test 2");
        meeting.setOrganizerId(2);
        meeting.setDetail("detailed");
        meeting.setLocation("online");
        meeting.setStartTime(new Date());
        meeting.setDuration(7200);
        meeting.setPrivate(false);
//        meeting.setJoinPassword("123456");
        System.out.println(meeting);
        Integer ret = meetingMapper.addMeeting(meeting);
        System.out.println(ret);
    }

    @Test
    public void deleteMeeting(){
        Integer meetingId = 5;
        Integer ret = meetingMapper.deleteMeeting(meetingId);
        System.out.println(ret);
    }


    @Test
    public void findMeetingByMeetingId(){
        Integer id = 2;
        Meeting ret = meetingMapper.findMeetingByMeetingId(id);
        System.out.println(ret);
    }

    @Test
    public void findMeetingsByOrganizerId(){
        Integer id = 1;
        List<Meeting> retList = meetingMapper.findMeetingsByOrganizerId(id);
        System.out.println(retList.size());
        for (Meeting meeting: retList){
            System.out.println(meeting);
        }

    }

}
