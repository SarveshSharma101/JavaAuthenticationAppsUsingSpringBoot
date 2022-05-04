function hideAndShowTheInput(show = true) {
  var x = document.getElementById("digestInput");
  if (show) {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function getUnameAndPassword() {
  var password = document.getElementById("password").value;
  var uname = document.getElementById("uname").value;
  return [uname, password];
}

function registerUser() {
  const [uname, password] = getUnameAndPassword();
  const url = "http://localhost:8090/basicUserRegister";
  const Http = new XMLHttpRequest();
  Http.open("POST", url, false);
  Http.setRequestHeader("Content-Type", "application/json");
  var reqBody = { uname: uname, pwd: password };

  if (document.getElementById("digest").checked) {
    var digest = document.getElementById("digestInput").value;
    if (digest.length<16 || digest.length>16){
        alert("Digest should be 16 character long");
        return
    }
    reqBody.encryptionType = "digest";
    reqBody.key = digest;
  } else {
    reqBody.encryptionType = "base64";
    reqBody.key = "";
  }

  console.log("-------------->", reqBody);
  Http.send(JSON.stringify(reqBody));

  if (
    Http.readyState == 4 &&
    Http.status == 201
    ) {
    console.log(Http.responseText);
    window.location.href = url;
  } else {
    console.log(Http.response);
    console.error("There was some error");
  }
}

function loginUser() {
  const [uname, password] = getUnameAndPassword();
  const url = "http://localhost:8090/basicUserLogin";
  const Http = new XMLHttpRequest();
  Http.open("POST", url, false);
  Http.setRequestHeader("Content-Type", "application/json");
  var reqBody = { uname: uname, pwd: password };

  console.log("-------------->", reqBody);
  Http.send(JSON.stringify(reqBody));

  if (Http.readyState == 4 && Http.status == 200) {
    console.log(Http.responseText);
  } else {
    console.log(Http.responseText);
    console.error("There was some error");
  }
}

function logout() {
  const url = "http://localhost:8090/basicUserLogout";
  const Http = new XMLHttpRequest();
  Http.open("POST", url, false);
  Http.setRequestHeader("Content-Type", "application/json");
  Http.send();


  if (
    Http.readyState == 4 &&
    Http.status == 200
    ) {
    console.log(Http.responseText);
  } else {
    console.error("There was some error");
  }
}