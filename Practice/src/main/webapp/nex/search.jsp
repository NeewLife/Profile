<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import="com.nexacro.java.xapi.data.*" %>
<%@ page import="com.nexacro.java.xapi.tx.*" %>

<%@ page contentType="text/xml; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
</head>
<body>
<%
/****** Service API initialization ******/
PlatformData pdata = new PlatformData();

int nErrorCode = 0;
String strErrorMsg = "START";

/******* JDBC Connection *******/
Connection conn = null;
Statement  stmt = null;
ResultSet  rs   = null;
Class.forName("oracle.jdbc.driver.OracleDriver");
conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.5:1521:XE","giantstudy","gs123");
stmt = conn.createStatement();

try {
    /******* SQL query *************/
    String SQL = "select * from board_study";
    rs = stmt.executeQuery(SQL);

    /********* Dataset Create ************/
    DataSet ds = new DataSet("customers");
    ds.addColumn("seq",DataTypes.STRING, 4);
    ds.addColumn("name",DataTypes.STRING, 100);
    ds.addColumn("id", DataTypes.STRING, 100);
    ds.addColumn("board_subject", DataTypes.STRING, 1000);
    ds.addColumn("board_content", DataTypes.STRING, 1000);
    ds.addColumn("reg_date", DataTypes.STRING, 100);
    ds.addColumn("upt_date", DataTypes.STRING, 100);
    int row = 0;
    while(rs.next())
    {
        row = ds.newRow();
        ds.set(row, "seq", rs.getString("seq"));    
        ds.set(row, "name", rs.getString("MEM_NAME"));
        ds.set(row, "id", rs.getString("mem_id"));
        ds.set(row, "board_subject", rs.getString("BOARD_SUBJECT"));
        ds.set(row, "board_content", rs.getString("BOARD_CONTENT"));
        ds.set(row, "reg_date", rs.getString("REG_DATE"));
        ds.set(row, "upt_date", rs.getString("UPT_DATE"));
        out.print("<td>" + rs.getString("BOARD_SUBJECT") + "</td>");
    }

    /********* Adding Dataset to PlatformData ************/
    pdata.addDataSet(ds);

    nErrorCode = 0;
    strErrorMsg = "SUCC";
}
catch(SQLException e) {
    nErrorCode = -1;
    strErrorMsg = e.getMessage();
}

/******** JDBC Close *******/
if ( stmt != null ) try { stmt.close(); } catch (Exception e) {}
if ( conn != null ) try { conn.close(); } catch (Exception e) {}

PlatformData senddata = new PlatformData();
VariableList varList = senddata.getVariableList();
varList.add("ErrorCode", nErrorCode);
varList.add("ErrorMsg", strErrorMsg);

/******** XML data Create ******/
HttpPlatformResponse res = new HttpPlatformResponse(response, 
    PlatformType.CONTENT_TYPE_XML,"UTF-8");
res.setData(pdata);
%>

</body>
</html>
