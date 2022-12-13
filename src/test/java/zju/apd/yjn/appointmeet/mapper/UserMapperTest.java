package zju.apd.yjn.appointmeet.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.apd.yjn.appointmeet.entity.User;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
    @Autowired(required=false)
    private UserMapper userMapper;

    @Test
    public void addUser(){
        User user = new User("yjn", "123456", "asdasd");
        Integer ret = userMapper.addUser(user);
        System.out.println(ret);
    }

    @Test
    public void findUserById(){
        Integer id = 1;
        User user = userMapper.findUserById(id);
        System.out.println(user);
    }

    @Test
    public void findUserByEmail(){
        String email = "asdasd";
        User user = userMapper.findUserByEmail(email);
        System.out.println(user);
    }

    @Test
    public void updateLastLoginDate(){
        Integer id = 1;
        Integer rows = userMapper.updateLastLoginDate(id, new Date());
        System.out.println(rows);
    }

    @Test
    public void updatePasswordById(){
        Integer id = 1;
        String newPassword = "654321";
        Integer rows = userMapper.updatePasswordById(id, newPassword);
        System.out.println(rows);
    }

    @Test
    public void updateNameById(){
        Integer id = 1;
        String newName = "njy";
        Integer rows = userMapper.updateNameById(id, newName);
        System.out.println(rows);
    }

    @Test
    public void updateEmailById(){
        Integer id = 1;
        String newEmail = "3190100625@zju.edu.cn";
        Integer rows = userMapper.updateEmailById(id, newEmail);
        System.out.println(rows);
    }

    @Test
    public void updateAvatarById(){
        Integer id = 3;
        String newAvatar = "/static/imgs/avatar/1.jpg";
        Integer rows = userMapper.updateAvatarById(id, newAvatar);
        System.out.println(rows);
    }



}
