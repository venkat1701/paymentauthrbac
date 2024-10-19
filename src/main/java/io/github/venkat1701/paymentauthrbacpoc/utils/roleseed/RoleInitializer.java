//package io.github.venkat1701.paymentauthrbacpoc.utils.roleseed;
//
//import io.github.venkat1701.paymentauthrbacpoc.rbac.entities.roles.Role;
//import io.github.venkat1701.paymentauthrbacpoc.rbac.repositories.roles.RoleRepository;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//@Component
//public class RoleInitializer implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final RoleRepository roleRepository;
//
//    public RoleInitializer(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        this.initializeRoles();
//    }
//
//    /**
//     * Initialize roles on application startup. Roles will be created if they don't exist.
//     */
//    private void initializeRoles() {
//        Role user = new Role();
//        user.setName("USER");
//        user.setDescription("USER ROLE");
//
//        Role admin = new Role();
//        user.setName("ADMIN");
//        user.setDescription("ADMIN ROLE");
//
//        Role manager = new Role();
//        user.setName("MANAGER");
//        user.setDescription("MANAGER ROLE");
//
//
//        List<Role> roles = Arrays.asList(
//                user,
//                admin,
//                manager
//        );
//
//        for (Role role : roles) {
//            roleRepository.save(role);
//        }
//    }
//}
