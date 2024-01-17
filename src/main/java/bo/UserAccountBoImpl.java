package bo;

import Security.PassEncTech2;
import dao.impl.UserAccountDoImpl;
import dto.UsersDto;
import entity.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.NoSuchAlgorithmException;

public class UserAccountBoImpl {

    private UserAccountDoImpl userAccountDoimpl = new UserAccountDoImpl();
   public boolean saveUser(UsersDto usersDto) throws NoSuchAlgorithmException {
       byte[] bytes = PassEncTech2.getSHA(usersDto.getPassword());
       String encriptpassword = PassEncTech2.toHexString(bytes);
       return userAccountDoimpl.save(
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
}
