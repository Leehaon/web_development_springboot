const deleteButton = document.getElementById('delete-btn');

//if (deleteButton) {
//    deleteButton.addEventListener('click', event => {
//        let id = document.getElementById('article-id').value;
//        fetch(`/api/articles/${id}`, {
//            method: 'DELETE'
//        })
//        .then(() => {
//            alert('삭제가 완료되었습니다.');
//            location.replace('/articles')
//        });
//    });
//}

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            location.replace('/articles');
        }

        httpRequest('DELETE', `/api/articles/${id}`, null, success, fail);
    });
}

const modifyButton = document.getElementById('modify-btn');

//if (modifyButton) {
//    modifyButton.addEventListener('click', event => {
//        let params = new URLSearchParams(location.search);
//        let id = params.get('id');
//
//        fetch(`/api/articles/${id}`, {
//            method: 'PUT',
//            headers: {
//                "Content-Type": "application/json",
//            },
//            body: JSON.stringify({
//                title: document.getElementById('title').value,
//                content: document.getElementById('content').value
//            })
//        })
//        .then(() => {
//            alert('수정이 완료되었습니다.');
//            location.replace(`/articles/${id}`);
//        });
//    });
//}

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        })

        function success() {
            alert('수정 완료되었습니다.');
            location.replace(`/articles/${id}`);    // 고친거 확인하려고 여기로
        }

        function success() {
            alert('수정 실패했습니다.');
            location.replace(`/articles/${id}`);
        }

        httpRequest('PUT', `/api/articles/${id}`, body, success, fail);
    });
}

const createButton = document.getElementById("create-btn");

//if (createButton) {
//    createButton.addEventListener("click", (event) => {
//        fetch("/api/articles", {
//            method: "POST",
//            headers: {
//                "Content-Type": "application/json",
//            },
//            body: JSON.stringify({
//                title: document.getElementById("title").value,
//                content: document.getElementById("content").value,
//            }),
//        }).then(() => {
//            alert("등록 완료되었습니다.");
//            location.replace("/articles");
//        });
//    });
//}
if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보냅니다.
    createButton.addEventListener('click', event => {
        body= JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        };
        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/articles');
        };

        httpRequest('POST', '/api/articles', body, success, fail)
    });
}
// 쿠키를 가져오는 함수
function getCookie(key){
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result
}
// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {  // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer' + localStorage.getItem('access_token'),
                    'Content-Type': 'application.json',
                },
                body: JSON.stringify({
                    refresh_token: getCookie('refresh_token'),
                }),
            })
            .then(res => {
                if (res.ok) {
                    return res.json();
                }
            })
            .then(result => {   // 재발급이 성공하면 로컬 스토리지 값을 새로운 액세스 토큰으로 교체
                localStorage.setItem('access_token', result.accessToken);
                httpRequest(method, url, body, success, fail);
            })
            .catch(error => fail());
        } else {
            return fail();
        }
    });
}

// 글을 수정하거나 삭제할 때 글쓴이가 요구되기 때문에
// 본인 글이 아닌데 수정, 삭제를 시도하는 경우에 예외르 발생시키도록
// 작성 -> BlogService.java로 이동