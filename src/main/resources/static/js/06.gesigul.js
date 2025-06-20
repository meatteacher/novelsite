let isEditMode = false;
let editingCommentId = null;
let myNickname = "GUEST";

document.addEventListener("DOMContentLoaded", function () {
    const boardId = get_url_info("boardno");
    if (!boardId) {
        alert("잘못된 접근입니다.");
        return;
    }

    // 로그인한 유저 닉네임 가져오기
    fetch("/api/board-comments/me")
        .then(res => res.text())
        .then(nick => {
            myNickname = nick;
            loadComments(); // 닉네임 받은 후 댓글 불러오기
        });

    // 게시글 정보 불러오기
    fetch(`/api/board/${boardId}`)
        .then(res => res.json())
        .then(data => {
            const isOwner = data.nickname === myNickname;

            document.querySelector(".title").innerHTML = `
            <div class="in_title">${data.title}</div>
            <div class="info">
                <div class="meta">
                    <span>${data.createdAt?.substring(0, 10)}</span>
                    <span class="nickname">${data.nickname || "익명"}</span>
                </div>
                <div class="info-right">
                    <span class="view">조회수: ${data.viewCount}</span>
                    ${isOwner ? `
                        <button id="editPostBtn">수정</button>
                        <button id="deletePostBtn">삭제</button>
                    ` : ""}
                </div>
            </div>
        `;

            document.querySelector(".write").innerText = data.content;
        });
    // 댓글 불러오기 함수
    function loadComments() {
        fetch(`/api/board-comments/${boardId}`)
            .then(res => res.json())
            .then(data => {
                const listEl = document.querySelector('.datgul .list');
                listEl.innerHTML = "";
                document.querySelector('.gul_count span').innerText = data.length;

                data.forEach(comment => {
                    const div = document.createElement('div');
                    div.innerHTML = `
                        <div class="comment-box">
                            <div class="comment-header">
                              <span class="nickname">${comment.nickname || "익명"}</span>
                              <div class="comment-buttons">
                                ${comment.nickname === myNickname ? `
                                  <button class="edit-btn" data-id="${comment.id}">수정</button>
                                  <button class="delete-btn" data-id="${comment.id}">삭제</button>
                                ` : ""}
                              </div>
                            </div>
                            <p class="comment-content" data-id="${comment.id}">${comment.content}</p>
                            <small>${comment.createdAt?.substring(0,10)}</small>
                          </div>
                    `;
                    listEl.appendChild(div);
                });
            });
    }

    // 댓글 등록 or 수정
    document.getElementById('commentSubmitBtn').addEventListener('click', () => {
        const content = document.getElementById('rcmd').value;
        if (!content.trim()) return;

        if (isEditMode) {
            // 수정
            fetch(`/api/board-comments/${editingCommentId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ content: content })
            }).then(() => {
                isEditMode = false;
                editingCommentId = null;
                document.getElementById('rcmd').value = "";
                document.getElementById('commentSubmitBtn').innerText = "등록";
                loadComments(); // reload 대신 비동기 재로딩
            });
        } else {
            // 등록
            fetch(`/api/board-comments/${boardId}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ content: content })
            }).then(() => {
                document.getElementById('rcmd').value = "";
                loadComments();
            });
        }
    });

    document.addEventListener("click", function (e) {
        const target = e.target;

        // 게시글 수정
        if (target.id === "editPostBtn") {
            location.href = `/board/write?boardno=${boardId}`;
        }

        // 게시글 삭제
        if (target.id === "deletePostBtn") {
            if (confirm("정말 삭제하시겠습니까?")) {
                fetch(`/api/board/${boardId}`, {
                    method: "DELETE"
                })
                    .then(() => {
                        alert("삭제 완료!");
                        location.href = "/05.gesipan";
                    })
                    .catch(err => {
                        alert("삭제 실패");
                        console.error(err);
                    });
            }
        }

        // 댓글 수정
        if (target.classList.contains("edit-btn")) {
            const commentId = target.dataset.id;
            const contentEl = document.querySelector(`.comment-content[data-id="${commentId}"]`);
            const content = contentEl.innerText;

            document.getElementById('rcmd').value = content;
            document.getElementById('commentSubmitBtn').innerText = "수정 완료";
            isEditMode = true;
            editingCommentId = commentId;
        }

        // 댓글 삭제
        if (target.classList.contains("delete-btn")) {
            const commentId = target.dataset.id;
            if (confirm("정말 삭제하시겠습니까?")) {
                fetch(`/api/board-comments/${commentId}`, {
                    method: "DELETE"
                }).then(() => loadComments());
            }
        }
    });

    // 댓글 수 불러오기
    fetch(`/api/board-comments/${boardId}`)
        .then(res => res.json())
        .then(data => {
            document.querySelector('.datgul_count span').innerText = data.length;
        });

    // 좋아요 수 불러오기
    fetch(`/api/board-like/count/${boardId}`)
        .then(res => res.text())
        .then(count => {
            document.querySelector('.like span').innerText = count;
        });

    // 추천 버튼 동작
    document.querySelector('.recommand button').addEventListener("click", function () {
        fetch(`/api/board-like/${boardId}`, {
            method: "POST"
        })
            .then(res => {
                if (res.ok) {
                    alert("추천 완료!");
                    // 새로 불러오기
                    return fetch(`/api/board-like/count/${boardId}`)
                        .then(res => res.text())
                        .then(count => {
                            document.querySelector('.like span').innerText = count;
                        });
                } else {
                    alert("이미 추천했거나 오류 발생!");
                }
            })
            .catch(err => {
                alert("추천 실패");
                console.error(err);
            });
    });
});
