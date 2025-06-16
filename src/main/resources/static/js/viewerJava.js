// 메뉴바 클릭에 사라지고 생기는 펑션

let view_window = document;
let viewer_option = document.getElementsByClassName('viewer_option')[0];

view_window.addEventListener('click', function() {
        viewer_option.classList.toggle('hidden');
});
