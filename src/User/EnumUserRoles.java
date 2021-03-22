package src.User;

/**
 * This class will be used by Users.java in order to set and check user
 * roles. This will also be useful when checking permissions for various
 * functions.
 *
 * @author Jacob Price | ga4116
 */
public enum EnumUserRoles {
    OWNER (3),
    ADMINISTRATOR (2),
    INVENTORY_MANAGER(1),
    PURCHASER(1),
    ACCOUNTANT(1),
    SALES_PERSON(1);

    private final int permissionLevel;

    EnumUserRoles(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public int getPermissionLevel() {return permissionLevel; }
}
