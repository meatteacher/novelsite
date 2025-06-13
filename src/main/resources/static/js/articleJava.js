function tmp2(el) {
    let item = document.getElementsByClassName('tab ');
    for(let i=0; i<item.length; i++) {
        item[i].classList.remove('active')    
    }       
    el.classList.add('active')
}

// 작품 불러오기
let idx = get_url_info("itemNo");
console.log("itemNo: ",idx)
let item = NOVEL_LIST[idx];

document.getElementsByClassName('middle_title')[0].innerHTML = `
                <div>
                    <h3>${item.title}</h3>
                    <h3>${item.title}</h3>
                </div>
`

document.getElementsByClassName('pyoji_set')[0].innerHTML = `
        <div class="pyo_back">
                    <div class="bg_img" style="background-image: url(../port/pyoji/${item.src});"></div>
                </div>
                <div class="pyoji">
                    <div class="img">
                        <img src="../port/pyoji/${item.src}" alt="">
                    </div>
                </div>
`

document.getElementsByClassName('book_info')[0].innerHTML = `
        <div class="sort">
                    <div class="tag">${item.tag}</div>
                    <div class="event">무료연재</div>
                </div>
                <div class="title">${item.title}</div>
                <div class="artist">
                    <img src="../port/artist.png" alt="artist">
                    <a href="#">${item.artist}</a>
                </div>
                <div class="scale">
                    <div class="item">
                        <div class="icon">
                            <img src="../port/people.png">
                        </div>
                        <div class="num">
                        ${item.bookCount}
                        </div>
                    </div>
                    <div class="item">
                        <div class="icon">
                            <img src="../port/good.png">
                        </div>
                        <div class="num">
                        ${item.ddaBong}
                        </div>
                    </div>
                    <div class="item">
                        <div class="icon">
                            <img src="../port/star.png">
                        </div>
                        <div class="num">
                            200
                        </div>
                    </div>
                    <div class="item">
                        <div class="icon">
                            <img src="../port/talk.png">
                        </div>
                        <div class="num">
                            200
                        </div>
                    </div>
                </div>
                
`

document.getElementsByClassName('sogae')[0].innerHTML = `
        <p>${item.sogae }</p>
`

document.getElementsByClassName('episode_item')[0].innerHTML = `
    <a href="02.viewer_system.html?itemNo=${item.item_no}">
        <div class="episode_name">
            <div class="epi_num">
                <p>1화</p>
            </div>
            <div class="epi_info">
            <span>2025.03.20</span>
            <span>13.0 K</span>
                <div class="epi_scale">
                    <div class="e_eye">
                        <img src="../port/eye.png" alt="">
                        <em>1</em>
                    </div>
                    <div class="e_like">
                        <img src="../port/good.png" alt="">
                        <em>1</em>
                    </div>
                    <div class="e_datgul">
                        <img src="../port/talk.png" alt="">
                        <em>1</em>
                    </div>
                </div>
            </div>
        </div>
        <div class="down">
            <img src="../port/down.png" alt="down">
        </div>
    </a>
`