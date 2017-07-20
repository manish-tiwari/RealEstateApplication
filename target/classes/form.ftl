<html>
    <head>
        <title>Welcome</title>
        <h1>Welcome to the online Real Estate portal(Pune)</h1>
        
    </head>
    <body style="background-color:powderblue;">
    	<div align="right">
        <form class="form-inline" method="GET" action="/login">
                
                <label for="email">Email</label>
                <input type="email"
                       class="form-control"
                       id="email"
                       name="email"
                       placeholder="john.doe@example.org">
                 
                <label for="name">Password</label>
                <input type="password"
                       class="form-control"
                       id="pwd"
                       name="pwd">
            <button type="submit" class="btn btn-default">Login</button>
        </form>
        
        </div>
        <div align="left">
        	<ul>
        		<li> <a href="http://127.0.0.1:4567/properties">See all the properties in pune</a></li>
        		<li> <a href="http://127.0.0.1:4567/properties/sort/1">See all the available properties in ascending order of price</a></li> 
        		<li><a href="http://127.0.0.1:4567/properties/sort/-1">See all the available properties in descending order of price</a></li> 
        		<li> See all the available properties in the range of  
        		<form class="form-inline" method="GET" action="/properties/filter/price">
                
                	<label for="min">Minimum Price(in Rs.)</label>
               		 <input type="number"
                       class="form-control"
                       id="min" min="1"
                       name="min">
                 
                	<label for="max">Maximum Price(in Rs.)</label>
                	<input type="number"
                       class="form-control" min="1"
                       id="max"
                       name="max">
          			  <button type="submit" class="btn btn-default">Filter</button>
       			 </form> 
        		</li>
        		<li> Search properties by area name 
        			<form class="form-inline" method="GET" action="/properties/searchByArea">
               			<input type="text" class="form-control" id="area" name="area">
            			<button type="submit" class="btn btn-default">Search</button>
        			</form>
        		 </li>
        	 </ul>
        </div>
    <body>
</html>