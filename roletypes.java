import java.util.*;


abstract class roletypes {
    
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
        return roles.get(roleId);
    }


}