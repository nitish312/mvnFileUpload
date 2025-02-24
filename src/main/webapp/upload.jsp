<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Excel File</title>
</head>
<body>
    <h2>Upload Excel File</h2>
    <form action="uploadFile" method="post" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <button type="submit">Upload</button>
    </form>
</body>
</html>
