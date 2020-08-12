/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RepositoryDAO;

import Model.Student;
import DBconnection.DBconnect;
import Model.FeeModel;
import Model.Marks;
import Model.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class StudentDAO {

    DBconnect db = new DBconnect();

    public boolean addStudent(Student s) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean check = checkStudentID(s);

        if (check == true) {
            try {
                con = db.getConnection();

                stmt = con.prepareStatement("insert into student (id, name, fname, address, clas) values (?, ?, ?, ?, ?) ");

                stmt.setString(1, s.getId());
                stmt.setString(2, s.getName());
                stmt.setString(3, s.getFname());
                stmt.setString(4, s.getAddress());
                stmt.setString(5, s.getClas());

                int noOfefectedRows = stmt.executeUpdate();

                System.out.println("noOfefectedRows : " + noOfefectedRows);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close(con, stmt, null);
            }
        }

        return check;
    }

    public boolean checkStudentID(Student stu) {

        boolean status = true;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();

            stmt = con.prepareStatement("select id from student");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");

                if (stu.getId().equals(id)) {

                    status = false;
                    break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }

        return status;
    }

    public boolean addFee(FeeModel fm) {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Student s = new Student();
        s.setId(fm.getId());
        boolean status = false;

        boolean check = checkStudentID(s);

        if (check == false) {
            try {

                con = db.getConnection();

                stmt = con.prepareStatement(" insert into fees (id, admfee, sportfee, examfee, exfee) values (?, ?, ?, ?, ?) ");

                stmt.setString(1, fm.getId());
                stmt.setDouble(2, fm.getAdmfee());
                stmt.setDouble(3, fm.getSportfee());
                stmt.setDouble(4, fm.getExamfee());
                stmt.setDouble(5, fm.getExfee());

                int noOfefectedRows = stmt.executeUpdate();

                if (noOfefectedRows > 0) {
                    status = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close(con, stmt, null);
            }

        }

        return status;
    }

    public String getStudentName(String id) {
        String name = "";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet sr = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(" select name from student where id =\'" + id + "\'");
            sr = stmt.executeQuery();

            while (sr.next()) {

                name += sr.getString("name");
                System.out.println(sr.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }
        return name;

    }

    public boolean addMark(Marks mark) {
        boolean status = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = db.getConnection();
            stmt = con.prepareStatement("insert into result (id, name, bangla, english, science,"
                    + " sociology, ict, physics, chemistry  ,biology  ,com, math ) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            stmt.setString(1, mark.getId());
            stmt.setString(2, mark.getName());
            stmt.setDouble(3, mark.getBangla());
            stmt.setDouble(4, mark.getEnglish());
            stmt.setDouble(5, mark.getScience());
            stmt.setDouble(6, mark.getSociology());
            stmt.setDouble(7, mark.getIct());
            stmt.setDouble(8, mark.getPhysics());
            stmt.setDouble(9, mark.getChemistry());
            stmt.setDouble(10, mark.getBiology());
            stmt.setDouble(11, mark.getCom());
            stmt.setDouble(12, mark.getMath());

            int noOfefectedRows = stmt.executeUpdate();
            if (noOfefectedRows > 0) {

                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }
        return status;
    }

    public static void main(String[] args) {
        StudentDAO d = new StudentDAO();

        d.getStudentName("a123");
    }

    public Marks getResultInfo(String id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Marks rm = new Marks();

        try {
            con = db.getConnection();

            stmt = con.prepareStatement("select * from result where id = \'" + id + "\'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String rsid = rs.getString("id");

                if (rsid.equals(id)) {

                    rm.setId(rs.getString("id"));
                    rm.setName(rs.getString("name"));
                    rm.setBangla(rs.getDouble("bangla"));
                    rm.setEnglish(rs.getDouble("english"));
                    rm.setMath(rs.getDouble("math"));
                    rm.setScience(rs.getDouble("science"));
                    rm.setSociology(rs.getDouble("sociology"));
                    rm.setIct(rs.getDouble("ict"));
                    rm.setBiology(rs.getDouble("biology"));
                    rm.setChemistry(rs.getDouble("chemistry"));
                    rm.setPhysics(rs.getDouble("physics"));
                    rm.setCom(rs.getDouble("com"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }
        return rm;
    }

    public boolean checkRsId(String id) {
        boolean status = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();

            stmt = con.prepareStatement("select id from result where id=\'" + id + "\'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String rsid = rs.getString("id");

                if (rsid.equals(id)) {

                    status = true;
                    break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }

        return status;
    }

    public boolean deleteStudent(String id) {
        boolean status = false;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();

            stmt = con.prepareStatement("delete  from student where id=\'" + id + "\'");
            // rs = stmt.executeQuery();
            int noOfefectedRows = stmt.executeUpdate();
            if (noOfefectedRows > 0) {

                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }

        return status;
    }

    public boolean addTeacher(Teacher s) {
        boolean status = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean check = checkTeacherID(s);

        if (check == true) {
            try {
                con = db.getConnection();
                stmt = con.prepareStatement("insert into teacher (id, name, fname, address, salary) values (?, ?, ?, ?, ?) ");
                stmt.setString(1, s.getId());
                stmt.setString(2, s.getName());
                stmt.setString(3, s.getFname());
                stmt.setString(4, s.getAddress());
                stmt.setDouble(5, s.getSalary());

                int noOfefectedRows = stmt.executeUpdate();

                System.out.println("noOfefectedRows : " + noOfefectedRows);
                if (noOfefectedRows > 0) {
                    status = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close(con, stmt, null);
            }
        }

        return status;
    }

    private boolean checkTeacherID(Teacher s) {
        boolean status = true;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = db.getConnection();

            stmt = con.prepareStatement("select id from student");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");

                if (s.getId().equals(id)) {

                    status = false;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }

        return status;

    }

    public Student getStudentInfo(String id) {
    Student s = new Student();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    try {
            con = db.getConnection();

            stmt = con.prepareStatement("select * from student where id =\'"+id+"\'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                s.setId( rs.getString("id"));
                s.setName(rs.getString("name"));
                s.setFname(rs.getString("fname"));
                s.setAddress(rs.getString("address"));
                s.setClas(rs.getString("clas"));
              
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(con, stmt, null);
        }

    
    return s;
    }
}
