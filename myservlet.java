```java
package student;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/result")
public class MyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        // Get student details
        String regno = request.getParameter("regno");
        String name = request.getParameter("name");
        String department = request.getParameter("department");

        // Get attendance
        double attendance = Double.parseDouble(
                request.getParameter("attendance"));

        // Get 5 subject marks
        String[] markValues = request.getParameterValues("marks");

        int[] marks = new int[5];
        int total = 0;

        for (int i = 0; i < 5; i++) {
            marks[i] = Integer.parseInt(markValues[i]);
            total += marks[i];
        }

        // Calculate average
        double average = total / 5.0;

        // Grade
        String grade;

        if (average >= 90)
            grade = "A+";
        else if (average >= 80)
            grade = "A";
        else if (average >= 70)
            grade = "B";
        else if (average >= 60)
            grade = "C";
        else if (average >= 50)
            grade = "D";
        else
            grade = "F";

        // Eligibility
        String eligibility;

        if (attendance >= 75)
            eligibility = "Eligible";
        else
            eligibility = "Not Eligible";

        // Check subject-wise pass
        boolean subjectPass = true;

        for (int mark : marks) {
            if (mark < 40) {
                subjectPass = false;
                break;
            }
        }

        // Final result
        String result;

        if (subjectPass && average >= 50 && attendance >= 75)
            result = "PASS";
        else
            result = "FAIL";

        // Display result
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Student Result</title>");
        out.println("</head>");

        out.println("<body>");

        out.println("<h2>Student Examination Result</h2>");

        out.println("<table border='1' cellpadding='10'>");

        out.println("<tr><th>Register Number</th><td>"
                + regno + "</td></tr>");

        out.println("<tr><th>Student Name</th><td>"
                + name + "</td></tr>");

        out.println("<tr><th>Department</th><td>"
                + department + "</td></tr>");

        for (int i = 0; i < 5; i++) {
            out.println("<tr><th>Subject " + (i + 1)
                    + "</th><td>" + marks[i] + "</td></tr>");
        }

        out.println("<tr><th>Total</th><td>"
                + total + "</td></tr>");

        out.println("<tr><th>Average</th><td>"
                + String.format("%.2f", average) + "</td></tr>");

        out.println("<tr><th>Grade</th><td>"
                + grade + "</td></tr>");

        out.println("<tr><th>Attendance</th><td>"
                + attendance + "%</td></tr>");

        out.println("<tr><th>Eligibility</th><td>"
                + eligibility + "</td></tr>");

        out.println("<tr><th>Result</th><td>"
                + result + "</td></tr>");

        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
    }
}
```
