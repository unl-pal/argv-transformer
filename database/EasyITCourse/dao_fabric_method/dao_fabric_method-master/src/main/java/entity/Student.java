package entity;

import java.util.List;


public class Student extends AbstractModelBean {
	private static final long serialVersionUID = -8807799012346193642L;
	private Integer id;
	private String firstName;
	private String lastName;
	private String group;
	private String date;
	List<Mark> marks;

}
