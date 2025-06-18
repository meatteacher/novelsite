function chk() {
    let pw1 = document.getElementById('pwd').value;
    let pw2 = document.getElementById('pwdchk').value;

    if (pw1 !== pw2) {
        alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요");
        return false;  // 이게 핵심: submit 자체 차단
    }

    // 비밀번호 일치하면 submit 진행
    alert("회원가입 완료!");
    return true;
}