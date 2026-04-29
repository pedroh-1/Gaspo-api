package com.gaspo.api.repository.gaspo;

import com.gaspo.api.model.gaspo.AvisoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class AvisoRepository {

    public List<AvisoModel> findAll() {
    }
}
