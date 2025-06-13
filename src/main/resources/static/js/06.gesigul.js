let idx = get_url_info("itemNo");
console.log("itemNo: ",idx);
let item = gesipan_List[idx];

// 게시글 삽입

document.getElementsByClassName('title')[0].innerHTML = `
<div class="in_title">${item.title}</div>
                <div class="info">
                    <span>날짜</span>
                    <span>>${item.artist}</span>
                    <span>>${item.bookCount}</span>
                    <span>>${item.ddaBong}</span>
                    <span>댓글</span>
                </div>
`

document.getElementsByClassName('inside')[0].innerHTML = `
<div class="write">
${item.sogae}
                </div>
`