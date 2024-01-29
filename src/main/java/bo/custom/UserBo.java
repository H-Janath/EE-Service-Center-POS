package bo.custom;

import bo.SuperBo;
import dto.UsersDto;
import javafx.collections.ObservableList;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserBo<T> extends SuperBo {
    public UsersDto searchUser(String email);
    public boolean saveUser(UsersDto usersDto) throws NoSuchAlgorithmException;
    public ObservableList<String> getUsers();
    public boolean checkValidityofCredintial(String usernme,String password) throws NoSuchAlgorithmException;
    public boolean updatePassword(String email,String password) throws NoSuchAlgorithmException;
    public List<UsersDto> allUsers();
    public boolean deleteUser(String userId);
}
