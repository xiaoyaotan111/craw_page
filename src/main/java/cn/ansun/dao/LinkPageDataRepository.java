package cn.ansun.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import cn.ansun.domain.LinkPageData;

public interface LinkPageDataRepository extends JpaRepository<LinkPageData, Integer> {
	
}
