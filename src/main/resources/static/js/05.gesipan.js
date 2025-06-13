// 글자에 임팩트 넣기

function tmp2(el) {
    let item = document.getElementsByClassName('tabli ');
    for(let i=0; i<item.length; i++) {
        item[i].classList.remove('active')    
    }       
    el.classList.add('active')
}

// 게시판 글 삽입문구

let guel = document.getElementsByClassName('ul')[0];

for(let i=0; i<gesipan_List.length; i++) {
guel.innerHTML += `
<li>
        <a href="06.gesigul.html?itemNo=${gesipan_List[i].item_no}">
            <div class="title">
                <span class="icon_new">
                    <img src="../port/NEW1.png" alt="">
                </span>
                ${gesipan_List[i].title}
            </div>
            <div class="info">
                <span>날짜 : 2025-03-27</span>
                <span class="nickname">${gesipan_List[i].artist}</span>
                <span>${gesipan_List[i].bookCount}</span>
                <span>${gesipan_List[i].ddaBong}</span>
                <span>댓글</span>
            </div>
        </a>
</li>
`;
}