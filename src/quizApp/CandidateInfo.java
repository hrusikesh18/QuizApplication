package quizApp;
import java.sql.*;

class CandidateInfo {
    int cid;
    String name;
    String email;
    int rollNo;
    int correct;
    String password;
    public CandidateInfo(int cid, String name, String email, int rollNo,String password) {
        this.cid = cid;
        this.name = name;
        this.email = email;
        this.rollNo = rollNo;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public static int getCid(String userName) {
        int cid = 0;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT cid FROM candidate WHERE name='" + userName + "'");

            if (resultSet.next()) {
                cid = resultSet.getInt("cid");
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ce) {
            System.out.println(ce);
        }
        return cid;
    }

    public String getPassword() {
        return password;
    }

    public void setCorrect(int correct, int cid1) {
        this.correct=correct;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement();
            stmt.executeQuery("update candidate set correct =" + correct + "where cid=" + cid1);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException ce) {
            System.out.println(ce);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }
    public int getCorrect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement();
            ResultSet rs =stmt.executeQuery("SELECT correct FROM candidate WHERE cid = " + cid);
            while(rs.next()){
                correct=rs.getInt("correct");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException ce) {
            System.out.println(ce);
        } catch (SQLException se) {
            System.out.println(se);
        }
        return correct;
    }



    public static CandidateInfo fetchCandidateDetails(int cid) {
        CandidateInfo candidate = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM candidate WHERE cid = " + cid);

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int rollNo = rs.getInt("roll");
                String password= rs.getString("password");
                candidate = new CandidateInfo(cid, name, email, rollNo,password);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ce) {
            System.out.println(ce);
        }
        return candidate;
    }


}