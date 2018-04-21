package practice1;

import com.google.common.collect.ImmutableList;

public class Security {

    private static final boolean NON_PERMISSION = false;
    private static final boolean HAS_PERMISSION = true;

    private SecurityChecker securityChecker;

    public Security(SecurityChecker checker) {
        this.securityChecker = checker;
    }

    public boolean hasAccess(User user, Permission permission, ImmutableList<Permission> permissions) {

        if (user == null) return NON_PERMISSION;
        if (permission == null) return NON_PERMISSION;
        if (permissions.size() == 0) return NON_PERMISSION;
        if (securityChecker.isAdmin()) return HAS_PERMISSION;

        return this.securityChecker.checkPermission(user, permission) || permissions.contains(permission);
    }
}
