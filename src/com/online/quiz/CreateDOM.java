package com.online.quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * CreateDOM
 * For generating JSON
 * From DB
 * And Generating XML
 * for further parsing
 */

public class CreateDOM {

    public static Document getDOM(String test, String id) throws SAXException, ParserConfigurationException, IOException, URISyntaxException, JSONException {
        Document dom = null;
        File quizFile = null;
        JSONObject quiz = new JSONObject();
        Connection con = DatabaseConnectionFactory.createConnection();
        JSONObject newJson = new JSONObject();
        try {
            ResultSet rs;
            PreparedStatement st = con.prepareStatement("SELECT * FROM  EXAMS WHERE id=?");
            st.setString(1, id);
            //Getting all exams Questions
            rs = st.executeQuery();
            String title = null;
            int count = 0;
            while (rs.next()) {
                count++;
                title = rs.getString("title");
            }
            // Add title of exam
            newJson.put("title", title);

            //Add total No. of Questions
            newJson.put("totalquizquestions", count);

            //Setting Quiz Duration
            newJson.put("quizduration", 30);

            // Reset ResultSet to avoid re-executing an already executed query
            rs.beforeFirst();
            JSONArray questionObject = new JSONArray();
            while (rs.next()) {
                //adding questions to An Questions Array
                JSONObject question = new JSONObject();
                question.put("quizquestion", rs.getString("question"));
                JSONArray answer = new JSONArray();
                answer.put(rs.getString("option1"));
                answer.put(rs.getString("option2"));
                answer.put(rs.getString("option3"));
                answer.put(rs.getString("option4"));
                question.put("answer", answer);
                question.put("correct", rs.getString("answer"));

                //System.out.println(newJson);
                questionObject.put(question);
            }
			 //System.out.println(questionObject);

            JSONObject question = new JSONObject();
            question.put("question", questionObject);
			 //System.out.println(question);

            JSONObject questions = new JSONObject();
            questions.put("questions", question);
            
            newJson.put("questions", question);

            //Generating the final JSON Object
            quiz.put("quiz", newJson);

        } catch (SQLException | JSONException sqe) {
            sqe.printStackTrace();
        }

        //Converting to XML
        String xml = XML.toString(quiz);

        PrintWriter writer = new PrintWriter("file.txt", "UTF-8");
        writer.println(xml);
        writer.close();

        String path = "file.txt"; //request.getServletContext().getRealPath("WEB-INF/../");

        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            in.close();
        } catch (IOException e) {
            // Maybe we should throw an exception and exit here if it fails ?
        }

        quizFile = new File(path);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        try {
            dom = db.parse(quizFile);
            dom.getDocumentElement().normalize();
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Error : Quiz File Not Found " + fileNotFound);
        }

        return dom;
    }

}
