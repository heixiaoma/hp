selectRole
===
    SELECT sys_role.* FROM sys_role,sys_user_role WHERE sys_user_role.role_id=sys_role.id AND sys_user_role.user_id=#userId#