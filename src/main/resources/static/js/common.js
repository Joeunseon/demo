/**
 * Common.js - 공통
 */

$(document).ready(function() {
    ui.init();
});

let ui = {
    init: function init() {
        this.datePicker();
        this.toastEditor();
    },
    datePicker: function datePicker() {
        $(document).on('focus', '.form-datepicker', function () {
          $(this).datepicker({
            defaultDate: new Date(),
            changeMonth: true,
            changeYear: true,
            monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
            showMonthAfterYear: true,
            showOtherMonths: true,
            dateFormat: "yy-mm-dd",
            gotoCurrent: true,
            showButtonPanel: true,
            closeText: "삭제",
            beforeShow: function (input, inst) {
                setTimeout(function () {
                    $('#ui-datepicker-div').addClass('datepicker-box');

                    // current button hide 처리
                    $(".ui-datepicker-current").hide();
            
                    // close button (삭제버튼) 처리
                    $('.ui-datepicker-close').on('click', function () {
                        $(input).val('');
                    });
                }, 10);
            },
          });
        });
        $(document).on('focusout', '.form-datepicker', function () {
          setTimeout(function(){
            $(this).datepicker('destroy');
          }, 500);
        })
    },
    toastEditor: function toastEditor() {
        $('.toast-editor').each(function () {
            let $this = $(this);

            if ( $this.data('toastEditor') )
                return;

            let editor = new toastui.Editor({
                el: this,
                height: '500px',
                initialEditType: 'wysiwyg', // 'markdown' or 'wysiwyg'
                previewStyle: 'vertical',       // 'tab' or 'vertical'
            });

            $this.data('toastEditor', editor);
        });
    }
}

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
    $('#alertModal').find('.modal-body').text(err.message);
    $('#alertModal').modal('show');
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
 * GET Text 요청
 * @param {string} url - 요청 URL
 * @returns {Promise} - text 데이터를 반환
 */
function fn_fetchGetText(url) {
    return fn_fetchRequest(url, { method: 'GET' }).then(response => response.text());
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
    const idPattern = /^[a-zA-Z0-9_-]{5,20}$/;

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

/**
 * 테이블 데이터 취득
 * @param {*} formId 
 * @param {*} callback 
 * @returns 
 */
function fn_resultList(formId, callback) {
    const targetForm = $('#'+formId);

    if (!targetForm.length) {
        console.error(`Form ID "${formId}"를 찾을 수 없습니다.`);
        return;
    }

    const queryString = targetForm.serialize();
    let url = targetForm.attr('action');
    if (queryString) {
        url += `?${queryString}`;
    } else {
        url += '?page=1';
    }

    fn_fetchGetData(url).then(data => {
        if ( data.result ) {
            // 페이징 처리
            fn_drawPaging(data.data.currentPage, data.data.pageScale, data.data.totalCnt);

            if ( callback ) callback(data.data);
        }
    });
}

/**
 * 페이징 처리
 * @param {*} currentPage 
 * @param {*} pageScale 
 * @param {*} totalCnt 
 */
function fn_drawPaging(currentPage, pageScale, totalCnt) {

    const url = `/pagination?currentPage=${currentPage}&pageScale=${pageScale}&totalCnt=${totalCnt}`;

    fn_fetchGetText(url).then(html => {

        $('#pagination').empty();
        $('#pagination').append(html);
    });
}