<%@ page import="java.util.ArrayList" %>
<%@ page import="com.SearchEngine.SearchResult" %>
<html>
<head>
    <title>Title</title>
    <style>
        table {
            border-collapse: collapse;
            border: 2px solid black;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
    </style>
        <link rel="stylesheet" type="text/css" href="styles.css" />
</head>
<body>
<h1>Simple Search Engine</h1>

<form action="Search">
    <input type="text"  name="keyword" > </input>
    <button>Submit</button>
</form>
<form action="History">
    <button>History</button>
</form>
     <table  class="resultsTable">
         <tr>
             <th>Title</th>
             <th>Link</th>
         </tr>
         <%
             ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
             for (SearchResult result :results){
         %>
        <tr>
            <td><%out.println(result.getTitle());%></td>
            <td><a href="<%out.println(result.getLink());%>"><%out.println(result.getLink());%></>a></td>
        </tr>
<%
    }
%>
     </table>
</body>
</html>
