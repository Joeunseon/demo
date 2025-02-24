/**
 * Join.js 
 */

const API_ENDPOINTS = {
    join: '/api/join',
    check: '/api/join/check',
};
const form = document.getElementById('joinForm');

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
        { flag: validatePwd(userPwd), selector: '#pwdPattern' },
        { flag: validatePwdContains(userId, userPwd), selector: '#pwdContains' },
        { flag: userPwd === $('#confirmPassword').val(), selector: '#pwdCredentials' },
        { flag: validateEmail($('#userEmail').val()), selector: '#emailPattern' }
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