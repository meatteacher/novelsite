function chk() {
    let pw1 = document.getElementById('pw1').value;
    let pw2 = document.getElementById('pw2').value;

    if (pw1 != pw2){
        alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요");
        return false;
    }
    else {
        alert("비밀번호가 일치합니다");
        setTimeout(function() {
            window.location.href = "00.main_page.html";
        }, 500);
    }
}