/**
 * Common.js - 공통
 */

/**
 * Fetch 요청 공통 처리
 * @param {string} url - 요청 URL
 * @param {Object} options - Fetch 옵션
 * @returns {Promise} - Fetch 결과 Promise
 */
function fn_fetchRequest(url, options = {}) {
    const defaultHeaders = {
        'Content-Type': 'application/json',
    };

    // FormData 사용 시 Content-Type 제거
    if (options.body instanceof FormData) {
        delete defaultHeaders['Content-Type'];
    }

    // 옵션에 헤더 병합
    options.headers = { ...defaultHeaders, ...options.headers };

    return fetch(url, options)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Fetch failed: ${response.status} ${response.statusText}`);
            }
            return response;
        })
        .catch(fn_handleError);
}

/**
 * 에러 처리
 * @param {Error} err - 에러 객체
 */
function fn_handleError(err) {
    console.error('Error occurred:', err.message);
    alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    throw err; // 재처리를 위해 에러를 다시 던짐
}

/**
 * GET 요청
 * @param {string} url - 요청 URL
 * @returns {Promise} - JSON 데이터를 반환
 */
function fn_fetchGetData(url) {
    return fn_fetchRequest(url, { method: 'GET' }).then(response => response.json());
}

/**
 * GET Blob 요청
 * @param {string} url - 요청 URL
 * @returns {Promise} - Blob 데이터를 반환
 */
function fn_fetchGetBlob(url) {
    return fn_fetchRequest(url, { method: 'GET' }).then(response => response.blob());
}

/**
 * POST 요청
 * @param {string} url - 요청 URL
 * @param {Object} body - 요청 데이터
 * @param {Object} headers - 추가 헤더 옵션
 * @returns {Promise} - JSON 데이터를 반환
 */
function fn_fetchPostData(url, body, headers = {}) {
    return fn_fetchRequest(url, {
        method: 'POST',
        headers,
        body: body instanceof FormData ? body : JSON.stringify(body),
    }).then(response => response.json());
}

/**
 * PATCH 요청
 * @param {string} url - 요청 URL
 * @param {Object} body - 요청 데이터
 * @param {Object} headers - 추가 헤더 옵션
 * @returns {Promise} - JSON 데이터를 반환
 */
function fn_fetchPatchData(url, body, headers = {}) {
    return fn_fetchRequest(url, {
        method: 'PATCH',
        headers,
        body: JSON.stringify(body),
    }).then(response => response.json());
}

/**
 * DELETE 요청
 * @param {string} url - 요청 URL
 * @returns {Promise} - JSON 데이터를 반환
 */
function fn_fetchDeleteData(url) {
    return fn_fetchRequest(url, { method: 'DELETE' }).then(response => response.json());
}

/**
 * id 패턴 유효성 검사
 * @param {*} id 
 * @returns {boolean}
 */
function validateId(id) {
    const idPattern = /^[a-zA-Z0-9_-]{4,20}$/;

    if (!idPattern.test(id)) 
        return false;

    if (id.includes(" "))
        return false;

    return true;
}

/**
 * id 유효성 검사 (금지단어)
 * @param {*} id 
 * @returns {boolean}
 */
function validateIdWords(id) {
    const forbiddenWords = ["admin", "system", "root"];
    
    for ( const word of forbiddenWords ) {
        if ( id.toLowerCase().includes(word) )
            return false;
    }

    return true;
}

/**
 * password 패턴 유효성 검사
 * @param {*} pwd 
 * @returns {boolean}
 */
function validatePwd(pwd) {
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-={}[\]:;"'<>,.?/~`]).{8,16}$/;

    if (!passwordPattern.test(pwd)) 
        return false;

    if (pwd.includes(" "))
        return false;
    
    return true;
}

/**
 * password 유효성 검사 (id 포함 4자 이상 여부)
 * @param {*} id 
 * @param {*} pwd 
 * @returns {boolean}
 */
function validatePwdContains(id, pwd) {
    for (let i = 0; i <= id.length - 4; i++) {
        const substring = id.substring(i, i + 4);
        if (substring && pwd.includes(substring)) {
            return false;
        }
    }

    return true;
}

/**
 * 이메일 유효성 검사
 * @param {*} email 
 * @returns {boolean}
 */
function validateEmail(email) {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailPattern.test(email);
}