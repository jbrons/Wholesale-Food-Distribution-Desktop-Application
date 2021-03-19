package src;

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
