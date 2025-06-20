// 글자에 임팩트 넣기
function tmp2(el) {
    let item = document.getElementsByClassName('tabli ');
    for(let i = 0; i < item.length; i++) {
        item[i].classList.remove('active');
    }
    el.classList.add('active');
}

// 게시판 글 삽입문구
let guel = document.getElementsByClassName('ul')[0];

document.addEventListener("DOMContentLoaded", function () {
    // 게시글 목록 불러오기
    fetch("/api/board/list")
        .then(res => res.json())
        .then(data => {
            guel.innerHTML = '';
            data.forEach(board => {
                const li = document.createElement("li");
                li.innerHTML = `
                    <a href="06.gesigul?boardno=${board.boardno}">
                        <div class="title">
                            <span class="icon_new">
                                <img src="/img/NEW1.png" alt="">
                            </span>
                            ${board.title}
                        </div>
                        <div class="info">
                            <span>날짜 : ${board.createdAt?.substring(0, 10) || "날짜없음"}</span>
                            <span class="nickname">${board.nickname || "익명"}</span>
                            <span>조회수: ${board.viewCount}</span>
                        </div>
                    </a>
                `;
                guel.appendChild(li);
            });
        })
        .catch(err => {
            console.error("게시글 불러오기 실패:", err);
        });

    // 글쓰기 버튼 클릭 시 글쓰기 페이지로 이동
    const writeBtn = document.getElementById("goWriteBtn");
    if (writeBtn) {
        writeBtn.addEventListener("click", function () {
            window.location.href = "/board/write";
        });
    }
});
