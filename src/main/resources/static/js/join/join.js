/**
 * Join.js 
 */

const API_ENDPOINTS = {
    join: '/api/join',
    check: '/api/join/check-duplicate'
};
const form = document.getElementById('joinForm');

$(document).ready(function () {
    $('#userId').on('focusout', function () {
        $('#checkUserIdBtn').data('flag', false);
    });

    $('#userEmail').on('focusout', function () {
        $('#checkUserEmailBtn').data('flag', false);
    });
});

function checkDuplicateId() {
    if ( $('#userId').val() === '' ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', '아이디'));
        $('#alertModal').modal('show');
        return;
    }
        
    fn_fetchGetData(`${API_ENDPOINTS.check}?userId=${$('#userId').val()}`)
        .then(data => {
            $('#alertModal .modal-body').text(data.message);
            $('#alertModal').modal('show');

            $('#checkUserIdBtn').data('flag', data.result);
        });
}

function checkDuplicateEmail() {
    if ( $('#userEmail').val() === '' ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', '이메일'));
        $('#alertModal').modal('show');
        return;
    }
        
    fn_fetchGetData(`${API_ENDPOINTS.check}?userEmail=${$('#userEmail').val()}`)
        .then(data => {
            $('#alertModal .modal-body').text(data.message);
            $('#alertModal').modal('show');

            $('#checkUserEmailBtn').data('flag', data.result);
        });
}

function joinConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', joinValidity);
        $('#confirmModal').modal('show');
    } 
}

function joinValidity() {
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    if ( !joinValidate() ) 
        return;

    join();
}

function joinValidate() {
    let isValid = true;
    
    const userId = $('#userId').val();
    const userPwd = $('#userPwd').val();

    const checks = [
        { flag: validateId(userId), selector: '#idPattern' },
        { flag: validateIdWords(userId), selector: '#idWords' },
        { flag: $('#checkUserIdBtn').data('flag'), selector: '#idCheck'},
        { flag: validatePwd(userPwd), selector: '#pwdPattern' },
        { flag: validatePwdContains(userId, userPwd), selector: '#pwdContains' },
        { flag: userPwd === $('#confirmPassword').val(), selector: '#pwdCredentials' },
        { flag: validateEmail($('#userEmail').val()), selector: '#emailPattern' },
        { flag: $('#checkUserEmailBtn').data('flag'), selector: '#emailCheck'}
    ];

    // 모든 검사 실행
    checks.forEach(check => {
        $(check.selector).toggle(!check.flag);
        if (!check.flag) isValid = false;
    });

    return isValid;
}

function join() {
    const formDataArray = $(form).serializeArray();
    const data = {};
    formDataArray.forEach(item => {
        if ( item.name !== 'confirmPassword')
            data[item.name] = item.value;
    });

    fn_fetchPostData(API_JOIN_URL.join, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if (data.result) {
            }
        });
}