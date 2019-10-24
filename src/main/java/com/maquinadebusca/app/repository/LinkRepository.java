/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.repository;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.entity.Host;
import com.maquinadebusca.app.entity.Link;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vinicius
 */
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Override
    List<Link> findAll();

    Link findByUrl(String url);

    List<Link> findByHost(Host findById);

    List<Link> findByUrlIgnoreCaseContaining(String url);

    @Query(value = "SELECT * FROM link ORDER BY url", nativeQuery = true)
    List<Link> getInLexicalOrder();

    @Query(value = "SELECT * FROM link", nativeQuery = true)
    public Slice<Link> getPage(Pageable pageable);

    @Query(value = "SELECT * FROM link l join host h on l.host=h.id "
            + " WHERE id between ?1 and ?2 and h.hostName LIKE ?3", nativeQuery = true)
    List<Link> findLinkByIdRange(Long id1, Long id2, String hostName);

    @Query(value = "SELECT COUNT(*) FROM Link WHERE id between ?1 and ?2 ", nativeQuery = true)
    Long countLinkByIdRange(Long id1, Long id2);

    @Query(value = "SELECT COUNT(*) FROM Link WHERE ultimaColeta between ?1 and ?2 ", nativeQuery = true)
    public Link contarLinkPorIntervaloDeData(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Transactional
    @Modifying
    @Query(value = "UPDATE link l SET l.ultimaColeta = :data WHERE l.url LIKE CONCAT('%',:host,'%')", nativeQuery = true)
    int updateLastCrawlingDate(@Param("data") LocalDateTime ultimaColeta, @Param("host") String nomeHost);
}
