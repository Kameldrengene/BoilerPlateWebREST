//Wait for onload event from window
window.onload = function () {
    //Attach clickHandler
    document.getElementById("testbutton").onclick = function () {
        //wait for response from fetch
        fetch("/BoilerPlate_war_exploded/rest/test/hello").then(function (response) {
            //Wait for response to be parsed as text
            response.text().then(function (text) {
                document.getElementById("outputDiv").innerHTML = text;
            })
        })

    };
    document.getElementById("testjsonbutton").onclick = function () {
        //wait for response from fetch
        fetch("/BoilerPlate_war_exploded/rest/test/json").then(function (response) {
            //Wait for response to be parsed as json
            response.json().then(function (json) {
                console.log(json);
                document.getElementById("outputDiv").innerHTML = json.text;
            })
        })
    };
    document.getElementById("testmysqlbutton").onclick = function () {
        //wait for response from fetch
        fetch("/BoilerPlate_war_exploded/rest/test/mysql").then(function (response) {
            //Wait for response to be parsed as json
            response.text().then(function (text) {
                console.log(text);
                document.getElementById("outputDiv").innerHTML = text;
            })
        })

    };

    document.getElementById("mysqlbutton").onclick = async function () {
        var j;
        //wait for response from fetch
        var j = await (await fetch("/BoilerPlate_war_exploded/rest/live/mysql_databases_nr/")).text();
        var i;
        var databases = [];
        for (i = 0; i < parseInt(j); i++) {
            databases.push(await (await fetch("/BoilerPlate_war_exploded/rest/live/mysql_databases/" + i)).text());
        }
        var databaseJSON = JSON.stringify(databases);
        console.log(databaseJSON);
        document.getElementById("outputDiv").innerHTML = databaseJSON;
    };

    document.getElementById("mysql_json").onclick = function () {
        fetch("/BoilerPlate_war_exploded/rest/live/mysql_json").then(function (response) {
            //Wait for response to be parsed as json
            response.json().then(function (json) {
                console.log(json.data[0]);
                document.getElementById("outputDiv").innerHTML = JSON.stringify(json.data[0].userName);
            })
        })
    };
};