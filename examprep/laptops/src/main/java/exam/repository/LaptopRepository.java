package exam.repository;

import exam.model.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//ToDo:
@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    Laptop findByMacAddress(String macAddress);

    List<Laptop> findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();
}
