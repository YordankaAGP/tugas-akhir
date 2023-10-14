package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsrRepo extends JpaRepository<Usr,Long> {

    //SELECT * FROM MstUser WHERE UserName = ? OR NoHp = ? OR Email = ?
//    public Optional<Usr> findByEmail(String userName, String noHp, String email);
    public Optional<Usr> findByEmail(String email);
    public Optional<Usr> findByUsername(String username);
    public Optional<Usr> findByUsernameOrEmail(String username,String email);
}
