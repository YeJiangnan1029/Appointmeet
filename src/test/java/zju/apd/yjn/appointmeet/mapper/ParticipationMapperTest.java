package zju.apd.yjn.appointmeet.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ParticipationMapperTest {
    @Autowired(required=false)
    private ParticipationMapper participationMapper;

    @Test
    public void addParticipationRecord(){
        Integer userId = 2;
        Integer meetingId = 4;
        Integer ret = participationMapper.addParticipationRecord(userId, meetingId, new Date());
        System.out.println(ret);
    }

    @Test
    public void deleteParticipationRecordByMeetingId(){
        Integer meetingId = 1;
        Integer ret = participationMapper.deleteParticipationRecordByMeetingId(meetingId);
        System.out.println(ret);
    }

    @Test
    public void getNumOfParticipationByUserIdAndMeetingId(){
        Integer userId = 2;
        Integer meetingId = 8;
        Integer ret = participationMapper.getNumOfParticipationByUserIdAndMeetingId(userId, meetingId);
        System.out.println(ret);
    }

    @Test
    public void deleteParticipationRecord(){
        Integer userId = 3;
        Integer meetingId = 8;
        Integer ret = participationMapper.deleteParticipationRecord(userId, meetingId);
        System.out.println(ret);
    }

    @Test
    public void findParticipatedMeetingsByUserId(){
        Integer userId = 2;
        List<Meeting> retList = participationMapper.findParticipatedMeetingsByUserId(userId);
        System.out.println(retList.size());
        for (Meeting meeting: retList){
            System.out.println(meeting);
        }
    }

    @Test
    public void findParticipatorsByMeetingId(){
        Integer meetingId = 1;
        List<User> retList = participationMapper.findParticipatorsByMeetingId(meetingId);
        System.out.println(retList.size());
        for (User user: retList){
            System.out.println(user);
        }
    }
}
