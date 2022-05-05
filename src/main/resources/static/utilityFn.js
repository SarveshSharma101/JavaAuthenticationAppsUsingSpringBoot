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
  const nxtUrl = "http://localhost:8090/login";
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
    reqBody.digestKeyValue = digest;
  } else {
    reqBody.encryptionType = "base64";
    reqBody.digestKeyValue = "";
  }

  console.log("-------------->", reqBody);
  Http.send(JSON.stringify(reqBody));

  if (
    Http.readyState == 4 &&
    Http.status == 201
    ) {
    console.log(Http.responseText);
    window.location.href = nxtUrl;
  } else {
    if (Http.status == 417) {
      alert("user already exist, choose another user name");
      return;
    } else if (Http.status == 400) {
      alert("Bad Request, username and password field cannot be empty");
      return;
    }
    alert("Some thing went wrong");
    console.log(Http.response);
    console.error("There was some error");
  }
}

function loginUser() {
  const [uname, password] = getUnameAndPassword();
  const url = "http://localhost:8090/basicUserLogin";
  const nxtUrl = "http://localhost:8090/app";
  const Http = new XMLHttpRequest();
  Http.open("PATCH", url, false);
  Http.setRequestHeader("Content-Type", "application/json");
  var reqBody = { uname: uname, pwd: password };

  console.log("-------------->", reqBody);
  Http.send(JSON.stringify(reqBody));

  if (Http.readyState == 4 && Http.status == 200) {
    console.log(Http.responseText);
    localStorage.setItem('uname', uname)
    window.location.href = nxtUrl;
  } else {
    if (Http.status == 404){
      alert("Not user found with the provided username")
      return
    }else if (Http.status == 401) {
      alert("Authentication failed, password didn't match")
      return
    }else if (Http.status == 400) {
      alert("Bad Request, username and password field cannot be empty");
      return;
    }
    
    console.log(Http.responseText);
    console.error("There was some error");
  }
}

function logout() {
  const uname = localStorage.getItem("uname");

  const url = "http://localhost:8090/basicUserLogout/" + uname;
  const nxtUrl = "http://localhost:8090/basicUserRegister";

  const Http = new XMLHttpRequest();
  Http.open("PATCH", url, false);
  Http.setRequestHeader("Content-Type", "application/json");
  Http.send();

  if (Http.readyState == 4 && Http.status == 200) {
    console.log(Http.responseText);
    localStorage.removeItem("uname");
    window.location.href = nxtUrl;
  } else {
    console.error("There was some error");
  }
}

function onload() {
  console.log("-----", localStorage.getItem("uname"))
  document.getElementById("username").innerHTML = localStorage.getItem("uname");
}