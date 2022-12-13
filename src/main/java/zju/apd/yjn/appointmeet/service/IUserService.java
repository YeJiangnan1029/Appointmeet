package zju.apd.yjn.appointmeet.service;

import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;
import zju.apd.yjn.appointmeet.service.ex.ServiceException;

import java.util.List;

public interface IUserService {
    // 用户注册
    Integer register(User user);

    // 用户登录 两种方式
    User loginById(Integer id, String password);
    User loginByEmail(String email, String password);

    // 获取用户个人信息
    User getUserInfoById(Integer userId, Boolean isDetailed);

    // 修改用户个人信息
    void modifyPassword(Integer userId, String oldPassword, String newPassword);
    void modifyName(Integer userId, String newName);
    void modifyEmail(Integer userId, String newEmail);
    void modifyAvatar(Integer userId, String newAvatar);

    // 创建会议
    void createMeeting(Meeting meeting);

    // 解散会议
    void dismissMeeting(Integer meetingId);

    // 加入会议
    void joinMeeting(Integer userId, String password, Integer meetingId);

    // 退出会议
    void quitMeeting(Integer userId, Integer meetingId);

    // 查询会议
    /**
     * @param userId 用户id
     * @return 返回meetingList[]，meetingList[0]是未结束的meeting列表，meetingList[1]是已结束的meeting列表
     */
    List<Meeting>[] findParticipatedMeetingsByUserId(Integer userId);

    Meeting getMeetingDetailedInfoByMeetingId(Integer meetingId);

    // 查询参会者
    List<User> getParticipatorsByMeetingId(Integer meetingId);
}
