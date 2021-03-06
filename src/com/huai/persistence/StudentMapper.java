package com.huai.persistence;

import java.util.List;

import com.huai.beans.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

	/**
	 * 根据学号获得学生ID
	 * @param studentNO
	 * @return
	 */
	List<Integer> getStuIDByStuNO(String studentNO);

	/**
	 * 根据学号获取学生
	 * @param studentNO
	 * @return
	 * liangyihuai
	 */
	List<Student> getStudentsByStuNO(String studentNO);
	/**
	 * 根据学生名字获取学生对象
	 * @param name
	 * @return
	 */
	Student getStudentByName(String name);
	/**
	 * 根据studentID获取学生对象
	 * @param studentNO
	 * @return
	 */
	Student getStudentByStudentID(int studentID);
	/**
	 * 往student表中添加一个学生
	 * @param student
	 */
	void addStudent(Student student) throws Exception;
	/**
	 * 将学生添加到一个课程中
	 * @param student
	 * @param courseId
	 */
	void addStudentToCourse(int studentID, int courseId) throws Exception;
	/**
	 * 根据课程号得到在此门课程中的学生的studentID
	 * @param courseId
	 * @return
	 */
	List<Integer> getStudentIDByCourseId(int courseId);
	/**
	 * 把学生从一个课程中删除
	 * @param studentID
	 * @param courseId
	 */
	void deleteStudentFromCourse(int studentID, int courseId) throws Exception;

	/**
	 * 根据课程id查询学生
	 * @param courseID
	 * @return
	 * liangyihuai
	 */
	List<Student> getStudents(int courseID);
	/**
	 * 获得当前最大的studentID
	 * @return
	 */
	int getMaxStudentID();
	/**
	 * 删除student表中的学生记录
	 * @param studentID
	 */
	void deleteStudent(int studentID) throws Exception;

	/**
	 * update Student by studentNO
	 * @param student
	 * @throws Exception
	 * LiangYH
	 */
	int updateStu(Student student)throws Exception;

	/**
	 * 修改学生密码
	 * @param studentNO
	 * @param newPassword
	 * @return
	 */
	int updatePassword(@Param("studentNO")String studentNO, @Param("newPassword")String newPassword);
}
