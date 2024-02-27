package com.gl.employeemanagementsystem.serviceimpl;

import com.gl.employeemanagementsystem.model.Role;
import com.gl.employeemanagementsystem.repository.RoleRepository;
import com.gl.employeemanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
        try {
            return roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            // Handle duplicate role error
            throw new IllegalArgumentException("Role with name '" + role.getName() + "' already exists.");
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
