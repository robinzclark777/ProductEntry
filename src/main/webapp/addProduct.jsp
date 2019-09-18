<html>
<body>
<form action="addProduct">

<label for="requirement">NMRS Requirement#</label>
<input type="text" name="requirementId" id="requirementId"><br>

<label for="targetId">Target ID</label>
<input type="text" name="targetId" id="targetId"><br>

<label for="title">Title</label>
<input type="text" name="title" id="title"><br>

<label for="summary">Summary</label>
<input type="text" name="summary" id="summary"><br>

<label for="keywords">Keywords</label>
<input type="text" name="keywords" id="keywords"><br>

<label for="collectionDate">Collection date (YYYY-MM-DDT00:00):</label>
<input type="datetime-local" id="collectionDate" name="collectionDate"
       value="2019-05-08T00:00"
       min="2017-01-01T00:00" max="2020-12-31T00:00"><br>
       
<label for="latitude">Latitude</label>
<input type="text" name="latitude" id="latitude"><br>
  
<label for="longitude">Longitude</label>
<input type="text" name="longitude" id="longitude"><br>

<label for="url">URL</label>
<input type="text" name="url" id="url"><br>

<input type="submit">  
       
</form>
</body>
</html>
