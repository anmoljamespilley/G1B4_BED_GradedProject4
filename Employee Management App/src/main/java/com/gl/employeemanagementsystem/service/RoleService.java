package com.gl.employeemanagementsystem.service;

import com.gl.employeemanagementsystem.model.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    Role getRoleById(Long id);

    List<Role> getAllRoles();
}
