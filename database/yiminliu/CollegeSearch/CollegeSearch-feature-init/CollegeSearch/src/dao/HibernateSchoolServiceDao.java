package dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import domain.School;

public class HibernateSchoolServiceDao implements SchoolServiceDao {

	private HibernateTemplate hibernateTemplate;

}
