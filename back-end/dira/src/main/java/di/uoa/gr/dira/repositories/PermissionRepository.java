package di.uoa.gr.dira.repositories;


import di.uoa.gr.dira.entities.project.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>  {
}
