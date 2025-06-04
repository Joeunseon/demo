/**
 * Password.js (Account)
 */

const ENDPOINTS = {
    main: '/',
    delay: '/api/account/password/delay',
    password: '/api/account/password'
};

const form = document.getElementById('accountForm');

$(document).on('ready', function () {
    if ( !$('#accountKey').val() ) {
        $('#alertModal').find('.modal-body').text($('#errorRequest').val());
        $('#alertModal').modal('show');
        $('#alertModal').on('hidden.bs.modal', function () {
            location.href = `${ENDPOINTS.main}`;
        });
    }
});

function delay() {

    fn_fetchPatchData(`${ENDPOINTS.delay}`, null)
        .then(data => {
            if ( data.result ) {
                location.href = ENDPOINTS.main;
            } else {
                $('#alertModal').find('.modal-body').text(data.message);
                $('#alertModal').modal('show');
            }
        });
}

function accountValidity() {
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }
    
    let isValid = true;
    const newPwd = $('#newPwd').val();

    const checks = [
        { flag: validatePwd(newPwd), selector: '#pwdPattern' },
        { flag: newPwd === $('#confirmPassword').val(), selector: '#pwdCredentials' }
    ];

    // 모든 검사 실행
    checks.forEach(check => {
        $(check.selector).toggle(!check.flag);
        if (!check.flag) isValid = false;
    });

    if ( isValid ) {
        $('#confirmModal .modal-body').text($('#saveConfrimMsg').val());
        $('#confirmModal .saveBtn').off('click').on('click', account);
        $('#confirmModal').modal('show');
    }
}

function account() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPatchData(`${ENDPOINTS.password}`, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    if ( $('#accountKey').val() === 'ACCOUNT_VERIFY' ) {
                        location.href = `${ENDPOINTS.me}`;
                    } else {
                        location.href = `${ENDPOINTS.main}`;
                    }
                });
            }
        });
}