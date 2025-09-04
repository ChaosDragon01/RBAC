import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

 


public class rolemanage extends storage{

        

    private Map<Integer, String> roles = new HashMap<>(){};

    
    void rolecreate(int roleId, String roleName) {
        if (roleId <= 0 || roleName == null || roleName.isEmpty()) {
            throw new IllegalArgumentException("Invalid role ID or name");
        } else if (roles.containsKey(roleId)) {
            throw new IllegalArgumentException("Role ID already exists");
        } else {
           roles.put(roleId, roleName);
        }

    }

    boolean roleEX; 
    boolean roleget(int roleId) {
        if (roleId <= 0) {
            throw new IllegalArgumentException("Invalid role ID");
        }
        else if (!roles.containsKey(roleId)) {
            roleEX = false;
            return roleEX;
        } else if (roles.get(roleId) == null || roles.get(roleId).isEmpty()) {
            throw new NoSuchElementException("Role name is empty");
        } else  {
        return roles.get(roleId);
        }
    } // I am so fuckiing confused if I should keep this or refractor the code because I think Keeping too much files is not good for the project
    void roledelete(int roleId) {
        if (roleId <= 0) {
            throw new IllegalArgumentException("Invalid role ID");
        } else if (!roles.containsKey(roleId)) {
            throw new NoSuchElementException("Role ID does not exist");
        } else {
            roles.remove(roleId);
        }
    }   
}


