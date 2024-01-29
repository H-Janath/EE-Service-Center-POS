package dao.custom;

import dao.CrudDao;
import entity.Users;

public interface UserDao extends CrudDao<Users> {
    public boolean updatePassword(String email, String password);
    public Users search(String email);
}
