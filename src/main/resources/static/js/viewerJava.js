// 메뉴바 클릭에 사라지고 생기는 펑션

let view_window = document;
let viewer_option = document.getElementsByClassName('viewer_option')[0];

view_window.addEventListener('click', function() {
        viewer_option.classList.toggle('hidden');
});

// 에피소드 불러오기

let idx = get_url_info("itemNo");
console.log("itemNo: ",idx)
let item = NOVEL_LIST[idx];

document.getElementsByClassName('middle_title')[0].innerHTML = `
        <p>${item.title}</p>
        <p>편수</p>
`

document.getElementsByClassName('episode')[0].innerHTML = `
        ${item.episode}
`