document.addEventListener("DOMContentLoaded", function () {
    const boardId = get_url_info("boardno"); // 수정 모드 여부 판단
    const form = document.getElementById("boardWriteForm");

    // 수정 모드면 기존 데이터 불러오기
    if (boardId) {
        fetch(`/api/board/${boardId}`)
            .then(res => res.json())
            .then(data => {
                document.getElementById("title").value = data.title;
                document.getElementById("content").value = data.content;
                document.querySelector(".submit_btn").innerText = "수정 완료";
            });
    }

    // form 제출 시 기본 동작 막고 JS 처리
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const title = document.getElementById("title").value.trim();
        const content = document.getElementById("content").value.trim();

        if (!title || !content) {
            alert("제목과 내용을 입력하세요.");
            return;
        }

        const method = boardId ? "PUT" : "POST";
        const url = boardId ? `/api/board/${boardId}` : "/api/board/write";

        fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ title, content })
        })
            .then(() => {
                alert(boardId ? "수정 완료!" : "등록 완료!");
                location.href = "/05.gesipan"; // 자유게시판 목록으로 이동
            })
            .catch(err => {
                alert("처리 실패");
                console.error(err);
            });
    });
});

// URL에서 boardno 가져오는 유틸 함수
function get_url_info(key) {
    const params = new URLSearchParams(location.search);
    return params.get(key);
}