package com.demo.sample

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository : JpaRepository<Sample, Long>, QuerydslPredicateExecutor<Sample>
