package zju.apd.yjn.appointmeet.mapper;

import zju.apd.yjn.appointmeet.entity.User;

import java.util.Date;

public interface UserMapper {
    Integer addUser(User user);
    User findUserById(Integer id);
    User findUserByEmail(String email);
    Integer updateLastLoginDate(Integer id, Date lastLoginDate);
    Integer updatePasswordById(Integer id, String password);
    Integer updateNameById(Integer id, String name);
    Integer updateEmailById(Integer id, String email);
    Integer updateAvatarById(Integer id, String avatar);
}
