package bo.custom.impl;

import Security.PassEncTech2;
import bo.custom.UserBo;
import dao.DaoFactory;
import dao.custom.impl.UserDaoImpl;
import dao.utill.DaoType;
import dto.UsersDto;
import entity.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UsersBoImpl implements UserBo<UsersDto> {

    private UserDaoImpl userDao = DaoFactory.getInstance().getDao(DaoType.USER);
    public UsersDto searchUser(String email){
        Users user = userDao.search(email);
        if(user!=null) {
            return new UsersDto(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole()
            );
        }
        return null;
    }
   public boolean saveUser(UsersDto usersDto) throws NoSuchAlgorithmException {
       byte[] bytes = PassEncTech2.getSHA(usersDto.getPassword());
       String encriptpassword = PassEncTech2.toHexString(bytes);
       return userDao.save(
               new Users(
                       usersDto.getEmail(),
                       encriptpassword,
                       usersDto.getRole()
               )
       );
   }

    public ObservableList<String> getUsers() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("ADMIN");
        list.add("EMPLOYEE");
        return list;
    }
    public boolean checkValidityofCredintial(String usernme,String password) throws NoSuchAlgorithmException {
            byte[] bytes = PassEncTech2.getSHA(password);
            String G_passwords = PassEncTech2.toHexString(bytes);
            UsersDto usersDto = searchUser(usernme);
            if(usersDto!=null) {
                if (usersDto.getPassword().equals(G_passwords) && usersDto.getEmail().equals(usernme)) {
                    return true;
                }
            }
             return false;

    }

    public boolean updatePassword(String email,String password) throws NoSuchAlgorithmException {
        byte[] bytes = PassEncTech2.getSHA(password);
        String G_passwords = PassEncTech2.toHexString(bytes);
        return userDao.updatePassword(email,G_passwords);
    }
    public List<UsersDto> allUsers(){
        List<Users> usersList = userDao.getAll();
        List<UsersDto> usersDtoList = new ArrayList<>();
        if(usersList!=null){
            for(Users users:usersList){
                usersDtoList.add(
                        new UsersDto(
                                users.getEmail(),
                                users.getPassword(),
                                users.getRole()
                        )
                );
            }
            return usersDtoList;
        }
        return null;
    }

    public boolean deleteUser(String userId) {
        return userDao.delete(userId);
    }
}
