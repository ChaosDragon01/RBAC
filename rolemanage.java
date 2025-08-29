import java.util.*;

 


abstract class rolemanage {

    // This class serves as a base for managing roles
    
    Map<Integer, String> roles = new HashMap<>(){
        
    };

    void rolecreate(int roleId, String roleName) {
        if (roleId <= 0 || roleName == null || roleName.isEmpty()) {
            throw new IllegalArgumentException("Invalid role ID or name");
        } else if (roles.containsKey(roleId)) {
            throw new IllegalArgumentException("Role ID already exists");
        } else {
           roles.put(roleId, roleName);
        }

    }

    String roleget(int roleId) {
        if (roleId <= 0) {
            throw new IllegalArgumentException("Invalid role ID");
        }
        else if (!roles.containsKey(roleId)) {
            throw new NoSuchElementException("Role ID does not exist");
        } else if (roles.get(roleId) == null || roles.get(roleId).isEmpty()) {
            throw new NoSuchElementException("Role name is empty");
        } else  {
        return roles.get(roleId);
        }
    } 


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