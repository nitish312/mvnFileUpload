<%@page language="java"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <title>Skill and Level Checker</title>

    <link rel="stylesheet" href="style.css">

</head>
<body>

<div class="container">
    <h2>Skill and Level Checker</h2>
    <form id="checkForm" action="add">
        <label for="skill">Skill:</label>
        <input type="text" id="skill" name="skill" required>

        <label for="level">Level:</label>
        <select id="level" name="level" required>
            <option value="e0">E0</option>
            <option value="e1">E1</option>
            <option value="e2">E2</option>
            <option value="e3">E3</option>
            <option value="l1">L1</option>
            <option value="l2">L2</option>
            <option value="l3">L3</option>
        </select>

        <button type="submit" onclick="showDiv1()">Check</button>
    </form>

<div class="alert alert-success" id="showresult" role="alert">
      File is generated!
</div>

</div>

<script>

function showDiv1() {
            // Display the result div
            const resultDiv = document.getElementById("showresult").style.display = "block";

            setTimeout(function() {
                            resultDiv.style.display = "none";
                        }, 5000);
        }

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>