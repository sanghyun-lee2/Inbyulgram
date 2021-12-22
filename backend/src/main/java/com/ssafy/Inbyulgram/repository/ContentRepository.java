package com.ssafy.Inbyulgram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.Inbyulgram.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer>  {
	public List<Content> findTop1000ByOrderByUidDesc();
}
