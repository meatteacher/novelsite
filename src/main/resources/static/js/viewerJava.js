const pathParts = window.location.pathname.split("/");
const epno = pathParts[pathParts.length - 1];

let viewer_area = document.querySelector('.real_view');
let viewer_option = document.querySelector('.viewer_option');
let commentModal = document.getElementById('commentModal');

viewer_area.addEventListener('click', function () {
    if (commentModal && !commentModal.classList.contains('hidden')) return;
    viewer_option.classList.toggle('hidden');
});

document.addEventListener('DOMContentLoaded', function () {
    const likeBtn = document.getElementById('likeBtn');
    const likeIcon = document.getElementById('likeIcon');
    const commentBtn = document.getElementById('commentToggleBtn');
    const closeModal = document.getElementById('closeModal');
    const submitComment = document.getElementById('submitComment');
    const commentInput = document.getElementById('commentInput');
    const commentList = document.getElementById('commentList');
    let myNickname = "GUEST";

    fetch(`/api/episode/${epno}/comments/me`)
        .then(res => res.text())
        .then(nick => {
            myNickname = nick;
        });

    if (likeBtn && likeIcon) {
        likeBtn.addEventListener('click', function (e) {
            e.preventDefault();
            fetch(`/episode/like?epno=${epno}`, { method: 'POST' })
                .then(response => response.text())
                .then(result => {
                    if (result === "좋아요 등록됨") likeIcon.innerText = "❤️";
                    else if (result === "좋아요 취소됨") likeIcon.innerText = "🤍";
                    else alert(result);
                })
                .catch(err => console.error("좋아요 요청 실패:", err));
        });
    }

    if (submitComment && commentInput && commentList) {
        submitComment.addEventListener('click', function () {
            const content = commentInput.value.trim();
            if (!content) {
                alert("댓글을 입력하세요.");
                return;
            }

            fetch(`/api/episode/${epno}/comments`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ content })
            })
                .then(response => {
                    if (!response.ok) throw new Error("댓글 등록 실패");
                    return response.json();
                })
                .then(() => {
                    commentInput.value = "";
                    loadComments();
                })
                .catch(err => {
                    console.error(err);
                    alert("로그인이 필요합니다.");
                });
        });

        commentBtn.addEventListener('click', function () {
            commentModal.classList.remove('hidden');
            loadComments(); // 댓글 로딩
        });

        function loadComments() {
            fetch(`/api/episode/${epno}/comments`)
                .then(response => response.json())
                .then(comments => {
                    commentList.innerHTML = "";
                    comments.forEach(c => {
                        if (c.nickname) {
                            const div = document.createElement("div");
                            div.classList.add("single-comment");
                            let innerHTML =
                                `<b>${c.nickname}:</b> <span class="comment-content">${c.content}</span>`;
                            if (c.nickname === myNickname) {
                                innerHTML += `<button class="edit-comment" data-id="${c.epcommentno}">수정</button>                                    <button class="delete-comment" data-id="${c.epcommentno}">삭제</button>`;
                            }
                            div.innerHTML = innerHTML;
                            commentList.appendChild(div);
                        } else {
                            console.warn("닉네임이 없는 댓글이 감지되었습니다:", c);
                        }
                    });

                    // 삭제
                    document.querySelectorAll('.delete-comment').forEach(btn => {
                        btn.addEventListener('click', function () {
                            const epCommentNo = this.dataset.id;
                            if (confirm("정말 삭제하시겠습니까?")) {
                                fetch(`/api/episode/${epno}/comments/epComment/delete?epCommentNo=${epCommentNo}`, {
                                    method: "POST"
                                })
                                    .then(response => response.text())
                                    .then(() => {
                                        alert("삭제되었습니다.");
                                        loadComments();
                                    })
                                    .catch(err => console.error("삭제 실패:", err));
                            }
                        });
                    });

                    // 수정
                    document.querySelectorAll('.edit-comment').forEach(btn => {
                        btn.addEventListener('click', function () {
                            const epCommentNo = this.dataset.id;
                            const commentDiv = this.parentElement;
                            const contentSpan = commentDiv.querySelector('.comment-content');
                            const originalContent = contentSpan.innerText;

                            // 기존 span 숨기기
                            contentSpan.style.display = 'none';

                            // textarea 생성
                            const textarea = document.createElement('textarea');
                            textarea.classList.add('edit-textarea');
                            textarea.value = originalContent;
                            commentDiv.insertBefore(textarea, this);

                            // 저장 버튼
                            const saveBtn = document.createElement('button');
                            saveBtn.innerText = '저장';
                            saveBtn.classList.add('save-edit');
                            commentDiv.insertBefore(saveBtn, this);

                            // 취소 버튼
                            const cancelBtn = document.createElement('button');
                            cancelBtn.innerText = '취소';
                            cancelBtn.classList.add('cancel-edit');
                            commentDiv.insertBefore(cancelBtn, this);

                            // 수정/삭제 버튼 숨기기
                            this.style.display = 'none';
                            const deleteBtn = commentDiv.querySelector('.delete-comment');
                            if (deleteBtn) deleteBtn.style.display = 'none';

                            // 저장 이벤트
                            saveBtn.addEventListener('click', function () {
                                const newContent = textarea.value.trim();
                                if (!newContent) {
                                    alert("내용을 입력하세요.");
                                    return;
                                }

                                fetch(`/api/episode/${epno}/comments/epComment/update`, {
                                    method: "POST",
                                    headers: { "Content-Type": "application/json" },
                                    body: JSON.stringify({
                                        epCommentNo: epCommentNo,
                                        content: newContent
                                    })
                                })
                                    .then(response => response.text())
                                    .then(() => {
                                        alert("수정되었습니다.");
                                        loadComments(); // 다시 로딩
                                    })
                                    .catch(err => console.error("수정 실패:", err));
                            });

                            // 취소 이벤트
                            cancelBtn.addEventListener('click', function () {
                                textarea.remove();
                                saveBtn.remove();
                                cancelBtn.remove();
                                contentSpan.style.display = '';
                                btn.style.display = '';
                                if (deleteBtn) deleteBtn.style.display = '';
                            });
                        });
                    });

                })
                .catch(error => console.error("댓글 로딩 중 오류 발생:", error));
        }
    }
});
