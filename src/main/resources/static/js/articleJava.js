document.addEventListener('DOMContentLoaded', function () {
    // 탭 초기화 강제 실행
    tmp2(document.querySelector('.tab'));

    const nno = get_url_info("nno");

    // 작품 불러오기
    fetch(`/novel/detail?nno=${nno}`)
        .then(resp => resp.json())
        .then(novel => {
            document.querySelector('.middle_title').innerHTML = `
                <div>
                    <h3>${novel.title}</h3>
                    <h3>${novel.title}</h3>
                </div>
            `;
            document.querySelector('.pyoji_set').innerHTML = `
                <div class="pyo_back">
                    <div class="bg_img" style="background-image: url('${novel.coverimg}');"></div>
                </div>
                <div class="pyoji">
                    <div class="img">
                        <img src="${novel.coverimg}" alt="">
                    </div>
                </div>
            `;
            document.querySelector('.book_info').innerHTML = `
                <div class="title">${novel.title}</div>
                <div class="artist">
                    <img src="/img/artist.png" alt="artist">
                    <a href="#">${novel.writer}</a>
                </div>
            `;
            document.querySelector('.sogae').innerHTML = `<p>${novel.description}</p>`;
        });

    // 에피소드 불러오기
    fetch(`/novel/${nno}/episodes`)
        .then(resp => resp.json())
        .then(list => {
            let html = '';
            list.forEach(ep => {
                html += `
                    <a href="/viewer/${ep.epno}">
                        <div class="episode_name">
                            <div class="epi_num"><p>${ep.episodeno}화</p></div>
                            <div class="epi_info">
                                <span>${ep.createdAt ? ep.createdAt.substring(0, 10) : ''}</span>
                                <span>${ep.viewCount ?? 0}</span>
                            </div>
                        </div>
                    </a>
                `;
            });
            document.querySelector('.episode_item').innerHTML = html;
        });

    function get_url_info(key) {
        let url = location.href.split("?");
        if (url.length > 1) {
            url = url[1].split("&");
            for (let i = 0; i < url.length; i++) {
                let tmp_url = url[i].split("=");
                if (tmp_url[0] == key) {
                    return tmp_url[1];
                }
            }
        }
        return null;
    }
});

function tmp2(el) {
    let item = document.getElementsByClassName('tab ');
    for (let i = 0; i < item.length; i++) {
        item[i].classList.remove('active');
    }
    el.classList.add('active');
}
